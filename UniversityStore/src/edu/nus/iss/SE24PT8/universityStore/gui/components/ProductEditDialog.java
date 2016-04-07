package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
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
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.ComboItem;

public class ProductEditDialog extends BaseDialogBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
    private JTextField txtBarCode;
    private JTextField txtDescription;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JTextField txtReorderQty;
    private JTextField txtOrderQty;
    private JComboBox<ComboItem> comboCategory;
    
    
	private Product prodcut;

	private ProductManager prodMgr = Store.getInstance().getMgrProduct();

	public ProductEditDialog(Product prodcut) {

		super(MainWindow.getInstance(), "Modify Product", "add");
		super.setModalityType(Dialog.ModalityType.MODELESS);
		this.prodcut = prodcut;
		add("Center", createFormPanel());
		UpdatePanel();

	}

	protected void UpdatePanel() {
		int selectedComboIndex = 1;
		ComboItem comboItm = null;
		for (int i = 1; i < comboCategory.getItemCount(); i++) {
			comboItm = comboCategory.getItemAt(i);
			if (comboItm != null && comboItm.getLabel().equalsIgnoreCase(prodcut.getCategory().getCategoryCode())) {
				selectedComboIndex = i;
			}
		}
		txtName.setText(prodcut.getProductName());
		txtName.setEditable(false);
		txtBarCode.setText(prodcut.getBarcode());
		txtBarCode.setEditable(false);
		txtDescription.setText(prodcut.getBriefDesp());
		comboCategory.setSelectedItem(comboCategory.getItemAt(selectedComboIndex));
		comboCategory.setEditable(false);
		txtPrice.setText(String.valueOf(prodcut.getPrice()));
		txtQty.setText(String.valueOf(prodcut.getQty()));
		txtReorderQty.setText(String.valueOf(prodcut.getReorderQty()));
		txtOrderQty.setText(String.valueOf(prodcut.getOrderQty()));
	}

	protected JPanel createFormPanel() {
		JPanel p = new JPanel();

		NumberFormat numberFormat = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(numberFormat);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);

		NumberFormatter doubleFormatter = new NumberFormatter(numberFormat);
		formatter.setValueClass(Double.class);
		formatter.setMinimum(0.0);

		txtName = new JTextField(20);
		txtBarCode = new JTextField(20);
		txtDescription = new JTextField(20);
		txtPrice = new JFormattedTextField(doubleFormatter);
		txtQty = new JFormattedTextField(formatter);
		txtReorderQty = new JFormattedTextField(formatter);
		txtOrderQty = new JFormattedTextField(formatter);

		comboCategory = new JComboBox<ComboItem>();
		for (ComboItem item : getComboCatData()) {
			comboCategory.addItem(item);
		}

		p.setLayout(new GridLayout(0, 2));

		p.add(new JLabel("Product Name"));
		p.add(txtName);
		p.add(new JLabel("BarCode"));
		p.add(txtBarCode);
		txtBarCode.setEditable(false);
		p.add(new JLabel("Description"));
		p.add(txtDescription);
		p.add(new JLabel("Category"));
		p.add(comboCategory);
		p.add(new JLabel("Price"));
		p.add(txtPrice);
		p.add(new JLabel("Quantity"));
		p.add(txtQty);
		p.add(new JLabel("Reorder Quantity"));
		p.add(txtReorderQty);
		p.add(new JLabel("Order Quantity"));
		p.add(txtOrderQty);

		return p;
	}
    

	protected boolean performCreateUpdateAction() {
		
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

		
		ComboItem selectedCombo = (ComboItem) comboCategory.getSelectedItem();
		String categoryCode = selectedCombo.getLabel();
		
		try {
			prodMgr.editProduct(barCode, briefDesp, qty, price, reorderQty, orderQty, categoryCode);
			
		} catch (BadProductException e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(rootPane, "Error in Update Prodcut!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;

		}

		SubjectManager.getInstance().Update("ProductPanel", "Product", "Modify");
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
