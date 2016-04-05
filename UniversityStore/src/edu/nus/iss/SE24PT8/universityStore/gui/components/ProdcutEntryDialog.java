package edu.nus.iss.SE24PT8.universityStore.gui.components;


import java.awt.Dialog;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	

    private JTextField txtName;
    private JTextField txtBarCode;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JTextField txtReorderQty;
    private JTextField txtOrderQty;
    private JComboBox<ComboItem> comboCategory;
    
    private String entryFlag;
     
    private ProductManager prodMgr =  Store.getInstance().getMgrProduct();
    public ProdcutEntryDialog () {
    	
        super (MainWindow.getInstance(), "New Product","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
        
    }
    
    public void setEntryFlag(String flag){
    	this.entryFlag = flag;
    }

    protected JPanel createFormPanel  ()  {
    	JPanel p = new JPanel ();
    	
    	NumberFormat intFormat = NumberFormat.getInstance();
        NumberFormatter intFormatter = new NumberFormatter(intFormat);
        intFormatter.setValueClass(Integer.class);
        intFormatter.setMinimum(0);
        
        NumberFormat doubleformat = NumberFormat.getInstance();
        NumberFormatter doubleFormatter = new NumberFormatter(doubleformat);
        intFormatter.setValueClass(Double.class);
        intFormatter.setMinimum(0.0);
    	
    	txtName  = new JTextField(20);
        txtBarCode  = new JTextField(20);
    	txtDescription  = new JTextField(20);
    	txtPrice = new JFormattedTextField(doubleFormatter);
    	txtQty = new JFormattedTextField(intFormatter);
    	txtReorderQty = new JFormattedTextField(intFormatter);
    	txtOrderQty = new JFormattedTextField();
    	
    	comboCategory = new JComboBox(getComboCatData().toArray());
    	//for (ComboItem item : getComboCatData()) {
		//	comboCategory.addItem(item);
		//}
    	
        p.setLayout (new GridLayout (0, 2));
        p.add (new JLabel ("Product Name"));
        p.add(txtName);
        p.add (new JLabel ("BarCode"));
        p.add(txtBarCode);
        p.add (new JLabel ("Description"));
        p.add(txtDescription);
        p.add (new JLabel ("Category"));
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
    

	protected boolean performCreateUpdateAction() {
		String productName = txtName.getText();
		String briefDesp = txtDescription.getText();
		int qty = Integer.parseInt(txtQty.getText());
		double price = Double.parseDouble(txtPrice.getText());
		String barCode = txtBarCode.getText();
		int reorderQty = Integer.parseInt(txtReorderQty.getText());
		int orderQty = Integer.parseInt(txtOrderQty.getText());
		
		ComboItem comboItem  =  (ComboItem) comboCategory.getSelectedItem();
		Category category = (Category) comboItem.getValue();

		if (entryFlag == Constants.DISCOUNT_ENTRYFLAG_NEW && prodMgr.getProductByBarcode(barCode) != null) {
			JOptionPane.showMessageDialog(rootPane, Constants.CONST_PRODUCT_ERR_BARCODEEXIST, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				ReturnObject returnObj = null;
				if (entryFlag == Constants.DISCOUNT_ENTRYFLAG_EDIT) {
					returnObj = prodMgr.editProduct(barCode,briefDesp,qty,price,reorderQty,orderQty, "CLO");
				} else {
					returnObj = prodMgr.addNewProduct(productName, briefDesp, qty, price, barCode, reorderQty, orderQty,
							category.getCategoryCode());
				}

				if (returnObj != null && returnObj.isSuccess()) {
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
    
    public JTextField getProdcutNameTextField(){
    	return txtName;
    }
    public JTextField getBarCodeTextField(){
    	return txtBarCode;
    }
    public JTextField getDescriptionTextField(){
    	return txtDescription;
    }
    public JComboBox<ComboItem> getComboCategory(){
    	return comboCategory;
    }
    public JTextField getQtyTextField(){
    	return txtQty;
    }
    public JTextField getReorderTextField(){
    	return txtReorderQty;
    }
    public JTextField getOrderTextField(){
    	return txtOrderQty;
    }
    
}
