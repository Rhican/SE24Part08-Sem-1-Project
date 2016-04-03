/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class StoreKeeper implements Comparable<StoreKeeper>  {
    
    private String storeKeeperName;
    private String password;
    
    public StoreKeeper(String storeKeeperName,String password){
        this.storeKeeperName=storeKeeperName;
        this.password=password;
    }
    
   public String getstoreKeeperName(){
       return this.storeKeeperName;
   }
   public String getPassword(){
       return this.password;
   }
    
    public boolean equals(Object storekeeper){
    	if (storekeeper instanceof StoreKeeper){
    		StoreKeeper u = (StoreKeeper)storekeeper;
    	   	if (this.getstoreKeeperName().equals(u.storeKeeperName)
    			&& this.getPassword().equals(u.getPassword()))
                        return true;
    	}
    	return false;
    }
    
     @Override
    public String toString() {
        return (this.storeKeeperName);
    }
    
    public void show(){
        System.out.println(this.toString());
    }
   
    @Override
    public int compareTo(StoreKeeper other) {
        return (getstoreKeeperName().compareTo(other.getstoreKeeperName()));
    }
    
 
}
