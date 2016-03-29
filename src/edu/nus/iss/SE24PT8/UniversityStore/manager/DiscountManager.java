package edu.nus.iss.SE24PT8.UniversityStore.manager;


import com.sun.javafx.geom.CubicApproximator;
import edu.nus.iss.SE24PT8.UniversityStore.domain.Customer;
import edu.nus.iss.SE24PT8.UniversityStore.domain.Discount;
import edu.nus.iss.SE24PT8.UniversityStore.domain.MemberDiscount;
import edu.nus.iss.SE24PT8.UniversityStore.domain.NonMember;
import edu.nus.iss.SE24PT8.UniversityStore.domain.Member;
import edu.nus.iss.SE24PT8.UniversityStore.domain.OtherDiscount;
import edu.nus.iss.SE24PT8.UniversityStore.domain.Product;

import edu.nus.iss.SE24PT8.UniversityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.UniversityStore.util.ApplicationConfig;
import edu.nus.iss.SE24PT8.UniversityStore.util.Constants;
import edu.nus.iss.SE24PT8.UniversityStore.util.DataAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javafx.scene.chart.PieChart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author misitesawn
 */
public class DiscountManager implements IManager {

    private static DiscountManager Instance = null;
    private ArrayList<Discount> discountList;
    private MemberManager memberManager;

    public static DiscountManager getInstance() {
        if (Instance == null) {
            Instance = new DiscountManager();
        }
        return Instance;

    }

    public DiscountManager() {
        discountList = new ArrayList<>();
        memberManager = MemberManager.getInstance();
        discountList = DataAdapter.loadDiscounts();
    }

    public ArrayList<Discount> getDiscountList() {
        return discountList;
    }


    /**
     * to add new Member Discount check Discount Code is already exists or not
     * add new Member Discount for valid record
     *
     * @param memberDiscount
     */
   public void addNewiDscount(String discountCode, String description, int percentage, Date startDate, int period, boolean isStartDateAlways, boolean isPeriodAlways, String applicableFor) throws BadDiscountException {

        Discount discount;
        
        
        if (isPeriodAlways) {
            period = 0;
        }

        if (getDiscountByCode(discountCode) != null) {

           throw new BadDiscountException(Constants.CONST_DISCOUNT_ERR_DISCOUNTCODEEXIST);

        }

        if (applicableFor.trim().equalsIgnoreCase(Constants.CONST_CUST_TYPE_MEMBER)) {
            discount = new MemberDiscount();
            discount.setApplicableFor(Constants.CONST_CUST_TYPE_MEMBER);
        } else {
            discount = new OtherDiscount();
            discount.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
        }
        discount.setDiscountCode(discountCode);
        discount.setDiscountDes(description);
        discount.setDiscountPercent(percentage);
        discount.setDiscountStartDate(startDate);
        discount.setDiscountPeriod(period);
        discount.setIsStartDateAlways(isStartDateAlways);
        discount.setIsPeriodAlways(isPeriodAlways);
        
        
        discountList.add(discount);
        saveData();

    }

    
     @Override
    public void getRelatedObjects() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Added by hendry..get a particular discount by code regardless for member or not
    public Discount getDiscountByCode(String discountCode){
       for(Discount discount:discountList){
           if(discount.getDiscountCode().equalsIgnoreCase(discountCode)){
               return discount;
           }
       }
       return null;
    }
    
    //add By Hendry..Get Max Discount based on date and customer type
    public Discount getMaxDiscount(Date date, Customer customer){
        
        int maxAmount=0;
        Discount memFirstDiscount=null;
        Discount maxDiscount=null;
        
        
        if(customer==null){
            return maxDiscount;
        }
        
        // Customer is not member, so just check discounts applicable for all
        if(!(customer instanceof Member)){
            for(Discount dis:discountList){
                //check whether it's applicable for all
                if(!(dis.getApplicableFor().equalsIgnoreCase(Constants.CONST_CUST_TYPE_PUBLIC))){
                    continue; //continue to next record
                }
                
                //check if it's valid for the date
                if(!dis.isDiscountValidForDate(date)){
                    continue; //continue to next record
                }
                
                //get discount amount and compare
                if(maxAmount<dis.getDiscountPercent()){
                    maxAmount=dis.getDiscountPercent();
                    maxDiscount=dis;
                }
            }
            
            return maxDiscount;
        }// end of customer not public
        
        
        //Now for Member

        String MemberFirstDiscountCode=ApplicationConfig.getInstance().getValue(ApplicationConfig.KEY_MEMBER_FIRSTTIME_DISCOUNTCODE);
        memFirstDiscount=getDiscountByCode(MemberFirstDiscountCode);
        
        for(Discount dis:discountList){
            //Skip First Member discount
            if(dis.getDiscountCode().equalsIgnoreCase(MemberFirstDiscountCode)){
                continue;
            }
            //Skip those public discounts
            if(dis.getApplicableFor().equalsIgnoreCase(Constants.CONST_CUST_TYPE_PUBLIC)){
                continue;
            }
            if(maxAmount<dis.getDiscountPercent()){
                maxAmount=dis.getDiscountPercent();
                maxDiscount=dis;
            }
        }
        
        //check if max discount is greated that first member. if yes, just take it, no need to check if member first time
        if(maxDiscount.getDiscountPercent()>memFirstDiscount.getDiscountPercent()){
           return maxDiscount;
        }
        
        Member member=(Member)customer;
        if(member.getLoyaltyPoints()<0){
            return memFirstDiscount;
        }else{
            return maxDiscount;
        }
        
    }
    
    
    @Override
    public void saveData() {
        DataAdapter.writeDiscounts(discountList);
       
    }

}
