/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author misitesawn
 */
public final class Constants {
    public static final String CONST_CUST_TYPE_MEMBER  = "M";
    public static final String CONST_CUST_TYPE_PUBLIC  = "A";
 
    public static final String CONST_ALWAYS = "ALWAYS";
 
    public static final String CONST_CUST_NONMEMBER_ID  = "PUBLIC";
    public static final String CONST_CUST_NONMEMBER_NAME = "PUBLIC";
    
    public static final String CONST_MEMBER_ERR_MEMBEREXIST="MemberID already exist!";
    public static final String CONST_MEMBER_ERR_MEMBERIDSPECIALCHAR="Invalid Member ID!Please remove special character";
    public static final String CONST_MEMBER_ERR_MEMBERNAMESPECIALCHAR="Invalid MemberName!Please remove special character";
    public static final String CONST_MEMBER_ERR_INVALIDLOYALTYPOINT="Invalid loyaltyPoints specified";
    public static final String CONST_MEMBER_ERR_INVALIDMEMBERID="No CustomerID specified";
    public static final String CONST_MEMBER_ERR_INVALIDMEMBERNAME="No CustomerName specified";
    public static final String CONST_MEMBER_ERR_MEMBERNAMELENGTH="Customer Name should not exceed more than 20 characters";
    public static final String CONST_MEMBER_ERR_MEMBERIDLENGTH="Customer ID should not exceed more than 20 char";
    public static final String CONST_MEMBER_ERR_MEMBERNOTFOUND="Member not found!";
    public static final String CONST_MEMBER_ERR_MEMBERLISTNULL="Member list not available!Please try again later";
    public static final String CONST_MEMBER_ERR_NOPOINT="Redeem Point not avaiable!";
    public static final String CONST_MEMBER_ERR_INSUFFICIENTPOINT="Redeem Point not available!";
    public static final String CONST_MEMBER_ERR_POINT="Point should be between 1 to 5000";

    public static final String CONST_PRODUCT_ERR_BARCODEEXIST = "Bar code for the prodcut is already exist!";


    public static final String CONST_STOREKEEPER_ERR_LOGIN="Invalid UserName/Password!";
    public static final String CONST_STOREKEEPER_ERR_SPECIALCHAR="User Name/Password do not accept special character";
    public static final String CONST_STOREKEEPER_ERR_MEMBEREXIST="User Name already exist in the system";
   
    public static final String CONST_DISCOUNT_ERR_DISCOUNTCODEEXIST = "Discount Code already exists!";
    
    
    //Added by Mugunthan
    public static final String CONST_CAT_ERR_INVALID_DETAILS = "Name and Code of category should be specified";
    public static final String CONST_CAT_ERR_LONG_CODE = "Code should contain three characters";
    public static final String CONST_CAT_MSG_CREATION_SUCUESS = "Category Sucussfully created.";
    public static final String CONST_CAT_ERR_CATCODEEXIST = "Category code already exist";
    public static final String CONST_CAT_MSG_UPDATE_SUCUESS = "Category updated sucessfully";
    public static final String CONST_CAT_ERR_NOTEXIST = "Category not exists";
    public static final String CONST_CAT_ERR_DELETE = "Category associated with some products";
    public static final String CONST_CAT_MSG_DELETE_SUCUESS = "Category deleted sucessfully";
    
    //Added by Mugunthan
    public static final String CONST_VENDOR_ERR_CATCODEMISSING = "Category code is missing";
    public static final String CONST_VENDOR_ERR_NAMEMISSING = "Vendor name is missing";
    public static final String CONST_VENDOR_MSG_CREATION_SUCUESS ="Vendor added Successfully";
    public static final String CONST_VENDOR_MSG_UPDATE_SUCUESS = "VendorList updated Successfully";
    
    //Added by Mugu
    public static final String ADD_OPERATION = "add";
    public static final String MODIFY_OPERATION = "modify";
    public static final String DELETE_OPERATION = "delete";
    public static final Color STORE_APP_TITLE_COLOR = new java.awt.Color(0, 64, 128);
    public static final Font STORE_APP_TITLE_FONT= new java.awt.Font("Segoe UI", 1, 20);
    
    public static final String PRODCUT_ENTRYFLAG_NEW = "NEW_ENTRY";
    public static final String PRODUCT_ENTRYFLAG_EDIT = "EDIT_ENTRY";
    public static final String DISCOUNT_ENTRYFLAG_NEW = "NEW_ENTRY";
    public static final String DISCOUNT_ENTRYFLAG_EDIT = "EDIT_ENTRY";
    
    

}