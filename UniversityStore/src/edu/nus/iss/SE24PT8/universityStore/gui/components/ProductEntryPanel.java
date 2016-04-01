package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.nus.iss.SE24PT8.universityStore.Store;
import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;

public class ProductEntryPanel extends JPanel{
	private JTextField txtProdcutName;
	private JTextField txtBarCode;
	private JTextField txtQty;
	private JTextField txtPrice;
	private JTextField txtOrderQty;
	private JTextField txtReorderQty;
	JTextPane txtDescription;
	JButton btnAdd ;
	
	JComboBox comboCategory;
	
	Store store = Store.getInstance();
	
	private Dimension jTextFieldDimenstion = new Dimension(500, 150);
	
	public void createPanel() {
		
		JLabel lblPrductName = new JLabel("Prodcut Name");
		
		JLabel lblBarCode = new JLabel("Bar Code");
		
		JLabel lblDescription = new JLabel("Description");
		
		JLabel lblQuantity = new JLabel("Quantity");
		
		JLabel lblPrice = new JLabel("Price");
		
		JLabel lblReorderQuantity = new JLabel("Reorder Quantity");
		
		JLabel lblOrderQuantity = new JLabel("Order Quantity");
		
		JLabel lblCategory = new JLabel("Category");
		
		txtProdcutName = new JTextField();
		txtProdcutName.setSize(jTextFieldDimenstion);
		
		txtBarCode = new JTextField();
		txtBarCode.setSize(jTextFieldDimenstion);
		
		comboCategory = new JComboBox();
		comboCategory.setSize(jTextFieldDimenstion);
		
		txtDescription = new JTextPane();
		
		
		txtQty = new JTextField();
		txtQty.setSize(jTextFieldDimenstion);
		
		txtPrice = new JTextField();
		txtPrice.setSize(jTextFieldDimenstion);
		
		txtOrderQty = new JTextField();
		txtOrderQty.setSize(jTextFieldDimenstion);
		
		txtReorderQty = new JTextField();
		txtReorderQty.setSize(jTextFieldDimenstion);
		
		btnAdd = new JButton("ADD");
		btnAdd.setSize(jTextFieldDimenstion);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdd)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPrductName)
								.addComponent(lblBarCode)
								.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDescription)
								.addComponent(lblReorderQuantity)
								.addComponent(lblPrice)
								.addComponent(lblOrderQuantity, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblQuantity))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(txtDescription, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
												.addComponent(comboCategory, 0, 157, Short.MAX_VALUE)
												.addComponent(txtBarCode, 146, 146, 146)
												.addComponent(txtProdcutName, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
											.addGap(14))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(txtReorderQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(134))
								.addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtOrderQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrductName)
						.addComponent(txtProdcutName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblBarCode)
						.addComponent(txtBarCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDescription)
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategory)
						.addComponent(comboCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblQuantity)
						.addComponent(txtQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrice)
						.addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReorderQuantity)
						.addComponent(txtReorderQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrderQuantity)
						.addComponent(txtOrderQty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(48)
					.addComponent(btnAdd)
					.addGap(39))
		);
		setLayout(groupLayout);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewRecord();
				
			}
		});
	}
	
	public JButton getBtnAdd() {
		return btnAdd;
	}
	
	public void AddNewRecord(){
		
	//	store.getMgrProduct().addNewProduct(txtProdcutName.getText(), txtDescription.getText(),Integer.parseInt( txtQty.getText() )
		//		, Double.parseDouble(txtPrice.getText()), txtBarCode.getText(), Integer.parseInt(txtQty.getText()), Integer.parseInt(txtReorderQty.getText()),
			//store.getMgrCategory().getCategory("CLO"));
		store.getMgrProduct().addNewProduct("ABC", "ABC BRIEF", 3, 23, "12345", 20, 30,store.getMgrCategory().getCategory("CLO"));
	}
	
	public static void main(String args[]){
		Frame f = new JFrame();
		ProductEntryPanel panel  = new ProductEntryPanel();
		panel.createPanel();
		f.add(panel, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		
		
		
	}
}
