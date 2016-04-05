
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.domain.Member;
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
public class MemberTest extends TestCase{
    
    Member m1,m2,m3;
    
    public MemberTest() {
    }
    
    @Before
    public void setUp() throws BadMemberRegistrationException {
        m1=new Member("ThiriLwin","G6365444U",100);
        m2=new Member("Mi Site","G6365499U",-1);
        m3=new Member("Theingi","G6365498U",500);
    }
    
    @After
    public void tearDown() {
        m1=null;
        m2=null;
        m3=null;
    }

    
    @Test
    public void testGetLoyaltyPoints()
    {
        assertEquals(100,m1.getLoyaltyPoints());
        assertEquals(-1,m2.getLoyaltyPoints());
        assertEquals(500,m3.getLoyaltyPoints());
    }
   
    @Test
    public void testGetCustomerName()
    {
        assertEquals("ThiriLwin",m1.getName());
        assertEquals("Mi Site",m2.getName());
    }
      
    @Test
    public void testGetCustomerID()
    {
        assertEquals("G6365444U",m1.getID());
        assertEquals("G6365499U",m2.getID());
    }
    
   @Test
    public void testEquals() throws BadMemberRegistrationException {
    Member m4 = new Member("Thiri","G636544",100);
    Member m5 = new Member("Thiri","G636544", 100);
    Member m6 = new Member("TestAccount","TEST",  -1);
    
    assertFalse (m4.equals(m6));
    assertEquals(m4,m5);
    
    }
   
    @Test
    public void testToString()
    {
       m1.show();
       m2.show();
       m3.show();
    }

}
