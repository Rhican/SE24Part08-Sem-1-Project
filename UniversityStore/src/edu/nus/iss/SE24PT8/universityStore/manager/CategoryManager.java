
package edu.nus.iss.SE24PT8.universityStore.manager;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
import java.util.ArrayList;

/**
 *
 * @author Mugunthan
 */
public class CategoryManager {
    
    private static CategoryManager instance = null;
    private ArrayList<Category> categories = null;
    
    private static ProductManager productMgr = ProductManager.getInstance();
    private static VendorManager vendorMgr = VendorManager.getInstance();
    
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
    
    public Category addCategory(String code, String name) throws BadCategoryException{
        if(code.trim().equals("") || name.trim().equals("") || code == null || name == null|| code.length() > 3)
          throw new BadCategoryException(Constants.CONST_CAT_ERR_INVALID_DETAILS);
        if (getCategory(code.toUpperCase()) == null){  
            Category newCat = new Category (code.toUpperCase(), name);
            categories.add(newCat);
            vendorMgr.getVendors().put(code.toUpperCase(), new ArrayList<Vendor>());
            
            DataAdapter.writeCategories(categories);
            DataAdapter.createVedorFile(code.toUpperCase());
            return newCat;
        } else {
        	throw new BadCategoryException(Constants.CONST_CAT_ERR_CATCODEEXIST);
        }            
        
    }
    
    //Assumption - category code canno't be changed
    public Category updateCategory(Category cat) throws BadCategoryException {
        Category oldCategory = getCategory(cat.getCategoryCode());
        if (oldCategory != null ) {
            oldCategory.setCategoryName(cat.getCategoryName());
            DataAdapter.writeCategories(categories);
            DataAdapter.writeVendors(cat.getVendorList(), cat.getCategoryCode());
            return oldCategory;
        } else {
            throw new BadCategoryException(Constants.CONST_CAT_ERR_NOTEXIST);
        }
    }
    
    public boolean deleteCategory(String code) throws BadCategoryException {
        if(code.trim().equals("") || code == null)
        	throw new BadCategoryException(Constants.CONST_CAT_ERR_NOTEXIST);
        
        Category cat = getCategory(code);
        if (cat != null) {
            if (productMgr.getProductCountInCategory(cat) > 0) {
            	throw new BadCategoryException(Constants.CONST_CAT_ERR_DELETE);
            } else {
                categories.remove(cat);
                DataAdapter.writeCategories(categories);
                DataAdapter.removeVedorFile(code);
                return true;//new ReturnObject(true, Constants.CONST_CAT_MSG_DELETE_SUCUESS, cat);
            }                
        } else {
        	throw new BadCategoryException(Constants.CONST_CAT_ERR_NOTEXIST);
        }        
        
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
