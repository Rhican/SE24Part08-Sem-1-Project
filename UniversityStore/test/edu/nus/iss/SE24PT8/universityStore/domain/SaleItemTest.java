package edu.nus.iss.SE24PT8.universityStore.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

/**
 *
 * @author Zehua
 */
public class SaleItemTest {

	SaleItem saleItem1, saleItem2;

	public SaleItemTest() {
	}

	@Before
	public void setUp() throws BadMemberRegistrationException {
		if (ProductManager.getInstance().getProductByID("CLO/1") == null) {
			try { 
				ProductManager.getInstance().addNewProduct("Centenary Jumper", "A really nice momento", 100, 21.45f,
						"1234", 10, 100, "CLO");
			} catch (Exception e) {
				System.out.println("Fail to Create product in setup.");
				fail("Fail to Create product in setup");
			}
		}
		if (ProductManager.getInstance().getProductByID("MUG/1") == null) {
			try {
				ProductManager.getInstance().addNewProduct("Centenary Mug", "A really nice mug this time", 250, 10.25f,
						"9876", 25, 150, "MUG");
			} catch (Exception e) {
				System.out.println("Fail to Create product in setup.");
				fail("Fail to Create product in setup");
			}
		}
		if (ProductManager.getInstance().getProductByID("STA/1") == null) {
			try {
				ProductManager.getInstance().addNewProduct("NUS Pen", "A really cute blue pen", 300, 30.50f,
						"12345678", 50, 100, "STA");
			} catch (Exception e) {
				System.out.println("Fail to Create product in setup.");
				fail("Fail to Create product in setup");
			}
		}
		saleItem1 = new SaleItem("CLO/1", 1);
		saleItem2 = new SaleItem(ProductManager.getInstance().getProductByID("MUG/1"), 2);
	}

	@After
	public void tearDown() {
		saleItem1 = null;
		saleItem2 = null;
	}

	@Test
	public void testSaleItemStringInt() {

		saleItem1 = new SaleItem("CLO/1", 1);
		saleItem2 = new SaleItem("MUG/1", 2); 
		
		assertNotEquals(saleItem1, null);
		assertNotEquals(saleItem2, null);
	}
	
	@Test
	public void testSaleItemProductInt() {
		saleItem1 = new SaleItem(ProductManager.getInstance().getProductByID("CLO/1"), 1);
		saleItem2 = new SaleItem(ProductManager.getInstance().getProductByID("MUG/1"), 2);
		
		assertNotEquals(saleItem1, null);
		assertNotEquals(saleItem2, null);
		
		int no1 = saleItem1.getSaleQuantity();
		int no2 = saleItem2.getSaleQuantity();
		assertEquals(no1, 1);
		assertEquals(no2, 2);
		assertNotEquals(no1, no2);
	}

	@Test
	public void testSetProductID() {
		String id1 = saleItem1.getProductID();
		String id2 = saleItem2.getProductID();
		
		saleItem1.setProductID("STA/1");
		saleItem2.setProductID(id1);
		
		String id1New = saleItem1.getProductID();
		String id2New = saleItem2.getProductID();

		assertEquals(id1, "CLO/1");
		assertEquals(id2, "MUG/1");
		assertEquals(id1New, "STA/1");
		assertEquals(id2New, "CLO/1");
		assertTrue(id1 != id1New);
		assertTrue(id2 != id2New);
	}

	@Test
	public void testGetProductID() {
		String id1 = saleItem1.getProductID();
		String id2 = saleItem2.getProductID();
		String id1_fromProduct = saleItem1.getProduct().getProductId();
		String id2_fromProduct = saleItem2.getProduct().getProductId();
		assertEquals(id1, "CLO/1");
		assertEquals(id1_fromProduct, "CLO/1");
		assertEquals(id2, "MUG/1");
		assertEquals(id2_fromProduct, "MUG/1");
	}

	@Test
	public void testGetSaleQuantity() {
		int no1 = saleItem1.getSaleQuantity();
		int no2 = saleItem2.getSaleQuantity();
		
		assertEquals(no1, 1);
		assertEquals(no2, 2);
	}

	@Test
	public void testChangeQuantity() {
		int no1 = saleItem1.getSaleQuantity();
		int no2 = saleItem2.getSaleQuantity();
		
		saleItem1.changeQuantity(no1 + no1);
		saleItem2.changeQuantity(no2 + no2);
		
		int no1New = saleItem1.getSaleQuantity();
		int no2New = saleItem2.getSaleQuantity();
		
		assertEquals(no1, 1);
		assertEquals(no2, 2);
		
		assertEquals(no1New, 2);
		assertEquals(no2New, 4);
		
		assertTrue(no1 != no1New);
		assertTrue(no2 != no2New);
	}

	
	@Test
	public void testGetProduct() {
		Product product1 = saleItem1.getProduct();
		Product product2 = saleItem2.getProduct();
		
		assertTrue(product1 != null);
		assertTrue(product2 != null);
		assertTrue(product1 != product2);
	}

	@Test
	public void testGetSubTotal() {
		saleItem1.setProductID("CLO/1");
		saleItem2.setProductID("CLO/1");
		saleItem1.changeQuantity(1);
		saleItem2.changeQuantity(1);
		assertTrue(saleItem1.getSubTotal() == saleItem2.getSubTotal());
		
		saleItem2.changeQuantity(2);
		assertTrue(saleItem1.getSubTotal() != saleItem2.getSubTotal());
		
	}

}
