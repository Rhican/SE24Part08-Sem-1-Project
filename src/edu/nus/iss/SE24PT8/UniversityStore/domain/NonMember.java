
package edu.nus.iss.SE24PT8.UniversityStore.domain;

import edu.nus.iss.SE24PT8.UniversityStore.exception.BadMemberRegistrationException;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class NonMember extends Customer{
    
    public NonMember(String customerName,String customerID) throws BadMemberRegistrationException
    {
        super(customerName,customerID);
    }
    
    @Override
    public String toString () {
        return (super.toString ());
    }
    
    
    
}
