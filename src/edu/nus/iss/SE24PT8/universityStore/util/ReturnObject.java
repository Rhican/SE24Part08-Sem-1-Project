/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

/**
 *
 * @author misitesawn
 */
public class ReturnObject<E > {
    public boolean success;
    public String message;
    public E data;

    
    //By Mugunthan - Added constructor and getter setters
    public ReturnObject(boolean success, String message, E data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }    
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
    
    
}
