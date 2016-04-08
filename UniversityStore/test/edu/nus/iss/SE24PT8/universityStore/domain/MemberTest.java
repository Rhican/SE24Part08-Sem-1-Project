
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
        m1=new Member("Member Test Account1","G6365044U",100);
        m2=new Member("Member Test Account2","G6365099U",-1);
        m3=new Member("Member Test Account3","G6365098U",500);
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
        assertEquals("Member Test Account1",m1.getName());
        assertEquals("Member Test Account2",m2.getName());
    }
      
    @Test
    public void testGetCustomerID()
    {
        assertEquals("G6365044U",m1.getID());
        assertEquals("G6365099U",m2.getID());
    }
    
   @Test
    public void testEquals() throws BadMemberRegistrationException {
    Member m4 = new Member("Thiri1","G636044",100);
    Member m6 = new Member("TestAccount","TEST",  -1);
    assertFalse (m4.equals(m6));
    
    }
   
    @Test
    public void testToString()
    {
       m1.show();
       m2.show();
       m3.show();
    }

}
