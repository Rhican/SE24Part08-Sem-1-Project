/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.exception;

/**
 *
 * @author Mugunthan
 */
public class BadVendorException extends Exception{
    public BadVendorException () {
    }

    public BadVendorException (String msg) {
        super (msg);
    }
}
