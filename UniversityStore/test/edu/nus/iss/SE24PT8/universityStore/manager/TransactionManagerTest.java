package edu.nus.iss.SE24PT8.universityStore.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.domain.TransactionInterface;
import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;
import junit.framework.TestCase;

/**
*
* @author Zehua
*/
public class TransactionManagerTest extends TestCase{
	
	@Before
	public void setUp() throws Exception {
		if (CategoryManager.getInstance().getCategory("CLO") == null) {
			CategoryManager.getInstance().addCategory("CLO", "test data- CLO");
		}
		if (ProductManager.getInstance().getProductByID("CLO/1") == null) {
			try { 
				ProductManager.getInstance().addNewProduct("Centenary Jumper", "A really nice momento", 100, 21.45f,
						"1234", 10, 100, "CLO");
			} catch (Exception e) {
				System.out.println("Fail to Create product in setup.");
				fail("Fail to Create product in setup");
			}
		}
	}
	
	@Test
	public void testGetInstance() {
		TransactionManager transactionManager = TransactionManager.getInstance();
		assertTrue(transactionManager != null);
		System.out.println("Pass Transaction Manager GetInstance.");
	}

	@Test
	public void testTransactionOperations() {
		TransactionManager transactionManager = TransactionManager.getInstance();
		ArrayList<Transaction> saleitems = transactionManager.getAllTransactions();
		int size1 = saleitems.size();
				
		TransactionInterface tran1 = transactionManager.getNewTransaction();
		TransactionInterface tran2 = transactionManager.getNewTransaction();
		TransactionInterface tran3 = transactionManager.getNewTransaction();
		assertTrue(tran1 != null);
		assertTrue(tran2 != null);
		assertTrue(tran3 != null);
		System.out.println("Pass TransactionManager getNewTransaction.");
		
		Transaction trans1 = (Transaction) tran1;
		Transaction trans2 = (Transaction) tran2;
		Transaction trans3 = (Transaction) tran3;
		
		assertTrue(trans1 != null);
		assertTrue(trans2 != null);
		assertTrue(trans3 != null);
		
		try {
			tran1.addSaleItem(ProductManager.getInstance().getProductByID("CLO/1"), 1);
			trans2.addSaleItem("CLO/1", 1);
			trans3.addSaleItem("CLO/1", 1);
			transactionManager.closeTransaction(tran1);
			transactionManager.closeTransaction(tran2);
			transactionManager.closeTransaction(tran3);
		} catch (TransactionException e) {
			fail("TransactionException");
		}
		System.out.println("Pass TransactionManager closeTransaction.");
		
		assertTrue(tran1.getId() > 0);
		assertTrue(tran2.getId() > 0);
		assertTrue(trans3.isClosed());
		
		ArrayList<Transaction> saleitems2 = transactionManager.getAllTransactions();
		assertTrue(saleitems2.size() == size1 + 3);
		
		Date today = new Date();
		Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24));
		ArrayList<Transaction> saleitemsToday = transactionManager.getAllTransactions(yesterday, today);
		assertTrue(saleitemsToday.size() >= 3);
		
		Vector<String> columns = new Vector<String>();
		String[][] report = transactionManager.getTransactionReport(yesterday, today,  columns);
		assertTrue(saleitemsToday.size() <= report.length);
		
		System.out.println("Pass All TransactionManager Transaction Operations.");
	}
}
