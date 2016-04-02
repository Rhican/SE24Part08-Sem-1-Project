/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.exception;

/**
 *
 * @author THIRILWIN
 * @created date 26 March 2016
 */
public class BadMemberRegistrationException extends Exception {
    public BadMemberRegistrationException(){
        
    }
    
    public BadMemberRegistrationException(String msg){
        super(msg);
    }
}
