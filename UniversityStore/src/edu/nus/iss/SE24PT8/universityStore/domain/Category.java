/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Mugunthan
 */
public class Category {

    private String categoryCode;
    private String categoryName;
    private ArrayList<Vendor> vendorList;

    public Category(String code, String name, ArrayList<Vendor> vendorList) {
        this(code, name);
        this.vendorList = vendorList;
    }
    
    public Category(String code, String name) {
        this.categoryCode = code;
        this.categoryName = name;
        this.vendorList=new ArrayList<>();
        
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Vendor> getVendorList() {
        return vendorList;
    }

    public void setVendorList(ArrayList<Vendor> newVendorList){
        this.vendorList=newVendorList;
    }
    
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }   
    

    @Override
    public String toString() {
        return "Category{" + "categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", venderList=" + vendorList + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.categoryCode);
        hash = 97 * hash + Objects.hashCode(this.categoryName);
        hash = 97 * hash + Objects.hashCode(this.vendorList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (!Objects.equals(this.categoryCode, other.categoryCode)) {
            return false;
        }
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        if (!Objects.equals(this.vendorList, other.vendorList)) {
            return false;
        }
        return true;
    }

 
    

}
