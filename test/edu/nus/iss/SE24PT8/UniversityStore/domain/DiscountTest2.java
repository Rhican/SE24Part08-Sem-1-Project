/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMember;
import edu.nus.iss.SE24PT8.universityStore.domain.Customer;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.util.ApplicationConfig;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
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
 * @author hendry
 */
public class DiscountTest2 {
    
    public DiscountTest2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

 
    @Test
    public void hello() {
        DiscountManager dm=DiscountManager.getInstance();
        Customer nonmember;
        for(Discount dis:dm.getDiscountList()){
            System.out.println("Discount"+dis.getDiscountCode()+" , startdate:"+dis.getDiscountStartDate());
        }
        
        
        try{
         nonmember=new NonMember(Constants.CONST_CUST_NONMEMBER_ID,Constants.CONST_CUST_NONMEMBER_NAME);
        }catch(Exception e){
            System.out.println("Exception e"+e);
        }
        
        Discount disMemberFirstTime;
        Discount disMemberMax;
        Discount disNonMax;
        String discountCodeFirstTime=ApplicationConfig.getInstance().getValue(ApplicationConfig.KEY_MEMBER_FIRSTTIME_DISCOUNTCODE);
        
        System.out.println("Member Firsttime discount:"+discountCodeFirstTime);
        disMemberFirstTime=dm.getDiscountByCode(discountCodeFirstTime);
        System.out.println("Discount for first time is:"+disMemberFirstTime);
        
        
        //now getting maxium discount for member(except first discount base on date
        for(Discount discount:dm.getDiscountList()){
            //Skip public discounts
            if(discount.getApplicableFor().equalsIgnoreCase(Constants.CONST_CUST_TYPE_PUBLIC)){
                continue;
            }
            
            System.out.println("Discount:"+discount);

        }
        
        
        //Check if Discount is valid for today regardless for member or public
        Date today=new Date();
        for(Discount discount:dm.getDiscountList()){
            System.out.println("Discount:"+discount.getDiscountCode()+" is valid?"+discount.isDiscountValidForDate(today));
            ;
        }
        
        
        //Check max Discount
        Customer notMember;
        Discount maxDiscount;
        try{
            notMember=new NonMember(Constants.CONST_CUST_NONMEMBER_ID,Constants.CONST_CUST_NONMEMBER_NAME);
            maxDiscount=dm.getMaxDiscount(new Date(), notMember);
            System.out.println("Max discount is :"+maxDiscount);
        }catch(Exception e){
            
        }
        
        
        
        
        
    
    }
}
