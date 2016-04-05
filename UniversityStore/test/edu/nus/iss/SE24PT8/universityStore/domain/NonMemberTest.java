
package edu.nus.iss.SE24PT8.universityStore.domain;


import edu.nus.iss.SE24PT8.universityStore.domain.NonMember;
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
public class NonMemberTest extends TestCase {
    
    NonMember m1,m2,m3;
    public NonMemberTest() {
    }
  
    @Before
    public void setUp() throws BadMemberRegistrationException {
        m1 = new NonMember("PUBLIC", "PUBLIC");
        m2 = new NonMember("PUBLIC", "TEST");
        m3 = new NonMember("PUBLIC", "PUBLIC");
    }
    
    @After
    public void tearDown() {
        m1=null;
        m2=null;
        m3=null;
    }

   @Test
    public void testEquals() {
  
    assertFalse (m1.equals(m2));
    assertEquals(m1,m3);
    }
  
    @Test
    public void testToString()
    {
        m1.show();
        m2.show();
        m3.show();
    }

}
