
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
        if(code.trim().equals("") || name.trim().equals("") || code == null || name == null|| code.length() > 3)
          return new ReturnObject(false, Constants.CONST_CAT_ERR_INVALID_DETAILS, null);
        if (getCategory(code.toUpperCase()) == null){  
            Category newCat = new Category (code.toUpperCase(), name);
            categories.add(newCat);
            
            DataAdapter.writeCategories(categories);
            DataAdapter.createVedorFile(code.toUpperCase());
            return new ReturnObject(true,Constants.CONST_CAT_MSG_CREATION_SUCUESS, newCat);
        } else {
            return new ReturnObject(false,Constants.CONST_CAT_ERR_CATCODEEXIST, null);
        }            
        
    }
    
    //Assumption - category code canno't be changed
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

	public void updateVendorList(ArrayList<Vendor> newVendorList, String catCode) {
		getCategory(catCode).setVendorList(newVendorList);
		
	}
    
    public Object[][] prepareCategoryTableModel() {
		ArrayList<Category> list = getCategories();
		Object[][] tableData = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			Category category = list.get(i);
			Object[] rowData = new Object[2];
			rowData[0] = category.getCategoryCode();
			rowData[1] = category.getCategoryName();
			tableData[i] = rowData;
		}
		return tableData;
	}
    
    
}
