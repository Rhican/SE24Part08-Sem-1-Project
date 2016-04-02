package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.manager.TransactionManager;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckoutPanel extends JPanel implements INotificable {
	private JTable tableSaleItems;
	private JTextField textFieldDiscountAmount;
	private JTextField textFieldRedeemPoint;
	private JTextField textFieldNetAmount;
	private JTextField textFieldTotalAmount;
	private CheckoutProductPanel productPanel = new CheckoutProductPanel();
	private CheckoutMemberPanel member = new CheckoutMemberPanel();
	private JPanel panelLeft;
	private JButton btnSave;
	private Transaction transaction = TransactionManager.getInstance().getNewTransaction();

	private void switchPanel(String panel) {
		panelLeft.setVisible(false);
		if (panel.equalsIgnoreCase("Product")) {
			panelLeft.remove(member);
			panelLeft.add(productPanel);
			btnSave.setText("Save");
		} else if (panel.equalsIgnoreCase("Member")) {
			panelLeft.remove(productPanel);
			panelLeft.add(member);
			btnSave.setText("Pay");
		}
		panelLeft.setVisible(true);
	}

	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("CheckOutPanel") && topic.equals("SaleItem")) {
			if (data.equalsIgnoreCase("Add"))
				handleAddSaleItem();
			else if (data.equalsIgnoreCase("Edit"))
				handleEditSaleItem();
			else if (data.equalsIgnoreCase("Remove"))
				handleRemoveSaleItem();
		}
	}

	private void handleAddSaleItem() {
		Product product = this.productPanel.getProduct();
		int quantity = productPanel.getQuanity();
		transaction.addSaleItem(product, quantity);
		UpdateSaleItemTable();
		System.out.println("SaleItem: " + product.getProductId() + " Add " + quantity);
	}

	private void handleEditSaleItem() {
		Product product = this.productPanel.getProduct();
		int quantity = productPanel.getQuanity();
		for (SaleItem saleitem : transaction.getSaleItems()) {
			if (saleitem.getProductID() == product.getProductId()) {
				saleitem.changeQuantity(quantity);
				UpdateSaleItemTable();
				break;
			}
		}
		System.out.println("SaleItem: " + product.getProductId() + " Edit " + quantity);
	}

	private void handleRemoveSaleItem() {
		Product product = this.productPanel.getProduct();
		for (SaleItem saleitem : transaction.getSaleItems()) {
			if (saleitem.getProductID() == product.getProductId()) {
				transaction.removeSaleItem(saleitem);
				UpdateSaleItemTable();
				break;
			}
		}
		System.out.println("SaleItem: " + product.getProductId() + " Remove ");
	}

	private void UpdateSaleItemTable() {
		final ArrayList<SaleItem> saleItems = transaction.getSaleItems();
		// TODO: can enable column sorting.
		Collections.sort(saleItems,
				(saleItem1, saleItem2) -> saleItem1.getProductID().compareTo(saleItem2.getProductID()));

		tableSaleItems.setVisible(false);// Hide first
		DefaultTableModel dataModel = (DefaultTableModel) tableSaleItems.getModel();
		if (dataModel != null) {
			int max = dataModel.getRowCount();
			for (int rowIndex = 0; rowIndex < max; rowIndex++) {
				dataModel.removeRow(0);
			}

			for (SaleItem saleItem : saleItems) {
				Object[] row = new Object[4];
				/*JButton button = new JButton("Delete");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					}
				});*/
				Product product = saleItem.getProduct();
				row[0] = product.getProductId();
				row[1] = product.getProductName();
				row[2] = saleItem.getSaleQuantity();
				row[3] = formatDollar(saleItem.getSubTotal());
				dataModel.addRow(row);
			}
		}
		tableSaleItems.setVisible(true); // Show when it is updated

		UpdateTransationSummary();
	}

	private void UpdateTransationSummary() {
		textFieldTotalAmount.setText(formatDollar(transaction.getTotalAmount()));
		textFieldRedeemPoint.setText(formatDollar(transaction.getRedeemedAmount())); 		
		textFieldDiscountAmount.setText(formatDollar(transaction.getDiscountAmount()));
		textFieldNetAmount.setText(formatDollar(transaction.getNetAmount())); 		
	}

	private String formatDollar(double value) {
		DecimalFormat df = new DecimalFormat("##0.00"); 
		return "$ " + df.format(value); 
	}
	
	private void handleSaleItemSelect() {
		int rowIndex = tableSaleItems.getSelectedRow();
		if (rowIndex < 0 ) return;
		String id = tableSaleItems.getModel().getValueAt(rowIndex, 0).toString();
		if (!id.isEmpty())
		{
			SaleItem saleItem = transaction.getSaleItem(id);
			if (saleItem != null)
			{
				this.productPanel.setSaleItem(saleItem);
			}
		}
		
	}
	/**
	 * Create the panel.
	 */
	public CheckoutPanel() {

		JPanel panel = new JPanel();

		panelLeft = new JPanel();

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 249,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
										.addGap(0)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panelLeft, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312,
										Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)).addGap(12)));
		panelLeft.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setName("");
		scrollPane_2.setRequestFocusEnabled(false);
		scrollPane_2.setFocusTraversalKeysEnabled(false);
		scrollPane_2.setFocusable(false);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnSave.getText().equalsIgnoreCase("Save"))
					switchPanel("Member");
				else
					switchPanel("Product");

			}
		});

		JLabel lblDiscount = new JLabel("Discount:");

		textFieldDiscountAmount = new JTextField();
		textFieldDiscountAmount.setText("$ 0.00");
		textFieldDiscountAmount.setBackground(SystemColor.menu);
		textFieldDiscountAmount.setEditable(false);
		textFieldDiscountAmount.setColumns(10);

		textFieldRedeemPoint = new JTextField();
		textFieldRedeemPoint.setText("$ 0.00");
		textFieldRedeemPoint.setBackground(SystemColor.menu);
		textFieldRedeemPoint.setEditable(false);
		textFieldRedeemPoint.setColumns(10);

		JLabel lblRedeemPoint = new JLabel("Redeem Point: ");

		JLabel lblNewLabel_3 = new JLabel("Net Amount:");

		textFieldNetAmount = new JTextField();
		textFieldNetAmount.setText("$ 0.00");
		textFieldNetAmount.setBackground(SystemColor.menu);
		textFieldNetAmount.setEditable(false);
		textFieldNetAmount.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);

		JLabel lblTotal = new JLabel("Total Amount:");

		textFieldTotalAmount = new JTextField();
		textFieldTotalAmount.setText("$ 0.00");
		textFieldTotalAmount.setBackground(SystemColor.menu);
		textFieldTotalAmount.setEditable(false);
		textFieldTotalAmount.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING,
								gl_panel.createSequentialGroup()
										.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel.createSequentialGroup().addComponent(lblNewLabel_3)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(textFieldNetAmount, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel.createSequentialGroup().addComponent(lblDiscount)
														.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(
																textFieldDiscountAmount, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.TRAILING,
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup().addComponent(lblRedeemPoint)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								gl_panel.createSequentialGroup().addComponent(lblTotal)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(textFieldTotalAmount, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldTotalAmount, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTotal))
				.addGap(8)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldDiscountAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiscount))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblRedeemPoint).addComponent(
						textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave).addComponent(textFieldNetAmount, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)).addContainerGap()));

		tableSaleItems = new JTable();
		tableSaleItems.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		tableSaleItems.setRequestFocusEnabled(false);
		tableSaleItems.setShowVerticalLines(false);
		scrollPane_2.setViewportView(tableSaleItems);
		tableSaleItems.setModel(
				new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"ID", "Name", "Quantity", "Sub Total"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSaleItems.getColumnModel().getColumn(0).setMaxWidth(400);
		tableSaleItems.getColumnModel().getColumn(1).setMinWidth(50);
		tableSaleItems.getColumnModel().getColumn(1).setMaxWidth(800);
		tableSaleItems.getColumnModel().getColumn(2).setMaxWidth(100);
		tableSaleItems.getColumnModel().getColumn(3).setMaxWidth(120);
		tableSaleItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSaleItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		        if (tableSaleItems.getSelectedRow() >= 0) {
		            handleSaleItemSelect();
		        }
		    }
		});

		panel.setLayout(gl_panel);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { btnSave, textFieldTotalAmount,
				textFieldDiscountAmount, textFieldRedeemPoint, textFieldNetAmount, scrollPane_2, tableSaleItems,
				lblNewLabel_3, lblDiscount, lblRedeemPoint, separator, lblTotal }));
		setLayout(groupLayout);
				
		
		SubjectManager.getInstance().addNotification("CheckOutPanel", "SaleItem", this);
		switchPanel("Product");
	}

}
