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
public class ReturnObject {
    private boolean success;
    private String message;
    private Object data;

    
    //By Mugunthan - Added constructor and getter setters
    public ReturnObject(boolean success, String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
}
