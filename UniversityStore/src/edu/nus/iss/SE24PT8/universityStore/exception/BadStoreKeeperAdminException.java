/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.exception;

/**
 *
 * @author THIRILWIN
 * @created-date 27-march-2016
 */
public class BadStoreKeeperAdminException extends Exception {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public BadStoreKeeperAdminException(){
   }
   
   public BadStoreKeeperAdminException(String message){
       super(message);
   }
}
