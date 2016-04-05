package edu.nus.iss.SE24PT8.universityStore.domain;

import java.util.ArrayList;
import java.util.Date;

import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;

public interface TransactionInterface {

	// Getters 
	public long getId();
	public Date getDate();
	public double getTotalAmount();
	public double getDiscountAmount();
	public double getNetAmount();
	public int getRedeemedPoint();
	public double getRedeemedAmount();
	public Member getMember();
	public String getMemberID();
	public Discount getDiscount();
	public Discount getDefaultDiscount();
	public ArrayList<SaleItem> getSaleItems();
	public SaleItem getSaleItem(String productID);
	
	// Setters 
	public boolean setMember(String memberID);
	public boolean setRedeemedPoint(int redeemedPoint);
	public boolean setDiscount(Discount discount);
	public boolean addSaleItem(Product product, int count) throws TransactionException;
	public boolean removeSaleItem(SaleItem saleItem) throws TransactionException;
}
