/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.ArrayList;
import java.util.Date;

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
    
    public void closeTransaction(Transaction transaction) {
    	transaction.close();
        DataAdapter.appendTransaction(transaction);
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
    
    
}
