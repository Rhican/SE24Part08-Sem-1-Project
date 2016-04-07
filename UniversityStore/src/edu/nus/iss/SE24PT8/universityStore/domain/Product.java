/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import java.util.Objects;

import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;

/**
 *
 * @author misitesawn
 */
public class Product {

    private String productId;
    private String productName;
    private String briefDesp;
    private int qty;
    private double price;
    private String barcode;
    private int reorderQty;
    private int orderQty;
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

   
    public Product(String productId, String productName, String briefDesp, int qty, double price, String barcode, int reorderQty, int orderQty) {
        this.productId = productId;
        this.productName = productName;
        this.briefDesp = briefDesp;
        this.qty = qty;
        this.price = price;
        this.barcode = barcode;
        this.reorderQty = reorderQty;
        this.orderQty = orderQty;

    }

    public Product(String productId, String productName, String briefDesp, int qty, double price, String barcode, int reorderQty, int orderQty, Category category) {
        this(productId, productName, briefDesp, qty, price, barcode, reorderQty, orderQty);
        this.category = category;

    }
    
    public Product(){
        
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getBriefDesp() {
        return briefDesp;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getReorderQty() {
        return reorderQty;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBriefDesp(String briefDesp) {
        this.briefDesp = briefDesp;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setReorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }
    /**
     * Return Category Id from ProductID
     *
     *  
     * @return categoryCode
     */
    public String getCategoryCode() {

        return this.getProductId().substring(0, 3);
    }

    
    public void reduceQuantity(int reduceAmount) throws BadProductException{
        //check reduce amount
        if(reduceAmount>qty){
           throw new BadProductException( "Amount reduced is greated than current quantity");
        }
        qty=qty-reduceAmount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.productId);
        hash = 97 * hash + Objects.hashCode(this.productName);
        hash = 97 * hash + Objects.hashCode(this.briefDesp);
        hash = 97 * hash + this.qty;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.barcode);
        hash = 97 * hash + this.reorderQty;
        hash = 97 * hash + this.orderQty;
        hash = 97 * hash + Objects.hashCode(this.category);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
         if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.qty != other.qty) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.reorderQty != other.reorderQty) {
            return false;
        }
        if (this.orderQty != other.orderQty) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.briefDesp, other.briefDesp)) {
            return false;
        }
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
       if (!Objects.equals(this.category, other.category)) {
            return false;
        } 
        return true;
    }
    
 
}
