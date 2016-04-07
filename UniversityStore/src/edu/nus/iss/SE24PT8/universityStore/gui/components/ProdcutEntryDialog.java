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
import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;
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
    	txtOrderQty = new JFormattedTextField(intFormatter);
    	
    	//comboCategory = new JComboBox(getComboCatData().toArray());
    	comboCategory = new JComboBox<ComboItem>();
    	for (ComboItem item : getComboCatData()) {
			comboCategory.addItem(item);
		}
    	
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
		String barCode = txtBarCode.getText();
		int qty = 0;
		double price = 0.0;
		int reorderQty = 0;
		int orderQty = 0;
		try {
			qty = Integer.parseInt(txtQty.getText());

		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(rootPane, "Please Enter correct Quantity!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			
			price = Double.parseDouble(txtPrice.getText());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(rootPane, "Please enter correct price!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {

			reorderQty = Integer.parseInt(txtReorderQty.getText());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(rootPane, "Please enter correct Reorder Quantity!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			
			orderQty = Integer.parseInt(txtOrderQty.getText());
		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(rootPane, "Please enter orrect Order Quantity!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		ComboItem comboItem = (ComboItem) comboCategory.getSelectedItem();
		Category category = (Category) comboItem.getValue();

		if (prodMgr.getProductByBarcode(barCode) == null) {

		}
		try {
			
			prodMgr.addNewProduct(productName, briefDesp, qty, price, barCode, reorderQty, orderQty,
					category.getCategoryCode());
		} catch (BadProductException e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		SubjectManager.getInstance().Update("ProductPanel", "Product", "Add");
		return true;
	}
    
	public ArrayList<ComboItem> getComboCatData() {
		ArrayList<Category> categories = Store.getInstance().getMgrCategory().getCategories();
		ArrayList<ComboItem> comboCatData = new ArrayList<>();
		for (Category category : categories) {
			comboCatData.add(new ComboItem(category, category.getCategoryCode()));
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
