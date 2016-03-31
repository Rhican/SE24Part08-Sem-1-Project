/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
import java.util.ArrayList;

/**
 *
 * @author Mugunthan
 */
public class CategoryManager implements IManager{
    
    private static CategoryManager instance = null;
    private ArrayList<Category> categories = null;
    
    private static ProductManager productMgr = ProductManager.getInstance();
    
    public static CategoryManager getInstance(){
        if(instance == null){
            instance = new CategoryManager();
            
            // Testing by zehua
            SubjectManager.getInstance().addSubject("Category", "Category Add");
        }
        return instance;
    }
    
    private CategoryManager()
    {
        categories = DataAdapter.loadCategories();
    }
    
    public Category getCategory(String code) {
        Category returnCat = null;
        for (Category category : categories) {
            if (category.getCategoryCode().equals(code))
                returnCat = category;
        }
        
        return returnCat;
    }
    
    public ArrayList<Category> getCategories(){
        return categories;
    }
    
    public ReturnObject addCategory(String code, String name){
        if(code.trim().equals("") || name.trim().equals("") || code == null || name == null)
          return new ReturnObject(false, Constants.CONST_CAT_ERR_INVALID_DETAILS, null);
        //Changed by hendry. Getcategory should be null
        //if (getCategory(code) != null){
        if (getCategory(code) == null){  
            Category newCat = new Category (code, name);
            categories.add(newCat);
            
            //Testing by zehua
            SubjectManager.getInstance().Update("Category", "Category Add", code);
            
            //DataAdapter.writeCategories(categories);
            return new ReturnObject(true,Constants.CONST_CAT_MSG_CREATION_SUCUESS, newCat);
        } else {
            return new ReturnObject(false,Constants.CONST_CAT_ERR_CATCODEEXIST, null);
        }            
        
    }
    
    //Assumbtion - category code connot be changed
    public ReturnObject updateCategory(Category cat) {
        Category oldCategory = getCategory(cat.getCategoryCode());
        if (oldCategory != null ) {
            oldCategory.setCategoryName(cat.getCategoryName());
            DataAdapter.writeCategories(categories);
            return new ReturnObject(true, Constants.CONST_CAT_MSG_UPDATE_SUCUESS, oldCategory);
        } else {
            return new ReturnObject(false, Constants.CONST_CAT_ERR_NOTEXIST, oldCategory);
        }
    }
    
    public ReturnObject deleteCategory(String code){
        if(code.trim().equals("") || code == null)
            return new ReturnObject(false,Constants.CONST_CAT_ERR_NOTEXIST, null);
        
        Category cat = getCategory(code);
        if (cat != null) {
            if (productMgr.getProductCountInCategory(cat) > 0) {
                return new ReturnObject(false,Constants.CONST_CAT_ERR_DELETE, null);
            } else {
                categories.remove(cat);
                DataAdapter.writeCategories(categories);
                //ToDO Remove vendor files
                DataAdapter.removeVedorFile(code);
                return new ReturnObject(true, Constants.CONST_CAT_MSG_DELETE_SUCUESS, cat);
            }                
        } else {
            return new ReturnObject(false,Constants.CONST_CAT_ERR_NOTEXIST, null);
        }        
        
    }

    @Override
    public void getRelatedObjects() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveData() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Added by Hendry: GEt the list of Vendors that the category is not currently assigned
    public ArrayList<Vendor> getVendorsNotInCategory(Category category){
        Category checkCategory;
        if(category==null){
            checkCategory=new Category("DUMMYXXX","DUMMYXXX");
        }else{
            checkCategory=category;
        }
        
        ArrayList<Vendor> vendorsNotAssigned=new ArrayList<Vendor>();
        ArrayList<Vendor> vendorsAssigned=checkCategory.getVendorList();
        
        
        for(Category cat:categories){
            //Skip same category
            if(cat.getCategoryCode().equalsIgnoreCase(checkCategory.getCategoryCode())){
                continue;//skip same category
            }
            
            //check vendors
            for(Vendor ven:cat.getVendorList()){
                if(!vendorsAssigned.contains(ven)&& !vendorsNotAssigned.contains(ven)){
                    vendorsNotAssigned.add(ven);
                }
            }//end of vendor check
        }// end of categories loop
        return vendorsNotAssigned;
    }
    
    
}
