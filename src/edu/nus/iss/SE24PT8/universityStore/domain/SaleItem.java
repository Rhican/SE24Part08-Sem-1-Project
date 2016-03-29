/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

/**
 *
 * @author Zehua
 */
/**
 * Remark - Item No removed, the item no will be the item index inside the
 * transaction
 *
 * @author Zehua
 */
public class SaleItem  {

    private int saleQuantity = 0;
    private Product product;
    private String  productID;
    
    
    public String getProductID(){
        return productID;
    }
    
    public SaleItem(Product product, int quantity){
        this.product=product;
        this.saleQuantity=quantity;
        this.productID=product.getProductId();
    }
    
    public void setProductID(String productID){
        this.productID=productID;
    }
    

    public SaleItem(String productID, int quantity) {
        /// TODO: Get Product
        //this.product = 
        this.saleQuantity = quantity;
    }

    public boolean addQuantity(int quantityToBeAdded) {
        if (quantityToBeAdded == 0) {
            return false;
        }
        int newSaleQuantity = saleQuantity + quantityToBeAdded;
        //skip check against actual quantity for data loading purpose.
        //Check should be done at the transaction level1!!1
        saleQuantity = newSaleQuantity;
        return true;
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
    
    
    //-----------------------------------------------------------
    private boolean isProductExist(String productID) {
        // TODO
        return false;
    }
    
 
}
