package edu.nus.iss.SE24PT8.universityStore.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

/**
*
* @author Zehua
*/
public class TransactionTest {

	private Transaction trans1, trans2, trans3;
	private static int passCount = 0, passTotal = 10;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		passCount = 0;
		passTotal = 9;
		
		//Ensure test member are in system
		MemberManager memberManager = MemberManager.getInstance();
		if (memberManager.getMember("100001") == null ) MemberManager.getInstance().addMember("User1", "100001");
		if (memberManager.getMember("100002") == null ) MemberManager.getInstance().addMember("User2", "100002");
		if (memberManager.getMember("100003") == null ) MemberManager.getInstance().addMember("User3", "100003");
		
		//Ensure test product are in system
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
	}
	
	@Before
	public void setUp() throws Exception {
		trans1 = new Transaction();
		trans2 = new Transaction();
		trans3 = new Transaction();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransaction() {
		Transaction trans = new Transaction();
		assertTrue(trans != null);
		System.out.println( (++passCount) + ". Pass Transaction Constructor");
	}

	@Test
	public void testSetDate() {
		Date today = new Date();
		Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24));
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		trans1.setDate(yesterday);
		trans2.setDate(today);
		trans3.setDate(tomorrow);
		
		assertTrue(trans1.getDate() == yesterday);
		assertTrue(trans2.getDate() == today);
		assertTrue(trans3.getDate() == tomorrow);
		System.out.println((++passCount) + ". Pass Transaction Date Operations");
	}

	@Test
	public void testSetDiscount() {
		Discount defaultDiscount = trans2.getDefaultDiscount();
		trans1.setDiscount(null);
		trans2.setDiscount(defaultDiscount);
		trans3.setDiscount(new Discount());
		
		assertTrue(trans1.getDiscount() == null);
		assertTrue(trans2.getDiscount() == defaultDiscount);
		assertTrue(trans3.getDiscount() != null);
		assertTrue(trans2.getDiscount() != trans3.getDiscount());		
		System.out.println((++passCount) + ". Pass Transaction Discount Operations");
	}
	
		
	@Test
	public void testSetMember() {
		trans1.setMember("");
		assertTrue(trans1.getMember() == null);
		
		trans1.setMember("100001");
		trans2.setMember("100002");
		trans3.setMember("100003");
		
		assertTrue(trans1.getMember().getID().equals("100001"));
		assertTrue(trans2.getMember().getID().equals("100002"));
		assertTrue(trans3.getMember().getID().equals("100003"));
		
		assertTrue(trans1.getMemberID().equals("100001"));
		assertTrue(trans2.getMemberID().equals("100002"));
		assertTrue(trans3.getMemberID().equals("100003"));
		System.out.println((++passCount) + ". Pass Transaction Member Operations");
	}
	
	
	// ---- Sale Item test
	@Test
	public void testSaleItemOperations() {
		
		try {
			trans1.addSaleItem("CLO/1", 1);
			trans2.addSaleItem("MUG/1", 2);
			trans3.addSaleItem("STA/1", 3);
		} catch (TransactionException e) {
			fail("Fail to add sale item to transaction by product id and quantity");
		}
		
		try {
			trans1.addSaleItem(ProductManager.getInstance().getProductByID("MUG/1"), 1);
			trans2.addSaleItem(ProductManager.getInstance().getProductByID("STA/1"), 2);
			trans3.addSaleItem(ProductManager.getInstance().getProductByID("CLO/1"), 3);			
		} catch (TransactionException e) {
			fail("Fail to add sale item to transaction by product and quantity");
		}
		
		SaleItem s1, s2, s3, s4;
		s1 = trans1.getSaleItem("CLO/1");
		s2 = trans3.getSaleItem("CLO/1");
		s3 = trans2.getSaleItem("MUG/1");
		s4 = trans1.getSaleItem("MUG/1");
		
		assertTrue(s1 != null);
		assertTrue(s2 != null);
		assertTrue(s3 != null);
		assertTrue(s4 != null);
		
		assertTrue(s1.getProductID().equals(s2.getProductID()));
		assertTrue(s3.getProductID().equals(s4.getProductID()));		
		
		ArrayList<SaleItem> saleitems1 = trans1.getSaleItems();
		ArrayList<SaleItem> saleitems2 = trans2.getSaleItems();
		ArrayList<SaleItem> saleitems3 = trans3.getSaleItems();
		
		assertTrue(saleitems1.size() == 2);
		assertTrue(saleitems2.size() == 2);
		assertTrue(saleitems3.size() == 2);
		
		s1.changeQuantity(3);;
		s2.changeQuantity(2);;
		s3.changeQuantity(1);;
		assertTrue(s1.getSaleQuantity() == 3);
		assertTrue(s2.getSaleQuantity() == 2);
		assertTrue(s3.getSaleQuantity() == 1);
		
		try {
			trans1.removeSaleItem(s1);
			trans2.removeSaleItem(s3);
			trans3.removeSaleItem(s2);
		} catch (TransactionException e) {
			fail("Fail to remove sale item from transaction");
		}
		
		saleitems1 = trans1.getSaleItems();
		saleitems2 = trans2.getSaleItems();
		saleitems3 = trans3.getSaleItems();
		assertTrue(saleitems1.size() == 1);
		assertTrue(saleitems2.size() == 1);
		assertTrue(saleitems3.size() == 1);

		System.out.println((++passCount) + ". Pass Transaction Saleitems Operations");
	}
	
	// ---- Redeemed Point  test
	@Test
	public void testRedeemedPointOperations() {
		trans1.setRedeemedPoint(10);
		trans2.setRedeemedPoint(20);
		trans3.setRedeemedPoint(30);
		
		assertTrue(trans1.getRedeemedPoint() == 10);
		assertTrue(trans2.getRedeemedPoint() == 20);
		assertTrue(trans3.getRedeemedPoint() == 30);
		
		double amount1 = trans1.getRedeemedAmount();
		double amount2 = trans2.getRedeemedAmount();
		double amount3 = trans3.getRedeemedAmount();	
		
		// To avoid exact double value comparing, use absolute difference of two double values
		assertTrue(Math.abs(amount1 * 2.0f - amount2) < 0.0001f);
		assertTrue(Math.abs(amount1 * 3.0f - amount3) < 0.0001f);
		
		trans1.setRedeemedPoint(20);
		trans2.setRedeemedPoint(20);
		trans3.setRedeemedPoint(20);
		amount1 = trans1.getRedeemedAmount();
		amount2 = trans2.getRedeemedAmount();
		amount3 = trans3.getRedeemedAmount();
		
		assertTrue(Math.abs(amount1 - amount2) < 0.0001f);
		assertTrue(Math.abs(amount1 - amount3) < 0.0001f);
		
		System.out.println((++passCount) + ". Pass Transaction Redeemed Point Operations");
	}	
	
	
	// Computation 	
	@Test
	public void testGetTotalAmount() {

		try {
			for(SaleItem saleItem : trans1.getSaleItems())
				trans1.removeSaleItem(saleItem);
			for(SaleItem saleItem : trans2.getSaleItems())
				trans1.removeSaleItem(saleItem);
			for(SaleItem saleItem : trans3.getSaleItems())
				trans1.removeSaleItem(saleItem);
		} catch (TransactionException e) {
			fail("Fail to remove sale item from transaction");
		}
		
		try {
			trans1.addSaleItem("CLO/1", 1);
			trans2.addSaleItem("CLO/1", 2);
			trans3.addSaleItem("CLO/1", 3);
			
			trans1.addSaleItem("MUG/1", 1);
			trans2.addSaleItem("MUG/1", 2);
			trans3.addSaleItem("MUG/1", 3);
			
			trans1.addSaleItem("STA/1", 1);
			trans2.addSaleItem("STA/1", 2);
			trans3.addSaleItem("STA/1", 3);
		} catch (TransactionException e) {
			fail("Fail to add sale item to transaction by product id and quantity");
		}
		
		trans1.setDiscount(null);	//discount object null, will use default discount
		trans2.setDiscount(null);	//discount object null, will use default discount
		trans3.setDiscount(null);	//discount object null, will use default discount
		
		double total1 = trans1.getTotalAmount();
		double total2 = trans2.getTotalAmount();
		double total3 = trans3.getTotalAmount();
			
		assertTrue(Math.abs(total1 * 2 - total2) < 0.0001f);
		assertTrue(Math.abs(total1 * 3 - total3) < 0.0001f);
		System.out.println((++passCount) + ". Pass Transaction Get Total Amount.");
		
		double discountAmount1 = trans1.getDiscountAmount();
		double discountAmount2 = trans2.getDiscountAmount();
		double discountAmount3 = trans3.getDiscountAmount();
		
		trans1.setMember("100001");	//use member discount
		trans2.setDiscount(null);	//discount object null, will use default discount
		trans3.setDiscount(trans3.getDefaultDiscount()); //use default discount
		
		double discountAmount1New = trans1.getDiscountAmount();
		double discountAmount2New = trans2.getDiscountAmount();
		double discountAmount3New = trans3.getDiscountAmount();
		assertTrue(discountAmount1New >= discountAmount1);
		assertTrue(Math.abs(discountAmount2New - discountAmount2) < 0.0001f);
		assertTrue(Math.abs(discountAmount3New - discountAmount3) < 0.0001f);
		System.out.println((++passCount) + ". Pass Transaction Get Discount Amount.");
		
		trans1.setRedeemedPoint(0);
		trans2.setRedeemedPoint(0);
		trans3.setRedeemedPoint(0);
		
		double netAmount1 = trans1.getNetAmount();
		double netAmount2 = trans2.getNetAmount();
		double netAmount3 = trans3.getNetAmount();
		
		assertTrue(Math.abs(total1 - discountAmount1New - netAmount1) < 0.0001f);
		assertTrue(Math.abs(total2 - discountAmount2New - netAmount2) < 0.0001f);
		assertTrue(Math.abs(total3 - discountAmount3New - netAmount3) < 0.0001f);
		System.out.println((++passCount) + ". Pass Transaction Get Net Amount.");
	}

	// ---- Close
	@Test
	public void testClose() {
			boolean isClosed1 = trans1.isClosed();
			boolean isClosed2 = trans2.isClosed();
			boolean isClosed3 = trans3.isClosed();
			assertTrue(!isClosed1);
			assertTrue(!isClosed2);
			assertTrue(!isClosed3);
			
			long id1 = trans1.getId();
			long id2 = trans2.getId();
			long id3 = trans3.getId();
			assertTrue(id1 == 0);
			assertTrue(id2 == 0);
			assertTrue(id3 == 0);
			
			try {
				trans1.close(true, true);
				trans2.close(true, false);
				trans3.close(false, false);
			} catch (TransactionException e) {
				fail("Fail to close transaction.");
			}
			
			isClosed1 = trans1.isClosed();
			isClosed2 = trans2.isClosed();
			isClosed3 = trans3.isClosed();
			assertTrue(isClosed1);
			assertTrue(isClosed2);
			assertTrue(isClosed3);
			
			id1 = trans1.getId();
			id2 = trans2.getId();
			id3 = trans3.getId();
			assertTrue(id1 != 0);
			assertTrue(id2 != 0);
			assertTrue(id3 != 0);
			
			System.out.println((++passCount) + ". Pass Transaction Close.");
		}
	
	// ---- Close
	@Test
	public void testSummary() {
			System.out.println("Test Summary: \n\t Pass: " + passCount + " out of " + passTotal + ".");
		}
}
