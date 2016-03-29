package edu.nus.iss.SE24PT8.UniversityStore.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.nus.iss.SE24PT8.UniversityStore.exception.BadVendorException;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class StoreKeeperTest extends TestCase {
    
    private StoreKeeper storeuser1;
    private StoreKeeper storeuser2;
    
   
    @Before
    public void setUp() {
        storeuser1= new StoreKeeper("Stacy","Dean56s");
        storeuser2=new StoreKeeper("Johny","A12ysd45");
    }
    
    @After
    public void tearDown() {
        storeuser1=null;
        storeuser2=null;
    }

    @Test
    public void testGetStoreKeeperName(){
        assertEquals("Stacy",storeuser1.getstoreKeeperName());
        assertEquals("Johny",storeuser2.getstoreKeeperName());
        
    }
    
    @Test
    public void testGetPassword(){
       assertEquals("Dean56s",storeuser1.getPassword());
       assertEquals("A12ysd45",storeuser2.getPassword());
    }
    
    @Test
    public void testStoreKeeper()
    {
       assertEquals("Stacy",storeuser1.getstoreKeeperName());
       assertEquals("Dean56s",storeuser1.getPassword());
       assertEquals("Johny",storeuser2.getstoreKeeperName());
       assertEquals("A12ysd45",storeuser2.getPassword());
    }
    
    @Test
    public void testEquals(){
        
        assertSame(storeuser1,storeuser1);
        assertSame(storeuser2,storeuser2);
        assertEquals(storeuser1,new StoreKeeper("Stacy","Dean56s"));
        assertEquals(storeuser2,new StoreKeeper("Johny","A12ysd45"));
        assertFalse(storeuser1.equals(storeuser2));
        assertFalse(storeuser2.equals(storeuser1));
        
    }
    
    @Test
    public void testToString() {
       storeuser1.show();
       storeuser2.show();
    }
}
