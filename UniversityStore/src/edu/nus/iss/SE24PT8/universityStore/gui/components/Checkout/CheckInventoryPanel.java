package edu.nus.iss.SE24PT8.universityStore.gui.components.Checkout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Group;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.ProdcutEntryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class CheckInventoryPanel  extends JPanel implements INotificable{

	/**
	 * Create the panel.
	 */
	
	
	private static final long serialVersionUID = 1L;
	private JScrollPane dataPane;
	private JTable productTable;
	private Store manager = Store.getInstance();
	private Object[][] products;
	private DefaultTableModel dataModel;
	String[] columnNames = manager.getMgrProduct().getInventoryCheckTableHeader();
	private ProductManager prodMgr = manager.getMgrProduct();
	
	
	private JLabel titleLabel;
	private JButton btnOrderItem;
	private JButton btnOrderAll;
	
	public CheckInventoryPanel() {
		
		super();
		SubjectManager.getInstance().addNotification("CheckInventoryPanel", "CheckInventory", this);
		intitalizeComponent();
		refersh();
	}
		

	public void refersh() {
		products = prodMgr.getLowInventoryProdcutTableModel();
		
		dataModel.setDataVector(products, columnNames);
		dataModel.fireTableDataChanged();
		productTable.setVisible(false);
		productTable.setVisible(true);
	}
	
	public void intitalizeComponent(){
		
		GroupLayout thisLayout = new GroupLayout((JComponent) this);
		this.setLayout(thisLayout);
		setLocation(500, 500);
		//this.setPreferredSize(getRootPane().getSize());
		this.setPreferredSize(new java.awt.Dimension(500, 700));
		titleLabel = new JLabel();
		titleLabel.setText("Check Inventory" );
		titleLabel.setVerifyInputWhenFocusTarget(false);
		titleLabel.setForeground(Constants.STORE_APP_TITLE_COLOR);
		titleLabel.setFont(Constants.STORE_APP_TITLE_FONT);
		dataPane = addList();


			btnOrderItem = new JButton("Order Item");
			btnOrderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					performOrderItem();
				}
			});
			btnOrderAll = new JButton("Order All");
			btnOrderAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					performOrderAllItem();
				}
			});
			//btnOrderItem = new JButton("Order All");
			//btnOrderItem.addActionListener(new ActionListener() {
				//public void actionPerformed(ActionEvent arg0) {
					//performOrderAll();
				//}
			//});
		
		Group group1 = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(titleLabel,
				GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
				GroupLayout.PREFERRED_SIZE);

		Group group2 = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		group2.addComponent(btnOrderItem).addComponent(btnOrderAll);;

		thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap().addGroup(group1)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(dataPane, 0, 440, Short.MAX_VALUE).addGap(20).addGroup(group2));

		Group group3 = thisLayout.createSequentialGroup().addGap(350).addComponent(titleLabel,
				GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addGap(152);
		
		Group group4 = thisLayout.createSequentialGroup();
		
		group4.addComponent(btnOrderItem, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(30)
		.addComponent(btnOrderAll, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(25);
		thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(dataPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 780,
						GroupLayout.PREFERRED_SIZE)
				.addGroup(GroupLayout.Alignment.LEADING, group3).addGroup(GroupLayout.Alignment.CENTER, group4));
		
	}
	
 	protected JScrollPane addList(){
		manager = Store.getInstance();
		products = manager.getMgrProduct().getLowInventoryProdcutTableModel();
		dataPane = new JScrollPane();
		{	
			dataModel = new DefaultTableModel(products, columnNames);
			dataModel.setDataVector(products, columnNames);
			productTable = new BaseTable(dataModel);
			dataPane.setViewportView(productTable);
		}
		return dataPane;
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

			if (product == null) {
				try {

					throw new Exception("Error in loading prodcut information");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ReturnObject returnObj = manager.getMgrProduct().orderProdcut(product, product.getOrderQty());
			
			if ( returnObj != null && returnObj.isSuccess() ){
				JOptionPane.showMessageDialog(new JFrame(),
						returnObj.getMessage(),
    					"Success", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		refersh();
	}
	
	public void performOrderItem() {
		if (productTable.getSelectedRow() != -1) {
			int selectedRow = productTable.getSelectedRow();
			Product product = manager.getMgrProduct().getProductList().get(selectedRow);
			if (product == null) {
				
				
				try {
					throw new Exception("Error in loading prodcut information");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		 ReturnObject returnObj  = 	manager.getMgrProduct().orderProdcut(product, product.getOrderQty());
		 if ( returnObj!=null && returnObj.isSuccess()){
			 JOptionPane.showMessageDialog(getRootPane(),
						returnObj.getMessage(),
 					"Success", JOptionPane.INFORMATION_MESSAGE);
		 }

		}
		refersh();
	}
	
	public void performOrderAllItem(){
		 ReturnObject returnObj  = 	manager.getMgrProduct().orderAllLowInventoryProdcut();
		 if ( returnObj!=null && returnObj.isSuccess()){
			 JOptionPane.showMessageDialog(getRootPane(),
						returnObj.getMessage(),
					"All Items are ordered sucessfully!", JOptionPane.INFORMATION_MESSAGE);
		 }
		 refersh();
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