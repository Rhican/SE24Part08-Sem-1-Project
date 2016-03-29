package edu.nus.iss.SE24PT8.UniversityStore.manager;

import edu.nus.iss.SE24PT8.UniversityStore.domain.Member;
import edu.nus.iss.SE24PT8.UniversityStore.exception.BadMemberRegistrationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author THIRILWIN
 *  @date 2016-March-17
 */
public class MemberManagerTest {
    
    public MemberManagerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
     public void testMemberManagerInstance() {
       System.out.println("1:testMemberManagerInstance");
       MemberManager memberManager=MemberManager.getInstance();
       assertFalse(memberManager.getMembers()==null);
       assertFalse(memberManager.getMembers().size()==0);
     }

     @Test
     public void testAddMember() throws BadMemberRegistrationException
     {
        System.out.println("2:testAddMember");
        MemberManager memberManager=MemberManager.getInstance();
        if(memberManager.getMember( "G6365444U")==null)
        {
        memberManager.addMember("ThiriLwin","G6365444U");
        }
        if(memberManager.getMember( "G6365499U")==null)
        {
        memberManager.addMember("Mi Site","G6365499U");
        }
        if(memberManager.getMember("G6365498U")==null)
        {
        memberManager.addMember("Theingi","G6365498U");
        }
        
        Member m=memberManager.getMember("G6365499U");
        assertEquals(m.getCustomerName(),"Mi Site");
     }
     
     @Test
     public void testRemoveMember() throws BadMemberRegistrationException
     {
        System.out.println("3:testRemoveMember");
        MemberManager memberManager=MemberManager.getInstance();
        if(memberManager.getMember( "A123456")==null)
        {
        memberManager.addMember("FirstMember", "A123456");
        }
        if(memberManager.getMember("B123456")==null)
        {
        memberManager.addMember("SecondMember", "B123456");
        }
        if(memberManager.getMember("C123456") == null)
        {
        memberManager.addMember("Third member", "C123456");
        }
        memberManager.removeMember("A123456");
        assertNull( memberManager.getMember("A123456"));
     }
     
     
     @Test
     public void testisFirstTransaction() throws BadMemberRegistrationException
     {
          System.out.println("5:testisFirstTransaction");
          MemberManager memberManager=MemberManager.getInstance();
          if(memberManager.getMember("B123456") == null)
          {
            memberManager.addMember("SecondMember", "B123456");
          }
          assertTrue(memberManager.isFirstTransaction("B123456"));
     }
     
     @Test
     public void testShowMembers()
     {
        System.out.println("6:testShowMembers");
        MemberManager memberManager=MemberManager.getInstance();
        memberManager.showMembers();
     }

     @Test
     public void testGetMember() throws BadMemberRegistrationException{
         System.out.println("7:testGetMember");
          MemberManager memberManager=MemberManager.getInstance();
          if(memberManager.getMember("B123456")==null)
          {
          memberManager.addMember("SecondMember", "B123456");
          }
          Member m=memberManager.getMember("B123456");
          assertEquals(m.getCustomerName(),"SecondMember");
     }
  
     @Test
     public void testAddLoyaltyPoints() throws BadMemberRegistrationException
     {
          System.out.println("8:testAddLoyaltyPoints");
          MemberManager memberManager=MemberManager.getInstance();
          if(memberManager.getMember("LPOINT123") == null)
          {
            memberManager.addMember("LPoint test account", "LPOINT123");
            memberManager.addLoyaltyPoints("LPOINT123", 1000);
            Member m=memberManager.getMember("LPOINT123");
            assertEquals(m.getLoyaltyPoints(),1000);
            memberManager.addLoyaltyPoints("LPOINT123", 750);
            Member m2=memberManager.getMember("LPOINT123");
            assertEquals(m2.getLoyaltyPoints(),1750);
          }
     }
     
     
      @Test
     public void testRedeemPoints() throws BadMemberRegistrationException
     {
          System.out.println("8:testRedeemPoints");
          MemberManager memberManager=MemberManager.getInstance();
         if(memberManager.getMember("L123") == null)
          {
          memberManager.addMember("LoyaltyTest", "L123");
          memberManager.addLoyaltyPoints("L123", 1000);
          Member m=memberManager.getMember("L123");
          assertEquals(m.getLoyaltyPoints(),1000);
          memberManager.redeemPoints("L123", 750);
          Member m2=memberManager.getMember("L123");
          assertEquals(m2.getLoyaltyPoints(),250);
          }
     }
}
