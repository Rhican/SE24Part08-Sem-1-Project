
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class Customer {
    private String customerName;
    private String customerID;
    
    public Customer(String customerName,String customerID) throws BadMemberRegistrationException
    {
       // Name length shoud not exceed more than 20 char
       // id lenght should not exceed more than 20 char
       String error = null;
        if (customerID == null)
             error = Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
        else if(customerID.equals(""))
            error = Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
        else if (customerName == null)
             error = Constants.CONST_MEMBER_ERR_INVALIDMEMBERNAME;
        else if(customerName.equals(""))
            error = Constants.CONST_MEMBER_ERR_INVALIDMEMBERNAME;
        else if(customerName.length()>20)
        {
            error=Constants.CONST_MEMBER_ERR_MEMBERNAMELENGTH;
        }
       else if(customerID.length()>20)
        {
            error=Constants.CONST_MEMBER_ERR_MEMBERIDLENGTH;
        }
        if (error != null)
        {
            throw new BadMemberRegistrationException (error);
        }
        this.customerName=customerName;
        this.customerID = customerID;
    }
    
    public String getCustomerName()
    {
        return this.customerName;
    }
    
    public String getCustomerID()
    {
        return this.customerID;
    }
    
    public String setCustomerName(String customerName)
    {
        return this.customerName=customerName;
    }
     
     public boolean equals(Object customer){
    	if (customer instanceof Customer){
    		Customer p = (Customer)customer;
    	   	if (this.customerName.equals(p.customerName)
    			&& this.getCustomerID().equals(p.getCustomerID()))
                            return true;
    	}
    	return false;
    }
     
     @Override
    public String toString () {
        String fullName = customerName;
        if (customerName != null) {
            fullName += "," + customerID;
        }
        return (fullName);
    }
    
    public void show () {
        System.out.println(this.toString());
    }
    
 
}
