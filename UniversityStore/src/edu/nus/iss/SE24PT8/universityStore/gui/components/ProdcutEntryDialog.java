package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.ComboItem;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class ProdcutEntryDialog extends BaseDialogBox{

private static final long serialVersionUID = 1L;
	
//0-Product ID
//  1-Product Name
///  2-Product Description
//  3-QuantityAvailable
//  4-Price
//  5-Barcode Number
// 6-Reorder Quantity
//  7-Order Quantity
	

    private JTextField txtName;
    private JTextField txtBarCode;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JTextField txtReorderQty;
    private JTextField txtOrderQty;
    private JComboBox<ComboItem> comboCategory;
    
     
    private ProductManager prodMgr =  Store.getInstance().getMgrProduct();
    private ArrayList<Category> categories ;

    public ProdcutEntryDialog () {
        super (MainWindow.getInstance(), "Add Product","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
        
        
    }

    protected JPanel createFormPanel  ()  {
    	JPanel p = new JPanel ();
    	
    	NumberFormat intFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(intFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        
     /*   NumberFormat doubleformat = NumberFormat.getInstance();
        NumberFormatter doubleFormatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);*/
    	
    	txtName  = new JTextField();
        txtBarCode  = new JTextField();
    	txtDescription  = new JTextField();
    	txtPrice = new JTextField();
    	txtQty = new JFormattedTextField(formatter);
    	txtReorderQty = new JFormattedTextField(formatter);
    	txtOrderQty = new JFormattedTextField();
    	
    	comboCategory = new JComboBox(getComboCatData().toArray());
    	for (ComboItem item : getComboCatData()) {
			comboCategory.addItem(item);
		}
    	
        p.setLayout (new GridLayout (0, 2));
        
        p.add (new JLabel ("Prodcut Name"));
        p.add(txtName);
        p.add (new JLabel ("BarCode"));
        p.add(txtBarCode);
        p.add (new JLabel ("Description"));
        p.add(txtDescription);
        p.add (new JLabel ("Description"));
        p.add(comboCategory);
        p.add (new JLabel ("Price"));
        p.add(txtPrice);
        p.add (new JLabel ("Quantity"));
        p.add(txtQty);
        p.add (new JLabel ("Reorder Quantity"));
        p.add(txtReorderQty);
        p.add (new JLabel ("Order Quantity"));
        p.add(txtOrderQty);
        

        return p;
    }
    

    protected boolean performCreateUpdateAction () {
       String productName = txtName.getText();
       String briefDesp  = txtDescription.getText();
       int qty =0;
      // if ( Integer.parseInt(txtQty.getText() ! ="") );
       double price  = Double.parseDouble(txtPrice.getText());
       String barCode = txtBarCode.getText();
       int reorderQty = Integer.parseInt(txtReorderQty.getText());
       int orderQty = Integer.parseInt(txtOrderQty.getText());
       
       if ( prodMgr.getProductByBarcode(barCode) !=null) {
    	   JOptionPane.showMessageDialog(rootPane,
					Constants.CONST_PRODUCT_ERR_BARCODEEXIST,
					"Error", JOptionPane.ERROR_MESSAGE);
    	   return false;
       }else {
    	   try {
			ReturnObject returnObj = prodMgr.addNewProduct(productName, briefDesp, qty, price, barCode, reorderQty, orderQty, "CLO");
			if ( returnObj.isSuccess() ){
				SubjectManager.getInstance().Update("ProductPanel", "Product", "Add");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       
        
    	return true;
    }
    
    public ArrayList<ComboItem> getComboCatData(){
    	ArrayList<Category> categories= Store.getInstance().getMgrCategory().getCategories();
    	ArrayList<ComboItem> comboCatData = new ArrayList<>();
    	for (Category category : categories) {
    		comboCatData.add(new ComboItem(category , category.getCategoryCode()));
		}
    	
    	return comboCatData;
    }
    
}
