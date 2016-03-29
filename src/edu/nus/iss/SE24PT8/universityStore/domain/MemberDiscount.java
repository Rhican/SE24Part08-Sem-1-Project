/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.domain;

import edu.nus.iss.SE24PT8.universityStore.util.Constants;

import java.util.Date;

/**
 *
 * @author misitesawn
 */
public class MemberDiscount extends Discount {
//    private  String applicableFor;
//  
//    public String getApplicableFor() {
//        return applicableFor;
//    }

    public MemberDiscount() {
    }

    public MemberDiscount(String discountCode, String discountDes, Date discountStartDate, int discountPeriod, int discountPercent,boolean isPeriodAlways) {
        super(discountCode, discountDes, discountStartDate, discountPeriod,discountPercent,isPeriodAlways,Constants.CONST_CUST_TYPE_MEMBER);
        
    }

    @Override
    public String toString() {
        return  super.toString() +  "MemberDiscount{" + "applicableFor=" + getApplicableFor() + '}';
    }

     
    
    
}
