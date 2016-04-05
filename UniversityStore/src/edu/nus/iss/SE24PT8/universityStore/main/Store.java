/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.main;


import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;

import edu.nus.iss.SE24PT8.universityStore.domain.*;
import edu.nus.iss.SE24PT8.universityStore.manager.*;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;

/**
 *
 * @author misitesawn
 */
public class Store {
  
//    public interface mgrDiscount{
//        public Discount getMaxDiscount(Date date);
//        public Discount getMaxDiscount(Date date,Member member);
//        public void addNewMemberDiscount(MemberDiscount memberDiscount);
//        public void addNewOtherDiscount(OtherDiscount otherDiscount);
//        
//    }

    //Store is placeholder for all managers, and responsible for loading them
    private ProductManager mgrProduct;
    private CategoryManager mgrCategory;
    private VendorManager mgrVendor;
    private MemberManager mgrMember;
    private DiscountManager mgrDiscount;
    private TransactionManager mgrTransaction;
    private StoreKeeperManager mgrStoreKeeper;    

    private Store() {
        mgrProduct=ProductManager.getInstance();
        mgrCategory=CategoryManager.getInstance();
        mgrVendor=VendorManager.getInstance();
        mgrMember=MemberManager.getInstance();
        mgrDiscount=DiscountManager.getInstance();
        mgrTransaction=TransactionManager.getInstance();
        mgrStoreKeeper=StoreKeeperManager.getInstance();
        //After all Managers are loaded, get the related objects
        mgrProduct.getRelatedObjects();
//        mgrCategory.getRelatedObjects();
//        mgrVendor.getRelatedObjects();
        mgrMember.getRelatedObjects();
        mgrDiscount.getRelatedObjects();
        
        
        
        //listObjects();
    }

    public ProductManager getMgrProduct() {
        return mgrProduct;
    }

    public CategoryManager getMgrCategory() {
        return mgrCategory;
    }

    public VendorManager getMgrVendor() {
        return mgrVendor;
    }

    public MemberManager getMgrMember() {
        return mgrMember;
    }

    public DiscountManager getMgrDiscount() {
        return mgrDiscount;
    }

    public TransactionManager getMgrTransaction() {
        return mgrTransaction;
    }

    public StoreKeeperManager getMgrStoreKeeper() {
        return mgrStoreKeeper;
    }
    
    
    
    
    
    
    public static Store getInstance() {
        return StoreHolder.INSTANCE;
    }
    
    private static class StoreHolder {

        private static final Store INSTANCE = new Store();
    }
    
    

    public void listObjects(){
 
        ArrayList<Product> products=mgrProduct.getProductList();
        for(Product object:products){
            System.out.println(DataAdapter.convertProductToString(object));
        }
        
        ArrayList<Category> categories=DataAdapter.loadCategories();
        for(Category object:categories){
            System.out.println(DataAdapter.convertCategoryToString(object));
            ArrayList<Vendor> vendors=DataAdapter.loadVendors(object.getCategoryCode());
            for(Vendor vendor:vendors){
                System.out.println(DataAdapter.convertVendorToString(vendor));
            }
            
        }
        
        ArrayList<edu.nus.iss.SE24PT8.universityStore.domain.Member> members=DataAdapter.loadMembers();
        for(edu.nus.iss.SE24PT8.universityStore.domain.Member object:members){
            System.out.println(DataAdapter.convertMemberToString(object));
        }
        
        ArrayList<StoreKeeper> storekeepers=DataAdapter.loadStoreKeepers();
        for(StoreKeeper object:storekeepers){
            System.out.println(DataAdapter.convertStoreKeeperToString(object));
        }
        
        ArrayList<Discount> discounts=DataAdapter.loadDiscounts();
        for(Discount discount:discounts){
            System.out.println(DataAdapter.convertDiscountToString(discount));
        }
        
        ArrayList<Transaction> transactions=DataAdapter.loadTransactions();
        for(Transaction trans:transactions){
            
            System.out.println("----------------------------");
            //System.out.println("Transaction"+trans.getTransactionID()+DataAdapter.comma+trans.getCustomerID()+DataAdapter.comma+DataAdapter.DF.format(trans.getTransactionDate()));
            System.out.println("   sale items:");
            for(SaleItem sale:trans.getSaleItems()){
                System.out.println("        "+sale.getProductID()+DataAdapter.comma+sale.getSaleQuantity());
            }
        }
        
        
    
        
        
    }
    
    
    
    
    

    
    
    
    
    
}
