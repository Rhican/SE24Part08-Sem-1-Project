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
import edu.nus.iss.SE24PT8.universityStore.exception.BadProductException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.ProdcutEntryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

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
	//private ProductManager prodMgr = manager.getMgrProduct();
	
	
	private JLabel titleLabel;
	private JButton btnOrderItem;
	private JButton btnOrderAll;
	
	public CheckInventoryPanel() {
		
		super();
		SubjectManager.getInstance().addNotification("ProductPanel", "Product", this);
		SubjectManager.getInstance().addNotification("CheckOutPanel", "CheckOut", this);
		intitalizeComponent();
		refersh();
	}
		

	public void refersh() {
		products =  manager.getMgrProduct().getLowInventoryProdcutTableModel();
		dataModel.setDataVector(products, columnNames);
		dataModel.fireTableDataChanged();
		productTable.setVisible(false);
		productTable.setVisible(true);
	}
	
	public void intitalizeComponent(){
		
		GroupLayout thisLayout = new GroupLayout((JComponent) this);
		this.setLayout(thisLayout);
		setLocation(500, 500);
		this.setPreferredSize(new java.awt.Dimension(500, 300));
		titleLabel = new JLabel();
		titleLabel.setText("Check Inventory");
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
	
	public void performOrderItem() {
		if (productTable.getSelectedRow() != -1) {
			String producID=productTable.getValueAt(productTable.getSelectedRow(),0).toString();
			Product product = manager.getMgrProduct().getProductByID(producID);
			//Product product = manager.getMgrProduct().getProductList().get(selectedRow);
			if (product == null) {
				try {
					//throw new Exception("Error in loading product information");
					 JOptionPane.showMessageDialog(getRootPane(),
							 "Error in loading product information",
		 					"Error", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		try{
			manager.getMgrProduct().orderProdcut(product, product.getOrderQty());
			refersh();
			
		}catch( BadProductException e){
			JOptionPane.showMessageDialog(getRootPane(),
					"Order successful!",
					"Success", JOptionPane.INFORMATION_MESSAGE);
		}

		}
		
	}
	
	public void performOrderAllItem(){
		 try{
			 manager.getMgrProduct().orderAllLowInventoryProdcut();
		 }
		 catch (BadProductException e){
		 JOptionPane.showMessageDialog(getRootPane(),
						"All Items are ordered sucessfully!",
					"Success", JOptionPane.INFORMATION_MESSAGE);
		 }
		 refersh();
		 SubjectManager.getInstance().Update("ProductPanel", "Product", "Modify");
	}

	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("ProductPanel") && topic.equals("Product")) {
			if (data.equalsIgnoreCase("Add")) {
				refersh();
			}
			else if (data.equalsIgnoreCase("Modify")) {
				refersh();
			}
		}
		else if (group.equals("CheckOutPanel") && topic.equals("CheckOut")) {
			if (data.equalsIgnoreCase("Complete")) {
				refersh();
			}
		}

		//System.out.println("Group: " + group + "Topic: " + topic + " \n data: " + data);
		refersh();
	}
}