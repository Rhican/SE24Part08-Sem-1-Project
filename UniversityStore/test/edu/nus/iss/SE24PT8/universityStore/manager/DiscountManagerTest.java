/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package edu.nus.iss.SE24PT8.universityStore.manager;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.domain.Customer;
import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author misitesawn
 */
public class DiscountManagerTest  extends TestCase {
    DiscountManager mgr = DiscountManager.getInstance();
    ArrayList<Discount> discounts = mgr.getDiscountList();
    
    
    @Test
    public void testAddDiscount() throws BadDiscountException{
        //addNewiDscount(String discountCode, String description, int percentage, Date startDate, int period, boolean isStartDateAlways, boolean isPeriodAlways, String applicableFor)
        
        mgr.addNewiDscount("DIS_01", "Disoucnt item o1", 10 , new Date(), 0, true, true, Constants.CONST_DISCOUNT_ERR_DISCOUNTCODEEXIST);
        
    }
    
    @Test
    public void testGtMaxDiscount(){
    	DiscountManager mgr  = new DiscountManager();
    	
     
         
    }
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
     public void testMemberManagerInstance() {
       System.out.println("1 :testMemberManagerInstance");
       MemberManager memberManager1=MemberManager.getInstance();
       MemberManager memberManager2=MemberManager.getInstance();
       assertEquals(memberManager1,memberManager2);
     }

     @Test
     public void testAddMember() throws BadMemberRegistrationException
     {
        System.out.println("2:testAddMember");
        MemberManager memberManager=MemberManager.getInstance();
        if(memberManager.getMember("Q00015123")==null)
        {
        memberManager.addMember("Test member 1","Q00015123");
        }
        if(memberManager.getMember("Q00015124")==null)
        {
        memberManager.addMember("Test member 1","Q00015124");
        }
        if(memberManager.getMember("Q00015125")==null)
        {
        memberManager.addMember("Lim","Q00015125");
        }
        
        Member m=memberManager.getMember("Q00015124");
        assertEquals(m.getName(),"Test member 1");
     }
     
     @Test
     public void testRemoveMember() throws BadMemberRegistrationException
     {
        System.out.println("3:testRemoveMember");
        MemberManager memberManager=MemberManager.getInstance();
        if(memberManager.getMember( "A123456")==null)
        {
        memberManager.addMember("MemberTest1", "A123456");
        }
        if(memberManager.getMember("B123456")==null)
        {
        memberManager.addMember("MemberTest2", "B123456");
        }
        if(memberManager.getMember("C123456") == null)
        {
        memberManager.addMember("MemberTest3", "C123456");
        }
        memberManager.removeMember("A123456");
        assertNull( memberManager.getMember("A123456"));
     }
     
     
     @Test
     public void testisFirstTransaction() throws BadMemberRegistrationException
     {
          System.out.println("5:testisFirstTransaction");
          MemberManager memberManager=MemberManager.getInstance();
          if(memberManager.getMember("D123456") == null)
          {
        	memberManager.addMember("MemberTest4", "D123456");
          }
          assertTrue(memberManager.isFirstTransaction("D123456"));
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
          if(memberManager.getMember("J00015123")==null)
          {
          memberManager.addMember("J00015123", "J00015123");
          }
          Member m=memberManager.getMember("J00015123");
          assertEquals(m.getName(),"J00015123");
     }
  
     @Test
     public void testAddLoyaltyPoints() throws BadMemberRegistrationException
     {
          System.out.println("8:testAddLoyaltyPoints");
          MemberManager memberManager=MemberManager.getInstance();
          if(memberManager.getMember("Point12345") == null)
          {
            memberManager.addMember("LoyaltyAccount", "Point12345");
            memberManager.addLoyaltyPoints("Point12345", 1000);
            Member m=memberManager.getMember("Point12345");
            assertEquals(m.getLoyaltyPoints(),1000);
            memberManager.addLoyaltyPoints("Point12345", 750);
            Member m2=memberManager.getMember("Point12345");
            assertEquals(m2.getLoyaltyPoints(),1750);
          }
     }
     
     
      @Test
     public void testRedeemPoints() throws BadMemberRegistrationException
     {
         System.out.println("8:test  Redeem Points");
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
