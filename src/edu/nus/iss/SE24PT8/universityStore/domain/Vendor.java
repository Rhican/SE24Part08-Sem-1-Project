/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import java.util.Objects;
import edu.nus.iss.SE24PT8.universityStore.exception.BadVendorException;

/**
 *
 * @author Mugunthan
 */
public class Vendor   {
    private String vendorName;
    private String vendorDescription;

    public Vendor(String vendorName, String vendorDescription) throws BadVendorException{
        if (vendorName == null || vendorName.equals(""))
            throw new BadVendorException("No valid vendor name specified");
        this.vendorName = vendorName;
        this.vendorDescription = vendorDescription;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorDescription() {
        return vendorDescription;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.vendorName);
        hash = 67 * hash + Objects.hashCode(this.vendorDescription);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vendor other = (Vendor) obj;
        if (!Objects.equals(this.vendorName, other.vendorName)) {
            return false;
        }
        if (!Objects.equals(this.vendorDescription, other.vendorDescription)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String fullName = vendorName;
        if (vendorDescription != null) {
            fullName += " (" + vendorDescription + ")";
        }
        return (fullName);
    }
    
    public void show(){
        System.out.println(this.toString());
    }
    
 
    
}
