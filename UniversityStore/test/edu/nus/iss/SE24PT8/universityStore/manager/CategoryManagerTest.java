/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Mugunthan
 */
public class CategoryManagerTest {
	CategoryManager catManager = null;

	@Before
	public void setUp() throws IOException {
		catManager = CategoryManager.getInstance();
	}

	@After
	public void tearDown() throws IOException {
		catManager = null;
	}

	@Test
	public void testCategoryMgrInstance() {
		System.out.println("Test");
		CategoryManager catManager1 = CategoryManager.getInstance();
		CategoryManager catManager2 = CategoryManager.getInstance();
		assertFalse(catManager1 == null);
		assertFalse(catManager2 == null);
		assertTrue(catManager1.equals(catManager2)); // Singleton test
	}

	@Test
	public void testAddCategory() {
		try {
			catManager.addCategory("code1", "name1");
			fail();
		} catch (BadCategoryException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_INVALID_DETAILS));
		}
		try {
			Category cat = catManager.addCategory("c11", "name1");
			assertTrue(cat.getCategoryCode().equals("C11"));
		} catch (BadCategoryException e) {
			fail();
		}
		try {
			catManager.addCategory("c11", "name2");
			fail();
		} catch (BadCategoryException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_CATCODEEXIST));
		}
		try {
			catManager.addCategory("c12", "name2");
		} catch (BadCategoryException e) {
			fail();
		}
	}
	
	@Test
	public void testGetCategories(){
		ArrayList<Category> cats = catManager.getCategories();
		assertTrue(cats.size() == 2);
	}
	
	@Test
	public void testGetCategory(){		
		assertTrue(catManager.getCategory("c12").getCategoryName().equals("name2"));
    	assertNotNull(catManager.getCategory("c12").getVendorList());
		assertNull(catManager.getCategory("code1"));
		assertNull(catManager.getCategory("xxx"));
		assertNotNull(catManager.getCategory("c12"));
		assertNotNull(catManager.getCategory("C12"));
	}
	
	@Test
	public void testUpdateCategory(){
		Category cat = catManager.getCategory("c11");
		cat.setCategoryName("Updated Name");
		try {
			catManager.updateCategory(cat);
			assertTrue(catManager.getCategory("c11").getCategoryName().equals("Updated Name"));
		} catch (BadCategoryException e) {
			fail();
		}
		try {
			Category cat2 = new Category("test1", "testName");
			catManager.updateCategory(cat2);
			fail();
		} catch (BadCategoryException e) {
			assertTrue(e.getMessage().equals(Constants.CONST_CAT_ERR_NOTEXIST));
		}
	}
}
