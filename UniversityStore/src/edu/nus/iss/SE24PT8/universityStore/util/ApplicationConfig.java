/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author hendry
 */
public class ApplicationConfig {
    
    private Properties properties;
  
    //Configuration Keys
    public static final String KEY_MEMBER_FIRSTTIME_DISCOUNTCODE="MEMBERFIRSTDISCOUNTCODE";
    public static final String KEY_DATA_ROOT_FOLDER="DATAROOTFOLDER"; //
    public static final String KEY_DISCOUNT_CONVERSION_RATE="DISCOUNTCONVERSIONRATE";  //Number of points required to be convereted to 1 dollar
    
    
     
    
    
    private ApplicationConfig() {
        properties=new Properties();
        try{
            properties.load(new FileInputStream("resources//config.properties"));
        }catch(Exception e){
            
        }
        
    }
    
    public static ApplicationConfig getInstance() {
        return ApplicationConfigHolder.INSTANCE;
    }
    
    private static class ApplicationConfigHolder {

        private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    }
    
    
    public String getValue(String key){
        String val=null;
        val=properties.getProperty(key);
        return val;
    }
    
    public void setValue(String key, String val){
        properties.setProperty(key, val);
    }
    
    //For quick testing
    public void listKeyValues() {
        properties.list(System.out);
    
    }
    
    
    /*public static void main(String[] args) {
        ApplicationConfig conf=ApplicationConfig.getInstance();
        conf.listKeyValues();
    }*/
}
