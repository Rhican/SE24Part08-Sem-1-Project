/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package edu.nus.iss.SE24PT8.UniversityStore.manager;
import edu.nus.iss.SE24PT8.UniversityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.UniversityStore.domain.Discount;
import edu.nus.iss.SE24PT8.UniversityStore.domain.MemberDiscount;
import edu.nus.iss.SE24PT8.UniversityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.UniversityStore.util.Constants;
import edu.nus.iss.SE24PT8.UniversityStore.util.DataAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author misitesawn
 */
public class DiscountManagerTest {
    DiscountManager mgr = DiscountManager.getInstance();
    ArrayList<Discount> discounts = mgr.getDiscountList();
    
    
    @Test
    public void testAddDiscount() throws BadDiscountException{
        //addNewiDscount(String discountCode, String description, int percentage, Date startDate, int period, boolean isStartDateAlways, boolean isPeriodAlways, String applicableFor)
        
        mgr.addNewiDscount("DIS_01", "Disoucnt item o1", 10 , new Date(), 0, true, true, Constants.CONST_DISCOUNT_ERR_DISCOUNTCODEEXIST);
        
    }
    
    @Test
    public void testGtMaxDiscount(){
        
       
        
      /* Arrays.so
                rt(discounts, Discount.DiscountComparator);*/
         
    }
}
