/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;


import edu.nus.iss.SE24PT8.universityStore.domain.Category;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mugunthan
 */
public class CategoryTest {
    
    Category cat1, cat2, cat3,cat4;
    
    public CategoryTest() {
    }
    
    @Before
    public void setUp() {
        cat1 = new Category("CLO", "Clothing");
        cat2 = new Category("DIA", "DIARY");
        cat3 = new Category("MOB", "Mobile");
        cat4 = new Category("ACS", null);
    }
    
    @After
    public void tearDown() {
    	cat1 = null;
    	cat2 = null;
    	cat3 = null;
    	cat4 = null;
    }
    
    @Test
    public void testCatCode() {
    	assertEquals("CLO", cat1.getCategoryCode());
    }
    
    @Test
    public void testCatName() {
    	assertEquals("DIARY", cat2.getCategoryName());
    	assertNull(cat4.getCategoryName());
    }

    @Test
    public void testEquals(){
        assertEquals(cat1, new Category("CLO", "Clothing"));
        assertEquals(cat4, new Category("ACS", null));
        assertFalse(cat2.equals(new Category("MOB", "Mobile")));
        assertFalse(cat3.equals(new Category("MOB", null)));
    }
}
