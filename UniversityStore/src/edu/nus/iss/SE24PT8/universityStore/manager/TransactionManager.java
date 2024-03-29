/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.domain.TransactionInterface;
import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;

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
		if (Instance == null)
			Instance = new TransactionManager();
		return Instance;
	}

	private TransactionManager() {
		transactions = DataAdapter.loadTransactions();
	}

	public boolean closeTransaction(TransactionInterface transactionInterface) throws TransactionException {
		Transaction transaction = (Transaction) (transactionInterface);
		if (transaction == null)
			return false;
		if (transaction.close(false, false)) {
			if (transaction != null)
				DataAdapter.appendTransaction(transaction);
			return true;
		}
		return false;
	}

	public Transaction getTransaction(long id) {
		for (Transaction transaction : transactions) {
			if (transaction.getId() == id)
				return transaction;
		}
		return null;
	}

	public TransactionInterface getNewTransaction() {
		Transaction newTransaction = new Transaction();
		transactions.add(newTransaction);
		return newTransaction;
	}

	public final ArrayList<Transaction> getAllTransactions() {
		return transactions;
	}

	public final ArrayList<Transaction> getAllTransactions(Date start, Date end) {
		ArrayList<Transaction> transactionsFiltered = new ArrayList<Transaction>();
		Date endPlus1 = new Date(end.getTime() + (1000 * 60 * 60 * 24));
		for (Transaction transaction : transactions) {
			Date date = transaction.getDate();
			if (date.after(start) && date.before(endPlus1)) {
				transactionsFiltered.add(transaction);
			}
		}
		return transactionsFiltered;
	}
	/**
	 * Search Transaction report,
	 *  In range (inclusive) between start and end dates.
	 * 
	 */
	public final String[][] getTransactionReport(Date start, Date end, Vector<String> columnNames) {
		ArrayList<Transaction> transactions = getAllTransactions(start, end);
		ArrayList<String[]> reportInList = new ArrayList<String[]>();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd /yyyy HH:mm:ss");

		for (Transaction transaction : transactions) {
			ArrayList<SaleItem> saleitems = transaction.getSaleItems();
			String date = df.format(transaction.getDate());
			String id = String.valueOf(transaction.getId());
			for (SaleItem saleitem : saleitems) {
				Product product = saleitem.getProduct();
				String[] row = new String[6];
				row[0] = product.getProductId();
				row[1] = product.getProductName();
				row[2] = product.getBriefDesp();
				row[3] = String.valueOf(saleitem.getSaleQuantity());
				row[4] = date;
				row[5] = id;
				reportInList.add(row);
			}
		}
		Collections.sort(reportInList, (row1, row2) -> ((String) row1[0]).compareTo(((String) row2[0]))); // Sort by product ID
		String[][] reportInArray = new String[reportInList.size()][];
		int index = 0;
		for (String[] row : reportInList) {
			reportInArray[index++] = row;
		}

		if (columnNames != null) {
			columnNames.clear();
			columnNames.addElement("Product ID");
			columnNames.addElement("Product Name");
			columnNames.addElement("Description");
			columnNames.addElement("Quantity");
			columnNames.addElement("Transaction Date");
			columnNames.addElement("Transaction No");
		}
		return reportInArray;
	}

}
