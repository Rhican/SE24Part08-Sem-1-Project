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
import edu.nus.iss.SE24PT8.universityStore.domain.MemberDiscount;
import edu.nus.iss.SE24PT8.universityStore.domain.OtherDiscount;
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
	public void testAddDiscount() throws BadDiscountException {

		String disCode = "";

		try {
			disCode = discounts.get(0).getDiscountCode();
		} catch (NullPointerException e) {

		}

		try {
			mgr.addNewiDscount(disCode, "Disoucnt item o1", 10, new Date(), 0, true, true, "A");

		} catch (BadDiscountException e) {
			String message = e.getLocalizedMessage();
			assertEquals(message, Constants.CONST_DISCOUNT_ERR_DISCOUNTCODEEXIST);
		}
		
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
 
     
}
