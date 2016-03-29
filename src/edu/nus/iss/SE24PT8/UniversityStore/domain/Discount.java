/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.domain;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author misitesawn
 */
public  class  Discount  implements Comparable<Discount>{
    private String discountCode;
    private String discountDes;
    private Date discountStartDate;
    private int discountPeriod;
    private boolean isStartDateAlways;
    private boolean isPeriodAlways;
    
    
    //Added by Hendry
    private int discountPercent;
    
    
    //added by Hendry
    private  String applicableFor;
    
    
    public boolean isDiscountValidForDate(Date date){
        // Start date always and perido always. confirm ok
        if(isStartDateAlways && isPeriodAlways){
            return true;
        }
        
        //Compare Dates
        int dateComparison=discountStartDate.compareTo(date);
        
        // check if start date is future date. If future, then cannot!
        if(dateComparison>0){
            return false;
        }
        
        //So now date start date is valid..check period...
        if(isPeriodAlways){
            return true;
        }
        
        //Get the end date of the discount period.
        Calendar c=Calendar.getInstance();
        c.setTime(discountStartDate);
        c.add(Calendar.DATE, discountPeriod);
        dateComparison=c.getTime().compareTo(date);
        
        if(dateComparison>=0){
            return true;
        }
        //Finally return false;
        return false;
    }
    
    
  
    public String getApplicableFor() {
        return applicableFor;
    }
    
    public void setApplicableFor(String applicable){
        applicableFor=applicable;
    }
    

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isIsStartDateAlways() {
        return isStartDateAlways;
    }

    public void setIsStartDateAlways(boolean isStartDateAlways) {
        this.isStartDateAlways = isStartDateAlways;
    }

    public boolean isIsPeriodAlways() {
        return isPeriodAlways;
    }

    public void setIsPeriodAlways(boolean isPeriodAlways) {
        this.isPeriodAlways = isPeriodAlways;
    }
    
    
    
    public Discount() {
    }
    
    public Discount(String discountCode, String discountDes, Date discountStartDate, int discountPeriod,int dicountPercent, boolean isPeriodAlways,String applicableFor) {
        this.discountCode = discountCode;
        this.discountDes = discountDes;
        this.discountStartDate = discountStartDate;
        this.discountPeriod = discountPeriod;
        this.isPeriodAlways = isPeriodAlways;
        this.applicableFor = applicableFor;
    }


    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setDiscountDes(String discountDes) {
        this.discountDes = discountDes;
    }

    public void setDiscountStartDate(Date discountStartDate) {
        this.discountStartDate = discountStartDate;
    }

    public void setDiscountPeriod(int discountPeriod) {
        this.discountPeriod = discountPeriod;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getDiscountDes() {
        return discountDes;
    }

    public Date getDiscountStartDate() {
        return discountStartDate;
    }

    public int getDiscountPeriod() {
        return discountPeriod;
    }

    @Override
    public String toString() {
        return "Discount{" + "discountCode=" + discountCode +", Percent:"+discountPercent +", discountDes=" + discountDes + ", discountStartDate=" + discountStartDate + ", discountPeriod=" + discountPeriod + '}';
    }

    @Override
    public int compareTo(Discount discount) {
        return this.discountPercent - discount.getDiscountPercent();
        
    }
   
    public static Comparator<Discount> DiscountComparator
            = new Comparator<Discount>() {

        @Override
        public int compare(Discount discount1, Discount discount2) {

            //ascending order
            return discount1.compareTo(discount2);

        }

    };
    
    
 
}
