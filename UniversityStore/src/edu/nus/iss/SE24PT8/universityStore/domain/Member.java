/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class Member extends Customer{
    private int loyaltyPoints;
    
    public Member(String customerName,String customerID,int loyaltyPoints) throws BadMemberRegistrationException 
    {
        super(customerName,customerID);
        String error = null;
        if (loyaltyPoints <-1)
            error = Constants.CONST_MEMBER_ERR_INVALIDLOYALTYPOINT;
        if (error != null)
        {
            throw new BadMemberRegistrationException (error);
        }
        
        this.loyaltyPoints = loyaltyPoints;
    }

    public ReturnObject redeemPoints(int points){
        
        if(points>loyaltyPoints){
            return new ReturnObject<>(false, "Points to be redeemed more than available", null);
        }
        
        loyaltyPoints=loyaltyPoints-points;
        return new ReturnObject<>(true, "Points deducted", this);
    }
    
    public int getLoyaltyPoints()
    {
        return this.loyaltyPoints;
    }
    
    public void setLoyaltyPoints(int loyaltyPoints)
    {
        this.loyaltyPoints=loyaltyPoints;
    }
    
    @Override
    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		super.equals(other);
		if (loyaltyPoints != other.loyaltyPoints)
			return false;
		return true;
	}
    
     @Override
     public String toString () {
        return (super.toString () + "," + loyaltyPoints );
    }
   
}
