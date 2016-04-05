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
import org.junit.validator.PublicClassValidator;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 *
 * @author Mugunthan
 */
public class CategoryManagerTest extends TestCase{
	CategoryManager catManager;
    
    public CategoryManagerTest() {
    }
    
    @Before
    public void setUp() {
         catManager = CategoryManager.getInstance();
    }
    
    @After
    public void tearDown() {
    	catManager = null;
    }
    
    @Test
    public void testCategoryMgrInstance(){
        CategoryManager catManager1 = CategoryManager.getInstance();
        CategoryManager catManager2 = CategoryManager.getInstance();
        assertFalse(catManager1 == null);
        assertFalse(catManager2 == null);
        assertTrue(catManager1.equals(catManager2)); // Singleton test    	
    }
    
    @Test
    public void testAddCategory(){
    	try {
			catManager.addCategory("code1", "name1");
			fail();
		} catch (BadCategoryException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_INVALID_DETAILS));
		}
    	try {
			Category cat = catManager.addCategory("c11", "name1");
			assertTrue(cat.getCategoryCode().equals("c11"));
		} catch (BadCategoryException e) {
			fail();
		}
    	try {
			catManager.addCategory("c11", "name2");
			fail();
		} catch (BadCategoryException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_CATCODEEXIST));
		}
    }
}
