/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
 *
 * @author misitesawn
 */
public class BadProductException extends Exception {

    public BadProductException() {
    }

    public BadProductException(String msg) {
        super(msg);
        
    }
    
   
}
