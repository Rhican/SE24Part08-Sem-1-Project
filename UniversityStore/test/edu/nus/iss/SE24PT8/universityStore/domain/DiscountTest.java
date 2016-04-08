/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMember;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.util.ApplicationConfig;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author misitesawn
 */
public class DiscountTest extends TestCase {
	Discount mdis1;
	Discount mdis2;
	Discount pdis3;
	Discount pdis4;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");	
		
		public DiscountTest() {
			
		}
		
	@Before
	public void setUp() throws ParseException {
		
		
		mdis1 = new MemberDiscount();
		mdis1.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
		mdis1.setDiscountCode("CHRIS");
		mdis1.setDiscountDes("Christmas Sale");
		mdis1.setDiscountPercent(20);
		mdis1.setIsStartDateAlways(false);
		mdis1.setDiscountStartDate(dateFormat.parse("2015-12-12"));
		mdis1.setDiscountPeriod(10);
		mdis1.setIsPeriodAlways(false);
		
		mdis2 = new MemberDiscount();
		mdis2.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
		mdis2.setDiscountCode("EXCM");
		mdis2.setDiscountDes("Member Special");
		mdis2.setDiscountPercent(20);
		mdis2.setIsStartDateAlways(true);
		mdis2.setDiscountStartDate(null);
		mdis2.setDiscountPeriod(0);
		mdis2.setIsPeriodAlways(true);
		
		pdis3 = new OtherDiscount();
		pdis3.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
		pdis3.setDiscountCode("SCH");
		pdis3.setDiscountDes("School Start Sale");
		pdis3.setDiscountPercent(30);
		pdis3.setIsStartDateAlways(false);
		pdis3.setDiscountStartDate(dateFormat.parse("2015-06-12"));
		pdis3.setDiscountPeriod(0);
		pdis3.setIsPeriodAlways(true);
		
		pdis4 = new OtherDiscount();
		pdis4.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
		pdis4.setDiscountCode("SUMMER");
		pdis4.setDiscountDes("SUMMER Sale");
		pdis4.setDiscountPercent(10);
		pdis4.setIsStartDateAlways(false);
		pdis4.setDiscountStartDate(dateFormat.parse("2015-03-01"));
		pdis4.setDiscountPeriod(30);
		pdis4.setIsPeriodAlways(false);
	
	}

		@After
		public void tearDown() {
			mdis1 = null;
			mdis2= null;
			pdis3=null;
			pdis4 = null;
			  
		}
		
		
		@Test
	public void testEquals() {
		assertEquals(mdis1.getClass(), new MemberDiscount().getClass());
	}
}

