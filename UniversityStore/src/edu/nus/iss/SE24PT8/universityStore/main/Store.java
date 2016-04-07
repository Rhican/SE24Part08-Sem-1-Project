/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.main;

import edu.nus.iss.SE24PT8.universityStore.manager.*;

/**
 *
 * @author Misite Sawn
 */
public class Store {
 
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
}
