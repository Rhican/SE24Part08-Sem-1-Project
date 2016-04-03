/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.AddCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.ModifyCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

/**
 *
 * @author SE24PT8
 */
public class ProductPanel extends BaseModulePanel implements INotificable {
	
	private static final long serialVersionUID = 1L;
	private JScrollPane prodcutPane;
	private JTable productTable;
	private Store manager = Store.getInstance();
	private Object[][] products;
	private DefaultTableModel dataModel;

	private final static String[] columnNames = { "ProdcutName", "Product Desc" ,"Category Name" };
	
	
	
	public ProductPanel() {
		super("Product");
		SubjectManager.getInstance().addNotification("ProductPanel", "Product", this);
	}

	public void refersh() {
		products = manager.getMgrProduct().prepareProductTableModel();
		productTable.setVisible(false);
		dataModel.setDataVector(products, columnNames);
		productTable.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager = Store.getInstance();
		products = manager.getMgrProduct().prepareProductTableModel();
		prodcutPane = new JScrollPane();
		{	
			dataModel = new DefaultTableModel(products, columnNames);
			dataModel.setDataVector(products, columnNames);
			productTable = new BaseTable(dataModel);
			prodcutPane.setViewportView(productTable);
		}
		return prodcutPane;
	}
	
	protected void performAddAction (){
		ProdcutEntryDialog d = new ProdcutEntryDialog();
		d.setEntryFlag(Constants.PRODCUT_ENTRYFLAG_NEW);
		d.pack();
		d.setVisible(true);
		refersh();
	}
	
	protected void performModifyAction () {
		if (productTable.getSelectedRow() != -1) {
            int selectedRow = productTable.getSelectedRow();
            Product product = manager.getMgrProduct().getProductList().get(selectedRow);
            	 
            if( product == null){
            	try {
					throw new Exception("Error in loading prodcut information");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
           
            ProdcutEditDialog editProdcutDialog = new ProdcutEditDialog(product);
            editProdcutDialog.pack();
           
            editProdcutDialog.setVisible(true);
        }
		refersh();
	}
	
	protected void performDeleteAction (){
		if (productTable.getSelectedRow() != -1) {
            String code = productTable.getValueAt(productTable.getSelectedRow(), 0).toString();
            ReturnObject  returnObject = manager.getMgrCategory().deleteCategory(code);
            if (returnObject.isSuccess()) {
            	JOptionPane.showMessageDialog(new JFrame(),
            			returnObject.getMessage(),
    					"Success", JOptionPane.INFORMATION_MESSAGE);
        	refersh();
            } else {
            	JOptionPane.showMessageDialog(new JFrame(),
            			returnObject.getMessage(),
    					"Error", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
	
	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("ProductPanel") && topic.equals("Product")) {
			if (data.equalsIgnoreCase("Add")) {
				refersh();
			}
		}

		System.out.println("Group: " + group + "Topic: " + topic + " \n data: " + data);
		refersh();
	}
	
	
	
}
	
	