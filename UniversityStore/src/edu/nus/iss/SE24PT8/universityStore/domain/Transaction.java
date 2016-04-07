/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

/**
 *
 * @author Zehua
 */
public class Transaction implements TransactionInterface {
	private static long transactionIDCount = 0;
	private static NonMember nonMember = null;
	private static final double dollarToRoyalPointRate = 0.1f; // Every $10 spent = 1 point
	private static final double royalPointToDollarRate = 0.05f;// Every 20 points = 1 dollar
	
	private long id;
	private String memberID;
	private Date date;
	private Discount discount;
	private int redeemedPoint;
	private HashMap<String, SaleItem> saleItems; // Table of "product id" vs "SaleItem"

	// - Constructors -------------------------------------------------------
	public Transaction() {
		if (nonMember == null) {
			try {
				String name = MemberManager.getInstance().getNonMemberName();
				nonMember = new NonMember(name, name);
			} catch (BadMemberRegistrationException e) {
				System.out.println("Warning: Fail to initialise NonMember object.");
			}
		}
		this.id = 0;
		this.date = new Date();
		this.memberID = "PUBLIC";
		this.discount = getDefaultDiscount();
		this.redeemedPoint = 0;
		this.saleItems = new HashMap<String, SaleItem>();
	}

	// - Setters -------------------------------------------------------
	public void setDate(Date date) {
		if (!isClosed())
			this.date = date;
	}

	public boolean setDiscount(Discount discount) {
		if (!isClosed())
			this.discount = discount;
		return !isClosed();
	}

	public boolean setRedeemedPoint(int redeemedPoint) {
		if (!isClosed())
			this.redeemedPoint = redeemedPoint;
		return !isClosed();
	}

	public boolean setMember(String memberID) {
		if (!isClosed()) {
			if (memberID.equalsIgnoreCase(nonMember.getID()))
				this.memberID = "";
			else
				this.memberID = memberID;
		}
		return !isClosed();
	}

	public boolean addSaleItem(Product product, int count) throws TransactionException {
		return addSaleItem(product.getProductId(), count);
	}

	public boolean addSaleItem(String productID, int count) throws TransactionException {
		validateThrowExceptionIfCanNotModify();
		validateThrowExceptionIfQuantityInvalid(count);
		if (saleItems.containsKey(productID)) {
			return changeQuantity(saleItems.get(productID), count);
		} else {
			SaleItem newSaleItem = new SaleItem(productID, count);
			saleItems.put(productID, newSaleItem);
		}
		return true;
	}

	public boolean removeSaleItem(SaleItem saleItem) throws TransactionException {
		validateThrowExceptionIfCanNotModify();
		String productID = saleItem.getProductID();
		if (saleItems.containsKey(productID)) {
			saleItems.remove(productID);
		}
		return true;
	}

	public boolean changeQuantity(SaleItem saleItem, int count) throws TransactionException {
		validateThrowExceptionIfCanNotModify();
		validateThrowExceptionIfQuantityInvalid(count);
		String productID = saleItem.getProductID();
		if (saleItems.containsKey(productID)) {
			saleItems.get(productID).changeQuantity(count);
		}
		return true;
	}

	public boolean close(boolean isValidated, boolean isProcessedRecord) throws TransactionException {
		if (!isValidated) {
			if (!validateSaleItems()) {
				throw new TransactionException("Sale Items Validation failed.");
			}
			if (!validateMember()) {
				throw new TransactionException("Member Validation failed.");
			}
		}

		if (!isProcessedRecord) {
			MemberManager memberManager = MemberManager.getInstance();
			Member member = memberManager.getMember(memberID);
			if (member != null) {
				try {
					if (redeemedPoint > 0)
						memberManager.redeemPoints(memberID, redeemedPoint);
					for (int newRoyalPoint = computeRoyalityPoint(); newRoyalPoint >= 1
							&& newRoyalPoint <= 5000; newRoyalPoint -= 5000) {
						memberManager.addLoyaltyPoints(memberID, computeRoyalityPoint());
					}
				} catch (BadMemberRegistrationException ex) {
					throw new TransactionException(
							"Fail to update member, exception from member manager:\n" + ex.getMessage(), ex);
				}
			}
			saleItems.values().stream().forEach((saleItem) -> {
				updateProduct(saleItem);
			});
		}

		if (!isClosed()) {
			id = getNextID();
		}

		return true;
	}

	// - Getters -------------------------------------------------------
	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public double getTotalAmount() {
		return computeTotalAmount();
	}

	public double getDiscountAmount() {
		if (discount != null) return computeDiscountedAmount(discount.getDiscountPercent());
		else {
			Discount nonMemberDiscount = DiscountManager.getInstance().getMaxDiscount(date, nonMember);
			 return (nonMemberDiscount != null) ? computeDiscountedAmount(nonMemberDiscount.getDiscountPercent()) : 0;
		}
	}

	public double getNetAmount() {
		return computeNetAmount();
	}

	public int getRedeemedPoint() {
		return redeemedPoint;
	}

	public double getRedeemedAmount() {
		return computeRedeemAmount(redeemedPoint);
	}

	public SaleItem getSaleItem(String productID) {
		if (saleItems.containsKey(productID))
			return saleItems.get(productID);
		return null;
	}

	public final ArrayList<SaleItem> getSaleItems() {
		return new ArrayList<>(saleItems.values());
	}

	public Member getMember() {
		return MemberManager.getInstance().getMember(memberID);
	}

	public String getMemberID() {
		if (memberID.isEmpty())
			return nonMember.getID();
		return memberID;
	}

	public Discount getDiscount() {
		return discount;
	}
	
	public Discount getDefaultDiscount() {
		return DiscountManager.getInstance().getMaxDiscount(date, nonMember);
	}
	

	public boolean isClosed() {
		return getId() != 0;
	}

	// -----------------------------------------------------------
	// Private
	// -----------------------------------------------------------
	/**
	 * Validate if the transaction content 1. Validate products and their
	 * quantities vs available stock 2. Validate member redeem point vs the
	 * member available royalty point
	 * 
	 * @return true if all validation passes, else false
	 */
	private boolean validateSaleItems() {
		// 1. Check if all Products are exists and have enough quantity
		if (!saleItems.values().stream().noneMatch((saleItem) -> (!isProductAvailable(saleItem)))) {
			return false;
		}
		return true;
	}

	private boolean validateMember() {
		// 2. Check if member loyalty point is bigger than redeeming point
		if (redeemedPoint > 0 && getMember() != null && redeemedPoint > getMember().getLoyaltyPoints())
			return false;

		return true;
	}

	private boolean isProductAvailable(SaleItem saleItem) {
		Product product = saleItem.getProduct();
		return (product != null) && (product.getQty() >= saleItem.getSaleQuantity());
	}

	private boolean updateProduct(SaleItem saleItem) {
		Product product = saleItem.getProduct();
		int remaining = product.getQty() - saleItem.getSaleQuantity();
		product.setQty(remaining);
		return false;
	}

	private double computeTotalAmount() {
		double amount = 0;
		amount = saleItems.entrySet().stream().map((pair) -> (SaleItem) pair.getValue())
				.filter((item) -> (item != null)).map((item) -> item.getSubTotal())
				.reduce(amount, (accumulator, _item) -> accumulator + _item);
		return amount;
	}

	private double computeDiscountedAmount(float discountedPercentage) {
		double discountFraction = discountedPercentage / 100.0f;
		return computeTotalAmount() * discountFraction;
	}

	private double computeRedeemAmount(int redeemedPoint) {
		return redeemedPoint * royalPointToDollarRate; 
	}

	private int computeRoyalityPoint() {
		return (int) (computeNetAmount() * dollarToRoyalPointRate + 0.5f); // Round up
	}

	private double computeNetAmount() {
		return computeTotalAmount() - computeRedeemAmount(redeemedPoint) - getDiscountAmount();
	}

	private void validateThrowExceptionIfCanNotModify() throws TransactionException {
		if (isClosed())
			throw new TransactionException("Transaction is Closed, no modification is allowed.");
	}

	private void validateThrowExceptionIfQuantityInvalid(int quantity) throws TransactionException {
		if (quantity <= 0)
			throw new TransactionException("Exception: Item Quantity can not less than 1.");
	}

	/**
	 * ID generation
	 * 
	 * @return next Id, in long
	 */
	private long getNextID() {
		return ++transactionIDCount;
	}
}
