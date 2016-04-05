/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 *
 * @author Mugunthan
 */
public class VendorManagerTest extends TestCase{
    
    @Before
    public void setUp() {
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
}
