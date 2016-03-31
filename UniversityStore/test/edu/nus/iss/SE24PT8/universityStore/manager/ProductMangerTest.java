/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;
 
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
    

/**
 *
 * @author misitesawn
 */
public class ProductMangerTest {
    private ProductManager productMgr;
    private CategoryManager categoryMgr;
    ArrayList<Product> products;
    @Before
    public void setUp() {
        productMgr = ProductManager.getInstance();
        categoryMgr = CategoryManager.getInstance();
        products = productMgr.getProductList();
        products.removeAll(products);

        productMgr.saveData();

    }
    
    @Test
    public void addProdcutTest(){
        
        Category cat1  = categoryMgr.getCategory("CLO");
        productMgr.addNewProduct("Air Tight Coffee", "Coffe Bup", 14, 24.90, "11994456", 20, 200, cat1);
      
        Category cat2  = categoryMgr.getCategory("CLO");
        productMgr.addNewProduct("Coffee Bean Mug", "Coffe Bean Special Mug", 100, 100, "11992235", 30, 100, cat2);
       
        
        Product prod1  = new Product();
        Product prod2  = new Product();
       
        prod1.setProductId("CLO/1");
        prod1.setProductName("Air Tight Coffee");
        prod1.setBriefDesp("Coffe Bup");
        prod1.setPrice(24.90);
        prod1.setBarcode("11994456");
        prod1.setReorderQty(20);
        prod1.setOrderQty(200);
        prod1.setCategory(cat1);
        prod1.setQty(14);
        
        prod2.setProductId("CLO/2");
        prod2.setProductName("Coffee Bean Mug");
        prod2.setQty(100);
        prod2.setBriefDesp("Coffe Bean Special Mug");
        prod2.setPrice(100);
        prod2.setBarcode("11992235");
        prod2.setReorderQty(30);
        prod2.setOrderQty(100);
        prod2.setCategory(cat2);
        
        assertEquals(products.get(0)  , prod1);
        
        
        assertEquals(products.get(1)  , prod2);
    }
    
   
    
    @Test 
    public void getLoserInventoryProdcutsTest(){
        
    }
    
    
    
    
}
