/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.exception;

/**
 *
 * @author hendry on behalf of misite
 */
public class BadDiscountException extends Exception {
     public BadDiscountException()
    {
        
    }
    
    public BadDiscountException(String msg)
    {
        super(msg);
    }
}
