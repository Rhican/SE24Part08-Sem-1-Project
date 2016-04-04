package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.ComboItem;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class DiscountEditDialog extends BaseDialogBox{
	private JTextField txtName;
    private JTextField txtBarCode;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JTextField txtReorderQty;
    private JTextField txtOrderQty;
    private JComboBox<ComboItem> comboCategory;
    
    
    private Discount discount;
     
    private DiscountManager prodMgr =  Store.getInstance().getMgrDiscount();
    private ArrayList<Discount> discounts ;

    public DiscountEditDialog (Discount discount) {
    	
        super (MainWindow.getInstance(), "Modify Product","modify");
        super.setModalityType(Dialog.ModalityType.MODELESS);
      //  this.prodcut = prodcut;
        add ("Center" , createFormPanel());
        
    }

    protected JPanel createFormPanel  ()  {
    	JPanel p = new JPanel ();
    /*	
    	NumberFormat intFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(intFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        
        NumberFormat doubleformat = NumberFormat.getInstance();
        NumberFormatter doubleFormatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
    	
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
    	
    	int selectedComboIndex  = 1;
    	
    	for ( int i  = 1; i < comboCategory.getItemCount();i++){
    		if( comboCategory.getItemAt(i).getLabel() == prodcut.getCategory().getCategoryCode()){
				selectedComboIndex = i;
			}
    	}
    	
    	
        p.setLayout (new GridLayout (0, 2));
        
        p.add (new JLabel ("Prodcut Name"));
        p.add(txtName);
        txtName.setText(prodcut.getProductName());
        p.add (new JLabel ("BarCode"));
        p.add(txtBarCode);
        txtBarCode.setText(prodcut.getBarcode());
        txtBarCode.setEditable(false);
        p.add (new JLabel ("Description"));
        p.add(txtDescription);
        txtDescription.setText(prodcut.getBriefDesp());
        p.add (new JLabel ("Category"));
        p.add(comboCategory);
        comboCategory.setSelectedItem(comboCategory.getItemAt(selectedComboIndex));
        p.add (new JLabel ("Price"));
        p.add(txtPrice);
        txtPrice.setText(String.valueOf(prodcut.getPrice()));
        p.add (new JLabel ("Quantity"));
        p.add(txtQty);
        txtQty.setText(String.valueOf(prodcut.getQty()));
        p.add (new JLabel ("Reorder Quantity"));
        p.add(txtReorderQty);
        txtReorderQty.setText(String.valueOf(prodcut.getReorderQty()));
        p.add (new JLabel ("Order Quantity"));
        p.add(txtOrderQty);
        txtOrderQty.setText(String.valueOf(prodcut.getOrderQty()));*/
        

        return p;
    }
    

	protected boolean performCreateUpdateAction() {
		String briefDesp = txtDescription.getText();
		int qty = 0;
		double price = Double.parseDouble(txtPrice.getText());
		String barCode = txtBarCode.getText();
		int reorderQty = Integer.parseInt(txtReorderQty.getText());
		int orderQty = Integer.parseInt(txtOrderQty.getText());
		
		ComboItem selectedCombo = (ComboItem) comboCategory.getSelectedItem();
		String categoryCode = selectedCombo.getLabel();
		
			ReturnObject returnObj =null;
			try {
			//	returnObj = prodMgr.editProduct(barCode, briefDesp, qty, price, reorderQty, orderQty,categoryCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (returnObj != null && returnObj.isSuccess()) {
				SubjectManager.getInstance().Update("ProductPanel", "Product", "Add");
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
