/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManager;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zehua
 */
public class Transaction {
    private static long transactionIDCount = 0;
    private long id;
    private String memberID;
    private Date date;
    private Discount discount;
    private int redeemedPoint;
    private HashMap<String, SaleItem> saleItems; //barcode vs saleitem

    
    //- Constructors -------------------------------------------------------
    public Transaction() {        
        this.id = 0;
        this.date = new Date();
        this.memberID = "PUBLIC";
        this.discount=null;
        this.redeemedPoint = 0;
        this.saleItems = new HashMap();
    }
    
    //- Setters -------------------------------------------------------
    public boolean setDiscount(Discount discount) {
        if (isClosed()) this.discount = discount;
        return !isClosed();
    }

    public boolean setRedeemedPoint(int redeemedPoint) {
        if (isClosed()) this.redeemedPoint = redeemedPoint;
        return !isClosed();
    }
    
    public boolean setMember(String memberID) {
        if (isClosed()) this.memberID = memberID;
        return !isClosed();
    }

    public void insertSaleItemRecord(SaleItem saleItem) {
        saleItems.put(saleItem.getProductID(), saleItem);
    }
    public ReturnObject addSaleItem(Product product, int count) {
        return addSaleItem(product.getProductId(), count);
    }
    
    public ReturnObject addSaleItem(String productID, int count) {
        if (isClosed()) 
        {
            return new ReturnObject(false, "Exception occurred : Transaction is Closed", null);
        }
        if (count <= 0) {
            return new ReturnObject(false, "Exception occurred : Item Quantity less than 1", null);
        }
        if (saleItems.containsKey(productID)) {
            //saleItems.get(productID).
            saleItems.get(productID).changeQuantity(count);
        } else {
            SaleItem newSaleItem = new SaleItem(productID, count);
            saleItems.put(productID, newSaleItem);
        }
        return new ReturnObject(true, "added saleitem", this);
    }

     public ReturnObject removeSaleItem(SaleItem saleItem) {
        
        String productID = saleItem.getProductID();
        int remaining = saleItem.getSaleQuantity();
        return removeSaleItem(productID, remaining);
        
    }
     
    public ReturnObject removeSaleItem(String productID, int count) {
        if (isClosed()) 
        {
            return new ReturnObject(false, "Exception occurred : Transaction is Closed", null);
        }
        if (count <= 0) {
            return new ReturnObject(false, "Exception occurred : Item Quantity less than 1", null);
        }
        if (saleItems.containsKey(productID)) {
            SaleItem item = saleItems.get(productID);
            item.changeQuantity(-count);
            if (item.getSaleQuantity() == 0) {
                saleItems.remove(productID);
            }
        } else {
            return new ReturnObject(true, "Sale Item is not exists", this);
        }
        return new ReturnObject(true, "removed saleitem", this);
    }
    
    public ReturnObject changeQuantity(SaleItem saleItem, int count)
    {
        if (isClosed()) 
        {
            return new ReturnObject(false, "Exception occurred : Transaction is Closed", null);
        }
        if (count <= 0) {
            return new ReturnObject(false, "Exception occurred : Item Quantity less than 1", null);
        }
        String productID= saleItem.getProductID();
        if (saleItems.containsKey(productID)) {
            //saleItems.get(productID).
            saleItems.get(productID).changeQuantity(count);
        }
        return new ReturnObject(true, "changed saleitem", this);
    }
    
    public boolean close() {
        if (validate()) return false;
        
        MemberManager memberManager = MemberManager.getInstance();
        try {
            memberManager.redeemPoints(memberID, redeemedPoint);
            memberManager.addLoyaltyPoints(memberID, computeRoyalityPoint());
        } catch (BadMemberRegistrationException ex) {
            //Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
        
        saleItems.values().stream().forEach((saleItem) -> {
            updateProduct(saleItem);
        });
        
        if (isClosed()) {
            id = getNextID();
        }
        
        return true;
    }

    public void setDate(Date date) {
        if (isClosed()) this.date = date;
    }
    //- Getters -------------------------------------------------------
    public double getTotalAmount() {
        return computeTotalAmount();
    }

    public double getDiscountAmount() {
        return (discount != null) ? computeDiscountedAmount(discount.getDiscountPercent()) : 0;
    }
    
    public double getNetAmount()
    {
        return computeNetAmount();
    }

    public int getRedeemedPoint() {
        return redeemedPoint;
    }
    
    public double getRedeemedAmount() {
        return computeRedeemAmount(redeemedPoint);
    }

    public SaleItem getSaleItem(String productID ) {
        if (saleItems.containsKey(productID)) return saleItems.get(productID);
        return null;
    }

    public final ArrayList<SaleItem> getSaleItems() {
        return new ArrayList<>(saleItems.values());
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Member getMember() {
        return MemberManager.getInstance().getMember(memberID);
    }

    public Discount getDiscount() {
        return discount;
    }
    public boolean isClosed()
    {
        return getId() != 0;
    }

    //-----------------------------------------------------------
    // Private
    //-----------------------------------------------------------
    /**
     * Validate if the transaction content
     *  1. Validate products and their quantities vs available stock
     *  2. Validate member redeem point vs the member available royalty point
     * @return true if all validation passes, else false
     */
    private boolean validate() 
    {
        // 1. Check if all Products are exists and have enough quantity
        if (!saleItems.values().stream().noneMatch((saleItem) 
                -> (!isProductAvailable(saleItem)))) {
            return false;
        }
        // 2. Check if member loyalty point is bigger than redeeming point
        if (redeemedPoint > getMember().getLoyaltyPoints()) return false;
        
        return true;        
    }
    
    private boolean isProductAvailable(SaleItem saleItem) {
        Product product = saleItem.getProduct();        
        return (product != null) && (product.getQty() >=  saleItem.getSaleQuantity());
    }

    private boolean updateProduct(SaleItem saleItem) {
        Product product = saleItem.getProduct();
        int remaining = product.getQty() - saleItem.getSaleQuantity();
        product.setQty(remaining);
        return false;
    }

    private double computeTotalAmount() {
        double amount = 0;
        amount = saleItems.entrySet().stream().map((pair) -> 
                (SaleItem) pair.getValue()).filter((item) -> 
                (item != null)).map((item) -> 
                item.getSubTotal()).reduce(amount, (accumulator, _item) -> 
                accumulator + _item);
        return amount;
    }

    private double computeDiscountedAmount(float discountedPercentage) {
        double discountFraction = discountedPercentage / 100.0f;
        return computeTotalAmount() * discountFraction;        
    }

    private double computeRedeemAmount(int redeemedPoint) {
        return redeemedPoint / 20.0f; //fixed: Every 100 redeem point = $5
    }
    
    private int computeRoyalityPoint() {
        return (int)(computeNetAmount() / 10.0f + 0.5f); //fixed: Every $10 spent = 1 point
    }

    private double computeNetAmount() {
        return computeTotalAmount() - computeRedeemAmount(redeemedPoint)
                - ((discount != null) ? computeDiscountedAmount(discount.getDiscountPercent()) : 0);
    }
    
        
    /**
     * ID generation
     * @return next Id, in long
     */
    private long getNextID()
    {
        return ++transactionIDCount;
    }
}


