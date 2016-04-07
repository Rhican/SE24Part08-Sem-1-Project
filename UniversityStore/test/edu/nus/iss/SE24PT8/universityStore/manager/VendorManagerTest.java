/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.exception.BadVendorException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mugunthan
 */
public class VendorManagerTest {//extends TestCase{
	CategoryManager catManager;
	VendorManager venManager;
    
    @Before
    public void setUp() throws BadCategoryException {
		catManager = CategoryManager.getInstance();
		venManager = VendorManager.getInstance();
		catManager.addCategory("PEN", "PENSIL");
		catManager.addCategory("MOB", "MOBILE");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testVendorMgrInstance(){
        VendorManager vendorManager1 = VendorManager.getInstance();
        VendorManager vendorManager2 = VendorManager.getInstance();
        assertFalse(vendorManager1 == null);
        assertFalse(vendorManager2 == null);
        assertTrue(vendorManager1.equals(vendorManager2)); // Singleton test    	
    }
    
    @Test
    public void testAddVendor(){
    	assertNotNull(venManager.getVendorsListByCategory("PEN"));
    	try {
			venManager.addVendor("PEN", "RENOLD", "Number 1 brand in Asia");			
		} catch (BadVendorException e) {
			fail();
		}
    	//adding duplicate vendor
    	try {
			venManager.addVendor("PEN", "RENOLD", "Number 1 brand in Asia");
			fail();
		} catch (BadVendorException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_VENDOR_ERR_EXIST));
		}
    	//category not exist
    	try {
			venManager.addVendor("AEN", "RENOLD", "Stay with you");
			fail();
		} catch (BadVendorException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_NOTEXIST));
		}
    	//no category name
    	try {
			venManager.addVendor("", "RENOLD", "Stay with you");
			fail();
		} catch (BadVendorException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_VENDOR_ERR_CATCODEMISSING));
		}
    	//No vendor name
    	try {
			venManager.addVendor("PEN", "", "Number 1 brand in Asia");
			fail();
		} catch (BadVendorException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_VENDOR_ERR_NAMEMISSING));
		}
    	try {
			venManager.addVendor("PEN", "RENOLD", "Number 1 brand in Asia");
			venManager.addVendor("PEN", "Atlas", "Atlas");	
			venManager.addVendor("MOB", "SAMSUNG", "SAMSUNG");	
			venManager.addVendor("MOB", "IPHONE", "A Apple brand");				
		} catch (BadVendorException e) {
			fail();
		}
    	
    }
    
    @Test
    public void testVendorListBycat(){
    	assertTrue(venManager.getVendorsListByCategory("PEN").size() == 3);
    	assertTrue(venManager.getVendorsListByCategory("MOB").size() == 2);
    	assertTrue(venManager.getVendorsListByCategory("XXX").size() == 0);
    }
    
    @Test
    public void testGetVendors() throws BadVendorException{
    	HashMap<String, ArrayList<Vendor>> vendors = venManager.getVendors();
    	assertTrue(vendors.get("MOB").size() == 2);
    	venManager.addVendor("MOB", "LG", "LG");
    	vendors = venManager.getVendors();
    	assertTrue(vendors.get("MOB").size() == 3);
    }
    
}
