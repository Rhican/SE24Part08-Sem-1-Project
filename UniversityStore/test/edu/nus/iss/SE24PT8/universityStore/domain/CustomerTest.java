
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.domain.Customer;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
         cus1= new Customer("Customer1","R64565FG41");
         cus2= new Customer("Customer2","F425637431562");
    }
    
    @After
    public void tearDown() {
        cus1=null;
        cus2=null;
    }

    @Test
    public void testGetCustomerName(){
        assertEquals("Customer1",cus1.getName());
        assertEquals("Customer2",cus2.getName());
    }
    
    @Test
    public void testGetPassword(){
       assertEquals("R64565FG41",cus1.getID());
       assertEquals("F425637431562",cus2.getID());
    }
   
    @Test
    public void testCustomer()
    {
        assertEquals("Customer1",cus1.getName());
        assertEquals("R64565FG41",cus1.getID());
        assertEquals("Customer2",cus2.getName());
        assertEquals("F425637431562",cus2.getID());
        assertSame(cus1,cus1);
        assertSame(cus2,cus2);
        
    }
    
    @Test
    public void testEquals() throws BadMemberRegistrationException{
        assertEquals(cus1, new Customer("Customer1","R64565FG41"));
        assertEquals(cus2,new Customer("Customer2","F425637431562"));
    }
    
    @Test
    public void testToString() {
        cus1.show();
        cus2.show();
    }
}
