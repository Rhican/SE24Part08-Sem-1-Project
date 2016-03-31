
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.domain.Customer;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author THIRILWIN
 * @created-date 2016-March-17
 */
public class CustomerTest extends TestCase {
    
    Customer cus1;
    Customer cus2;
    
    public CustomerTest() {
       
    }
   
    @Before
    public void setUp() throws BadMemberRegistrationException {
         cus1= new Customer("Ang Lee","R64565FG4");
         cus2= new Customer("Yan Martel","F42563743156");
    }
    
    @After
    public void tearDown() {
        cus1=null;
        cus2=null;
    }

    @Test
    public void testGetCustomerName(){
        assertEquals("Ang Lee",cus1.getCustomerName());
        assertEquals("Yan Martel",cus2.getCustomerName());
    }
    
    @Test
    public void testGetPassword(){
       assertEquals("R64565FG4",cus1.getCustomerID());
       assertEquals("F42563743156",cus2.getCustomerID());
    }
   
    @Test
    public void testCustomer()
    {
        assertEquals("Ang Lee",cus1.getCustomerName());
        assertEquals("R64565FG4",cus1.getCustomerID());
        assertEquals("Yan Martel",cus2.getCustomerName());
        assertEquals("F42563743156",cus2.getCustomerID());
        assertSame(cus1,cus1);
        assertSame(cus2,cus2);
        
    }
    
    @Test
    public void testEquals() throws BadMemberRegistrationException{
        
        assertEquals(cus1, new Customer("Ang Lee","R64565FG4"));
        assertEquals(cus2,new Customer("Yan Martel","F42563743156"));
    }
    
    @Test
    public void testToString() {
        cus1.show();
        cus2.show();
    }
}
