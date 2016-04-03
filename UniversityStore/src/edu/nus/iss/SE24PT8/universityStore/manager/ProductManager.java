/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.TableModel;

/**
 *
 * @author misitesawn
 */
public class ProductManager implements IManager{
    //test git

    private static ProductManager Instance = null;
    private ArrayList<Product> productList;

    private static CategoryManager categoryManager;
    
    private  static String[] inventoryCheckColumnNames = { "ProdcutName", "Description" ,"Category Name" ,"Price"  ,"Quantity" ,"Reorder Quantity", "Order Quantity" ,"Vendor" ,"Remark"};
    private  static String[] columnNames = { "Product Id", "ProdcutName", "BarCode " ,"Product Desc" ,"Category Name" ,"Price"  ,"Quantity" };

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public static ProductManager getInstance() {
        if (Instance == null) {
            Instance = new ProductManager();
        }
        return Instance;

    }

    /**
     * Read all product List from file Set each product Category
     *
     *
     */
    private ProductManager() {
        categoryManager = CategoryManager.getInstance();
        productList = new ArrayList<>();
        productList = DataAdapter.loadProducts();
        setProductCategory();

    }

    /**
     * Set Category of each Products
     *
     *
     */
    private void setProductCategory() {

        Category category;
        Iterator<Product> i = productList.iterator();
        while (i.hasNext()) {
            Product product = i.next();
            if (product.getCategory() == null) {
                category = categoryManager.getCategory(product.getCategoryCode());
                product.setCategory(category);
            }
        }
    }

    /**
     * Add new Product to ProductList
     *
     * @param product
     * @throws Exception 
     *
     */
    
    //0-Product ID
    //  1-Product Name
    ///  2-Product Description
    //  3-QuantityAvailable
    //  4-Price
    //  5-Barcode Number
    // 6-Reorder Quantity
    //  7-Order Quantity
    public ReturnObject addNewProduct(String productNmae, String briefDesp, int qty, double price, String barCode, int reorderQty, int orderQty, String categoryCode) throws Exception {
        // new Product(productId, productName, briefDesp, 0, 0, barcode, 0, 0);
        Product product;
        ReturnObject returnObj  = new ReturnObject(true, "ok", null);
		Category category;
		category = categoryManager.getCategory(categoryCode);

		if (category == null) {
			throw new Exception("Category Eror during operation!");
		}

		String productId = categoryCode + "/" + Integer.toString(getProductCountInCategory(category) + 1);

		product = new Product(productId, productNmae, briefDesp, qty, price, barCode, reorderQty, orderQty, category);
		productList.add(product);

		saveData();
		return returnObj;

    }
    
    
    /**
     * @param prodcutID
     * @param briefDescp
     * @param qty
     * @param price
     * @param barCode
     * @param reorderQty
     * @param orderQty
     * @param categoryCode
     * @return
     * 
     *Assumption: ProdcutName and BarCode cannot be modified
     * @throws Exception 
     */
    public ReturnObject editProduct(String barCode,String briefDescp, int qty, double price,int reorderQty, int orderQty, String categoryCode) throws Exception{
    	Product product = Store.getInstance().getMgrProduct().getProductByBarcode(barCode);
    	ReturnObject returnObj  = new ReturnObject(true, "ok", null);
    	if ( product == null){
    		throw new BadProductException("Can not retrive prodcut information.");
    	}
    	
        Category category;
		category = categoryManager.getCategory(categoryCode);

		if (category == null) {
			throw new Exception("Category Eror during operation!");
		}

		product.setQty(qty);
		product.setBriefDesp(briefDescp);
		product.setPrice(price);
		product.setReorderQty(reorderQty);
		product.setReorderQty(reorderQty);
		product.setOrderQty(orderQty);
		product.setCategory(category);
		

		saveData();
        return returnObj;
    }
    
	public ReturnObject orderProdcut(Product product, int orderQty) {
		ReturnObject returnObj = new ReturnObject(true, "ok", null);
		product.setQty(product.getQty() + orderQty);
		saveData();
		return returnObj;

	}
    
    /**
     * Get the number of products in a Category
     * @reuturn Product Count 
     * @param category
     */
    public int getProductCountInCategory(Category category){
        int count = 0;
        if ( getProductsByCategory(category).size() > 0) 
               count = getProductsByCategory(category).size();
        return count;
    }

    /**
     * Write Product To File
     * Must call after add, update, delete product item
     *
     */
    public void saveData() {
        DataAdapter.writeProducts(productList);
    }

    /**
     * Get all products in a Category
     *
     * @param category
     * @return Products
     */
    public ArrayList<Product> getProductsByCategory(Category category) {

        ArrayList<Product> products = new ArrayList<>();
        Iterator <Product> i = productList.iterator();
        while (i.hasNext()){
            Product product  = i.next();
            //need to test equals methods
            if (product.getCategory().equals(category)){
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Return boolean check if the bar code already exists
     *
     * @param barcode
     * @return boolean
     */
    public boolean isvalidBarCode(String barcode) {
        Iterator<Product> i = productList.iterator();
        while (i.hasNext()) {
            Product product = i.next();
            if (product.getBarcode().equalsIgnoreCase(barcode)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return boolean check if the bar code already exists
     *
     * @param barcode
     * @return boolean
     */
//    public Product getProduct(String barcode) {
//
//        Iterator<Product> i = this.productList.iterator();
//        while (i.hasNext()) {
//            Product product = i.next();
//            if (product.getBarcode().equalsIgnoreCase(barcode)) {
//                return product;
//            }
//        }
//
//        return null;
//    }
    
    
    public Product getProductByID(String producID){
        for(Product product:productList){
            if(product.getProductId().equalsIgnoreCase(producID)){
                return product;
            }
        }
        return null;
    }//end get PRoductby ID
    
    public Product getProductByBarcode(String barcode){
        for(Product product:productList){
            if(product.getBarcode().equalsIgnoreCase(barcode)){
                return product;
            }
        }
        return null;
    }
    

    /**
     * Return List of Product that have lower inventory quantity
     *
     * @return Product List
     */
    public ArrayList<Product> getLowerInventoryProducts() {
        ArrayList<Product> lowerInventoroyProducts = new ArrayList<>();
        Iterator<Product> i = this.productList.iterator();
        while (i.hasNext()) {
            Product product = i.next();
            if (product.getQty() <= product.getReorderQty()) {
                lowerInventoroyProducts.add(product);
            }
        }

        return lowerInventoroyProducts;
    }

    /**
     * Return List of Product that have lower inventory quantity
     * Call by Transaction during checkout
     * @param productList
     * @return Product List
     */
    public ArrayList<Product> getLowerInventoryProducts(ArrayList<Product> productList) {
        ArrayList<Product> lowerInventoroyProducts = new ArrayList<>();
        Iterator<Product> i = productList.iterator();
        while (i.hasNext()) {
            Product product = i.next();
            if (product.getQty() <= product.getReorderQty()) {
                lowerInventoroyProducts.add(product);
            }
        }

        return lowerInventoroyProducts;
    }

    
     @Override
    public void getRelatedObjects() {
       CategoryManager mgrCategory=CategoryManager.getInstance();
       
       for(Product product:productList){
          product.setCategory(mgrCategory.getCategory(product.getCategoryCode()));
       }
    }
     
     public String[] getProductTableHeader(){
    	 return columnNames;
     }
     
     
     
     //All Products
     public Object[][] prepareProductTableModel() {
 		ArrayList<Product> list = getProductList();
 		return prepareProductTableModel(list);

     }
     
     
     public Object[][] prepareProductTableModel(ArrayList<Product> prodcuts) {
  		Object[][] tableData = new Object[prodcuts.size()][3];
  		for (int i = 0; i < prodcuts.size(); i++) {
  			Product product = prodcuts.get(i);
  			Object[] rowData = new Object[6];
  			rowData[0] = product.getProductName();
  			rowData[1]  = product.getBarcode();
  			rowData[2] = product.getBriefDesp();
  			rowData[3] = product.getCategory().getCategoryName();
  			rowData[4] = product.getPrice();
  			rowData[5] = product.getQty();
  			tableData[i] = rowData;
  		}
  		return tableData;
  	}
     
     public Object[][] getLowInventoryProdcutTableModel() {
    	 ArrayList<Product> prodcuts = getLowerInventoryProducts();
   		Object[][] tableData = new Object[prodcuts.size()][3];
   		for (int i = 0; i < prodcuts.size(); i++) {
   			Product product = prodcuts.get(i);
   			Object[] rowData = new Object[9];
   			rowData[0] = product.getProductName();
   			rowData[1]  = product.getBriefDesp();
   			rowData[2] = product.getCategory().getCategoryName();
   			rowData[3] = product.getPrice();
   			rowData[4] = product.getQty();
   			rowData[5] = product.getReorderQty();
   			rowData[6] = product.getOrderQty();
   			if ( product.getCategory().getVendorList().size() >=1 ){
   				rowData[7] = product.getCategory().getVendorList().get(0);
   				rowData[8] = "";
   			}
   			else {
   				rowData[7] = "NA";
   				rowData[8] = "Add Vendor for Prodcut Category First";
   			}
   			
   			tableData[i] = rowData;
   		}
   		return tableData;
   	}
     

	public String[] getInventoryCheckTableHeader() {
		return inventoryCheckColumnNames;
	}

	public ReturnObject orderAllLowInventoryProdcut() {
		ReturnObject returnObj  = new ReturnObject(true, "ok", null);
		if(  getLowerInventoryProducts().size() >=1) {
			for (Product product : getLowerInventoryProducts()) {
				product.setQty(product.getOrderQty() + product.getOrderQty());
			}
		}
			
		return returnObj;
	}
}
