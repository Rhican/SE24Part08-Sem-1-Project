/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Customer;
import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.domain.MemberDiscount;
import edu.nus.iss.SE24PT8.universityStore.domain.OtherDiscount;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMember;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.domain.StoreKeeper;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.manager.CategoryManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author hendry
 */
public class DataAdapter {

    private static final String ROOT_DATA_FOLDER = ApplicationConfig.getInstance().getValue(ApplicationConfig.KEY_DATA_ROOT_FOLDER);
    private static final String FILENAME_SUFFIX = ".dat";
    public static final String comma=",";

    private static final String FILENAME_CATEGORY = ROOT_DATA_FOLDER + "//Category.dat";
    private static final String FILENAME_DISCOUNT = ROOT_DATA_FOLDER + "//Discounts.dat";
    private static final String FILENAME_MEMBERS = ROOT_DATA_FOLDER + "//Members.dat";
    private static final String FILENAME_PRODUCT = ROOT_DATA_FOLDER + "//Products.dat";
    private static final String FILENAME_STOREKEEPER = ROOT_DATA_FOLDER + "//Storekeepers.dat";
    private static final String FILENAME_TRANSACTIONS = ROOT_DATA_FOLDER + "//Transactions.dat";
    private static final String FILENAME_VENDOR_PREFIX = ROOT_DATA_FOLDER + "//Vendors";

    // private static final DateFormat df=new SimpleDateFormat(DateTimeFormatter.ISO_LOCAL_DATE);
    public static final DateFormat DF = new SimpleDateFormat("yyyy-M-dd");

    // ==============Start of Category Tasks-=======================
    /* File Structure for Category
     0-Category ID
     1-Category Name;
    
     */
    private static Category convertTokensToCategory(ArrayList<String> tokens) {
        String token0_CategoryID = "";
        String token1_CategoryName = "";

        try {
            token0_CategoryID = tokens.get(0);
            token1_CategoryName = tokens.get(1);
            ArrayList<Vendor> vendors = loadVendors(token0_CategoryID);
            Category cat = new Category(token0_CategoryID, token1_CategoryName, vendors);
            return cat;
        } catch (Exception e) {
            return null;
        }

    }

    public static ArrayList<Category> loadCategories() {
        ArrayList<Category> objectList = new ArrayList<>();

        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_CATEGORY);
        for (ArrayList<String> tokens : dataLines) {
            Category object = convertTokensToCategory(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertCategoryToString(Category object) {
        return object.getCategoryCode() + "," + object.getCategoryName();
    }

    public static void writeCategories(ArrayList<Category> objects) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (Category object : objects) {
            dataLines.add(convertCategoryToString(object));
        }

        FileOperations.writeFile(dataLines, FILENAME_CATEGORY, false);

    }

    //============End of Category Tasks=================   
     // ==============Start of Vendor Tasks-=======================
    /* File Structure for Vendor
     0-Vendor Name
     1-Vendor Description;
     */
    private static Vendor convertTokenstoVendor(ArrayList<String> tokens) {
        String token0_VendorName = "";
        String token1_VendorDescription = "";
        try {
            token0_VendorName = tokens.get(0);
            token1_VendorDescription = tokens.get(1);
            Vendor vendor = new Vendor(token0_VendorName, token1_VendorDescription);
            return vendor;
        } catch (Exception e) {
            System.out.println("Error getting tokens for Vendor Object");
            return null;
        }
    }

    public static ArrayList<Vendor> loadVendors(String categoryID) {
        ArrayList<Vendor> objectList = new ArrayList<>();
        String filename = FILENAME_VENDOR_PREFIX + categoryID.toUpperCase().trim() + FILENAME_SUFFIX;
        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(filename);
        for (ArrayList<String> tokens : dataLines) {
            Vendor object = convertTokenstoVendor(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertVendorToString(Vendor object) {
        return object.getVendorName() + "," + object.getVendorDescription();
    }

    public static void writeVendors(ArrayList<Vendor> objects, String categoryID) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (Vendor object : objects) {
            dataLines.add(convertVendorToString(object));
        }
        String filename = FILENAME_VENDOR_PREFIX + categoryID.toUpperCase().trim() + FILENAME_SUFFIX;
        FileOperations.writeFile(dataLines, filename, false);

    }

 // ==============Start of Discount Tasks-=======================
    /* File Structure for Discount
     0-Discount Code
     1-Discount Description
     2-Start Date
     3-Period
     4-Percentage
     5-Applicable for 
        
     */
    private static Discount convertTokenstoDiscount(ArrayList<String> tokens) {

        String token0_discountCode = "";
        String token1_discountDescription = "";
        String token2_startDate = "";
        String token3_period = "";
        String token4_percentage = "";
        String token5_applicableFor = "";
        int percentage = 0;
        boolean isStartDateAlways = false;
        boolean isPeriodAlways = false;
        Date startDate = null;
        int period = 0;

        Discount discount = null;
        try {
            token0_discountCode = tokens.get(0);
            token1_discountDescription = tokens.get(1);
            token2_startDate = tokens.get(2);
            token3_period = tokens.get(3);
            token4_percentage = tokens.get(4);
            token5_applicableFor = tokens.get(5);

            percentage = new Integer(token4_percentage).intValue();

            //determine startdate
            if (token2_startDate.equalsIgnoreCase(Constants.CONST_ALWAYS)) {
                isStartDateAlways = true;
            } else {
                startDate = DF.parse(token2_startDate);
            }

            // determine period
            if (token3_period.equalsIgnoreCase(Constants.CONST_ALWAYS)) {
                isPeriodAlways = true;
            } else {
                period = new Integer(token3_period).intValue();
            }

            //Determine the Discount Type 
            if (token5_applicableFor.trim().equalsIgnoreCase(Constants.CONST_CUST_TYPE_MEMBER)) {
                discount = new MemberDiscount();
                discount.setApplicableFor(Constants.CONST_CUST_TYPE_MEMBER);
            } else {
                discount = new OtherDiscount();
                discount.setApplicableFor(Constants.CONST_CUST_TYPE_PUBLIC);
            }

            discount.setDiscountCode(token0_discountCode);
            discount.setDiscountDes(token1_discountDescription);
            discount.setDiscountPercent(percentage);
            discount.setDiscountStartDate(startDate);
            discount.setDiscountPeriod(period);
            discount.setIsStartDateAlways(isStartDateAlways);
            discount.setIsPeriodAlways(isPeriodAlways);
            
            return discount;

        } catch (Exception e) {
            return null;
        }

    }

    public static ArrayList<Discount> loadDiscounts() {
        ArrayList<Discount> objectList = new ArrayList<>();

        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_DISCOUNT);
        for (ArrayList<String> tokens : dataLines) {
            Discount object = convertTokenstoDiscount(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertDiscountToString(Discount object) {
        String token_StartDate="";
        String token_Period="";
        
        if(object.isIsStartDateAlways()){
            token_StartDate=Constants.CONST_ALWAYS;
        }else{
            token_StartDate=DF.format(object.getDiscountStartDate());
        }
        
        if(object.isIsPeriodAlways()){
            token_Period=Constants.CONST_ALWAYS;
        }else{
            token_Period=""+object.getDiscountPeriod();
        }
        
        return object.getDiscountCode()+comma+object.getDiscountDes()+comma+token_StartDate+comma+token_Period+comma+object.getDiscountPercent()+comma+
               object.getApplicableFor();
    }

    public static void writeDiscounts(ArrayList<Discount> objects) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (Discount object : objects) {
            dataLines.add(convertDiscountToString(object));
        }

        FileOperations.writeFile(dataLines, FILENAME_DISCOUNT, false);

    }
    // ==============Start of Member Tasks-=======================
    /* File Structure for Member
     0-Member ID
     1-Member Name
     2-Loyalty Point
     
     */

    private static Member convertTokenstoMember(ArrayList<String> tokens) {
        String token0_MemberName = "";
        String token1_MemberID = "";
        String token2_LoyaltyPoint = "";
        int loyaltyPoints = 0;
        try {
            token0_MemberName = tokens.get(0);
            token1_MemberID   = tokens.get(1);
            token2_LoyaltyPoint = tokens.get(2);
            loyaltyPoints = new Integer(token2_LoyaltyPoint).intValue();
            Member member = new Member(token0_MemberName, token1_MemberID, loyaltyPoints);
            return member;
        } catch (Exception e) {
            System.out.println("Error getting tokens for Vendor Object");
            return null;
        }

    }

    public static ArrayList<Member> loadMembers() {
        ArrayList<Member> objectList = new ArrayList<>();

        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_MEMBERS);
        for (ArrayList<String> tokens : dataLines) {
            Member object = convertTokenstoMember(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertMemberToString(Member object) {
       return  object.getCustomerName() + "," + object.getCustomerID() + "," + object.getLoyaltyPoints();
    }

    public static void writeMembers(ArrayList<Member> objects) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (Member object : objects) {
            dataLines.add(convertMemberToString(object));
        }

        FileOperations.writeFile(dataLines, FILENAME_MEMBERS, false);

    }
      // ==============Start of Product Tasks-=======================
    /* File Structure for Product
     0-Product ID
     1-Product Name
     2-Product Description
     3-QuantityAvailable
     4-Price
     5-Barcode Number
     6-Reorder Quantity
     7-Order Quantity
     
     */

    private static Product convertTokenstoProduct(ArrayList<String> tokens) {
        String token0_ProductID = "";
        String token1_ProductName = "";
        String token2_ProductDesctipion = "";
        String token3_QuantityAvail = "";
        String token4_Price = "";
        String token5_Barcode = "";
        String token6_Reorder = "";
        String token7_Order = "";

        Double price = 0.0;
        int quantityAvailable = 0;
        int quantityReoder = 0;
        int quantityOrder = 0;

        try {

            token0_ProductID = tokens.get(0);
            token1_ProductName = tokens.get(1);
            token2_ProductDesctipion = tokens.get(2);
            token3_QuantityAvail = tokens.get(3);
            token4_Price = tokens.get(4);
            token5_Barcode = tokens.get(5);
            token6_Reorder = tokens.get(6);
            token7_Order = tokens.get(7);

            quantityAvailable = new Integer(token3_QuantityAvail).intValue();
            quantityReoder = new Integer(token6_Reorder).intValue();
            quantityOrder = new Integer(token7_Order).intValue();
            price=Double.parseDouble(token4_Price );
            
            Product product = new Product(token0_ProductID, token1_ProductName, token2_ProductDesctipion, quantityAvailable, price, token5_Barcode, quantityReoder, quantityOrder);
            return product;

        } catch (Exception e) {
            System.out.println("Exception :"+e);
            System.out.println("Error getting tokens for Product Object");
            return null;
        }

    }

    public static ArrayList<Product> loadProducts() {
        ArrayList<Product> objectList = new ArrayList<>();

        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_PRODUCT);
        for (ArrayList<String> tokens : dataLines) {
            Product object = convertTokenstoProduct(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertProductToString(Product object) {
        return object.getProductId() + comma + object.getProductName() + comma+object.getBriefDesp()+comma + object.getQty() + comma + object.getPrice() + comma + object.getBarcode() + comma
                + object.getReorderQty() + comma + object.getOrderQty();
    }

    public static void writeProducts(ArrayList<Product> objects) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (Product object : objects) {
            dataLines.add(convertProductToString(object));
        }

        FileOperations.writeFile(dataLines, FILENAME_PRODUCT, false);

    }

        // ==============Start of StoreKeeper Tasks-=======================
    /* File Structure for StoreKeeper
     0-StoreKeeperID
     1-password
       
     
     */
    private static StoreKeeper convertTokenstoStoreKeeper(ArrayList<String> tokens) {
        String token0_StoreKeeperID = "";
        String token1_Password = "";

        try {
            token0_StoreKeeperID = tokens.get(0);
            token1_Password = tokens.get(1);

            StoreKeeper storekeeper = new StoreKeeper(token0_StoreKeeperID, token1_Password);
            return storekeeper;
        } catch (Exception e) {
            System.out.println("Error getting tokens for Vendor Object");
            return null;
        }

    }

    public static ArrayList<StoreKeeper> loadStoreKeepers() {
        ArrayList<StoreKeeper> objectList = new ArrayList<>();

        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_STOREKEEPER);
        for (ArrayList<String> tokens : dataLines) {
            StoreKeeper object = convertTokenstoStoreKeeper(tokens);
            if (object != null) {
                objectList.add(object);
            }
        }

        return objectList;
    }

    public static String convertStoreKeeperToString(StoreKeeper object) {
        return object.getstoreKeeperName() + "," + object.getPassword();
    }

    public static void writeStoreKeepers(ArrayList<StoreKeeper> objects) {
        ArrayList<String> dataLines = new ArrayList<>();
        for (StoreKeeper object : objects) {
            dataLines.add(convertStoreKeeperToString(object));
        }

        FileOperations.writeFile(dataLines, FILENAME_STOREKEEPER, false);

    }

        // ==============Start of Transaction Tasks-=======================
    /* File Structure for Transaction
     0-StoreKeeperID
     1-password
       
     
     */
    private static Transaction convertTokenstoTransaction(ArrayList<String> tokens) {
        String token0_TransactionID = "";
        String token1_ProductID = "";
        String token2_CustomerID = "";
        String token3_Quantity = "";
        String token4_Date = "";
        Date date = null;
        int quantity = 0;

        Customer customer = null;
        try {
            token0_TransactionID = tokens.get(0);
            token1_ProductID = tokens.get(1);
            token2_CustomerID = tokens.get(2);
            token3_Quantity = tokens.get(3);
            token4_Date = tokens.get(4);

            date = DF.parse(token4_Date);
            quantity = new Integer(token3_Quantity).intValue();

            //Construct Transaction Object 
            
            
             Transaction transaction=new Transaction();
             //transaction.setTransactionID(token0_TransactionID);
             transaction.setDate(date);
             transaction.setMember(token2_CustomerID);
        
             /*
             if(token2_CustomerID.equalsIgnoreCase(Constants.CONST_CUST_TYPE_PUBLIC)){
             customer=new NonMember(Constants.CONST_CUST_TYPE_PUBLIC,Constants.CONST_CUST_TYPE_PUBLIC);
             }else{
             customer=new Member(token2_CustomerID,token2_CustomerID,0);
             }
             
             //transaction.setCustomer(customer);
            */
             
             SaleItem sale=new SaleItem(token1_ProductID,quantity);
            
             Product product=new Product();
             product.setProductId(token1_ProductID);
             sale.setProductID(token1_ProductID);
         
             transaction.insertSaleItemRecord(sale);
             transaction.close();
             return transaction;
         
            
        } catch (Exception e) {
            System.out.println("Error getting tokens for Transaction Object");
            return null;
        }

    }

     
     public static ArrayList<Transaction> loadTransactions(){
        ArrayList<Transaction> objectList=new ArrayList<>();
        HashMap<Long,Transaction> transactions=new HashMap<>();
        Transaction existingTransaction;
        ArrayList<ArrayList<String>> dataLines = FileOperations.readFileTokenized(FILENAME_TRANSACTIONS);
        for(ArrayList<String> tokens:dataLines){
            Transaction transaction=convertTokenstoTransaction(tokens);
            //check if transaction is already in the list
            existingTransaction=null;
            existingTransaction=transactions.get(transaction.getId());

                if(existingTransaction==null){
                    transactions.put(transaction.getId(), transaction);
                }else{
                    existingTransaction.insertSaleItemRecord(transaction.getSaleItems().get(0));
                }//end of else
        }//end of For loop


        objectList.addAll(transactions.values());
        return objectList;
     }
    
    
    
     
    public static ArrayList<String> convertTransactionToString(Transaction transaction) {
         ArrayList<String> lines=new ArrayList<String>();
         StringBuffer sb;
         
         for(SaleItem item:transaction.getSaleItems()){
             sb=new StringBuffer();
             //1: Transaction id
            sb.append(transaction.getId());
            sb.append(comma);
            //2: Product ID
            sb.append(item.getProduct().getProductId());
            sb.append(comma);
            //3 :Member
            sb.append(transaction.getMember().getCustomerID());
            sb.append(comma);
            //4: quantity
            sb.append(item.getSaleQuantity());
            sb.append(comma);
            //5: date:
            sb.append(DF.format(transaction.getDate()));

          lines.add(sb.toString());
       }
         
         return lines;
    }

    public static void writeTransactions(Transaction trans) {
        ArrayList<String> dataLines = new ArrayList<>();
        dataLines=convertTransactionToString(trans);
        FileOperations.writeFile(dataLines, FILENAME_TRANSACTIONS, true);

    }

    public static void removeVedorFile(String code) {
        String filename = FILENAME_VENDOR_PREFIX + code.toUpperCase().trim() + FILENAME_SUFFIX;
        FileOperations.deleteFile(filename);
    }
    
    public static void testRetrieve(){
        ArrayList<Product> products=DataAdapter.loadProducts();
        for(Product object:products){
            System.out.println(convertProductToString(object));
        }
        
        ArrayList<Category> categories=DataAdapter.loadCategories();
        for(Category object:categories){
            System.out.println(convertCategoryToString(object));
            ArrayList<Vendor> vendors=DataAdapter.loadVendors(object.getCategoryCode());
            for(Vendor vendor:vendors){
                System.out.println(convertVendorToString(vendor));
            }
            
        }
        
        ArrayList<Member> members=DataAdapter.loadMembers();
        for(Member object:members){
            System.out.println(convertMemberToString(object));
        }
        
        ArrayList<StoreKeeper> storekeepers=DataAdapter.loadStoreKeepers();
        for(StoreKeeper object:storekeepers){
            System.out.println(convertStoreKeeperToString(object));
        }
        
        ArrayList<Discount> discounts=DataAdapter.loadDiscounts();
        for(Discount discount:discounts){
            System.out.println(convertDiscountToString(discount));
        }
        
        ArrayList<Transaction> transactions=DataAdapter.loadTransactions();
        for(Transaction trans:transactions){
            
            System.out.println("----------------------------");
            System.out.println("Transaction"+trans.getId()+comma+trans.getMember().getCustomerID()+comma+DF.format(trans.getDate()));
            System.out.println("   sale items:");
            for(SaleItem sale:trans.getSaleItems()){
                System.out.println("        "+sale.getProductID()+comma+sale.getSaleQuantity());
            }
        }
        
        
        
        
        
    }
    
    
    public static void main(String[] args) {
        
        DataAdapter.testRetrieve();
        Category cat=CategoryManager.getInstance().getCategory("CLO");
        for(Vendor ven:cat.getVendorList()){
            System.out.println("Vendors for cat CLO"+ven);
        }
        
        
        ArrayList<Vendor> notIn=CategoryManager.getInstance().getVendorsNotInCategory(cat);
        System.out.println("Vendors not in CLO");
        for(Vendor ven:notIn){
            System.out.println("Vendor"+ven);
        }
        
        
    }

}//end of class
