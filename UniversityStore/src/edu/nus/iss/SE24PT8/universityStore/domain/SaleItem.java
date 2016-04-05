/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

/**
 *
 * @author Zehua
 */
public class SaleItem  {

    private int saleQuantity = 0;
    private Product product;
    
    
    public SaleItem(Product product, int quantity){
        this.product=product;
        this.saleQuantity=quantity;
    }
    
    public SaleItem(String productID, int quantity) {
        this.product = ProductManager.getInstance().getProductByID(productID);
        this.saleQuantity = quantity;
    }

    public void setProductID(String productID){
    	this.product = ProductManager.getInstance().getProductByID(productID);
    }
    
    public String getProductID(){
        return product.getProductId();
    }    
    
    public void changeQuantity(int newQuantity){
        saleQuantity=newQuantity;
    }
    
    public int getSaleQuantity() {
        return saleQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getSubTotal()
    {
        return product.getPrice() * saleQuantity;
    }
        
 
}
