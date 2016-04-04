/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Zehua
 */
public class TransactionManager {
    private static TransactionManager Instance = null;
    private ArrayList<Transaction> transactions;
    
    /**
     *
     * @return
     */
    public static TransactionManager getInstance() {
        if(Instance == null) Instance = new TransactionManager();
        return Instance;
    }
        
    public TransactionManager() {
        transactions=DataAdapter.loadTransactions();
    }

    /**
     * Write To File
     * Must call after add, update, delete product item
     *
     */
    
    public boolean closeTransaction(Transaction transaction) {
    	if (transaction.close() ) {
    		DataAdapter.appendTransaction(transaction);
    		return true;
    	}
    	return false;
    }
    
    public Transaction getTransaction(long id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) return transaction;
        }
        return null;
    }
    
    public Transaction getNewTransaction() {
        Transaction newTransaction = new Transaction();
        transactions.add(newTransaction);
        return newTransaction;
    }  
    
    public final ArrayList<Transaction> getAllTransactions() {
    	return transactions;
    }
    
    public final ArrayList<Transaction> getAllTransactions(Date start, Date end) {
    	ArrayList<Transaction> transactionsFiltered = new ArrayList<Transaction>();
    	for(Transaction transaction : transactions) {
    		if (transaction.getDate().after(start) && transaction.getDate().before(end) )
    		{
    			transactionsFiltered.add(transaction);
    		}
    	}
    	return transactionsFiltered;
    }
    
    public final String[][] getTransactionReport(Date start, Date end, Vector<String> columnNames) {
    	ArrayList<Transaction> transactions = getAllTransactions(start, end);
    	ArrayList<String[]> reportInList = new ArrayList<String[]>();
    	for(Transaction transaction : transactions) {
    		ArrayList<SaleItem> saleitems = transaction.getSaleItems();
    		String date = transaction.getDate().toString();
    		String id = String.valueOf(transaction.getId());
    		for(SaleItem saleitem : saleitems) {
    			Product product = saleitem.getProduct();
    			String[] row = new String[5];
    			row[0] = product.getProductId(); 
    			row[1] = product.getProductName(); 
    			row[2] = product.getBriefDesp(); 
    			row[3] = id; 
    			row[4] = date; 
    			reportInList.add(row);    			
    		}
    	}
    	Collections.sort(reportInList, (row1, row2) -> ((String)row1[0]).compareTo( ((String)row2[0]))); // Sort by product ID
    	String[][] reportInArray = new String[reportInList.size()][];
    	int index = 0;
    	for(String[] row : reportInList) {
    		reportInArray[index++] = row;
    	}
    	
    	for(String[] row : reportInList) {
    		System.out.println( row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + row[3] + "\t" + row[4] + "\n" );
    	}
    	
    	if (columnNames != null) {
	    	columnNames.clear();
	    	columnNames.addElement("Product ID");
	    	columnNames.addElement("Product Name");
	    	columnNames.addElement("Product Description");
	    	columnNames.addElement("Transaction No");
	    	columnNames.addElement("Transaction Date");
    	}
    	//{"Product ID", "Product Name", "Product Description", "Transaction No", "Transaction Date"};
    	return reportInArray;
    }
    
}
