/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.ArrayList;

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
        //transactions = new ArrayList();
        transactions=DataAdapter.loadTransactions();
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
    
}