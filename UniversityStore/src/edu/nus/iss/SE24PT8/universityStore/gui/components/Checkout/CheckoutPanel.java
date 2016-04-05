package edu.nus.iss.SE24PT8.universityStore.gui.components.Checkout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.SystemColor;

import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.domain.Transaction;
import edu.nus.iss.SE24PT8.universityStore.domain.TransactionInterface;
import edu.nus.iss.SE24PT8.universityStore.exception.TransactionException;
import edu.nus.iss.SE24PT8.universityStore.externalDevice.Printer;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
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
	private CheckoutMemberPanel memberPanel = new CheckoutMemberPanel();
	private CheckoutPayDialog paymentDialog = null;
	private Printer printer = new Printer();
	private JPanel panelLeft;
	private JButton btnPage;
	private TransactionInterface transaction = TransactionManager.getInstance().getNewTransaction();
	private JButton btnDelete;
	private JButton btnPay;

	private void switchPanel(String panel) {
		panelLeft.setVisible(false); 
		if (panel.equalsIgnoreCase("Product")) {
			panelLeft.remove(memberPanel);
			panelLeft.add(productPanel);
			productPanel.focusID();
			btnPage.setText("Next");
		} else if (panel.equalsIgnoreCase("Member")) {
			panelLeft.remove(productPanel);
			panelLeft.add(memberPanel);
			memberPanel.focusID();
			btnPage.setText("Previous");
		}
		UpdatePaymentButton();
		panelLeft.setVisible(true);
	}

	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("CheckOutPanel") && topic.equals("SaleItem")) {
			if (data.equalsIgnoreCase("Add")){
				handleAddSaleItem();
			}
		}
		if (group.equals("CheckOutPanel") && topic.equals("SaleItem")) {
			if (data.equalsIgnoreCase("Found")){
				handleFoundProduct();
			}
		}
		else if (group.equals("CheckOutPanel") && topic.equals("Member")) {
			if (data.equalsIgnoreCase("Update")){
				handleUpdateMember();
			}
		}
		else if (group.equals("CheckOutPanel") && topic.equals("Discount")) {
			if (data.equalsIgnoreCase("Update")){
				handleUpdateDiscount();
			}
		}
		else if (group.equals("CheckOutPanel") && topic.equals("Payment")) {
			if (data.equalsIgnoreCase("Done")){
				handlePaymentComplete();
			}
			else if (data.equalsIgnoreCase("Back")){
				handlePaymentAbort();
			}
			else if (data.equalsIgnoreCase("Cancel")){
				handlePaymentCancel();
			}
		}
		
	}

	private void handleUpdateMember() {
		Member member = memberPanel.getMemeber();
		transaction.setMember(member.getID());
		transaction.setRedeemedPoint(memberPanel.getRedeemPoint());
		UpdateTransationSummary();
	}
	private void handleUpdateDiscount() {
		transaction.setDiscount(memberPanel.getDiscount());
		UpdateTransationSummary();
	}

	private void handleAddSaleItem() {
		Product product = productPanel.getProduct();
		int quantity = productPanel.getQuanity();
		
		try {
			transaction.addSaleItem(product, quantity);
		} catch (TransactionException e) {
			JOptionPane.showMessageDialog(getRootPane(),
		   			 "Fail to Add Sale Item \n" + e.getMessage(),
						"Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		UpdateSaleItemTable();
		btnDelete.setEnabled(false);
		productPanel.reset();
	}
	private void handleFoundProduct() {
		Product product = productPanel.getProduct();
		if (product == null) return;
		SaleItem saleItem =  transaction.getSaleItem(product.getProductId());
		if (saleItem == null) return;
		productPanel.setSaleItem(saleItem);
	}

	private void handleSaleItemSelect() {
		int rowIndex = tableSaleItems.getSelectedRow();
		if (rowIndex < 0 ) {
			btnDelete.setEnabled(false);
			return;
		}
		String id = tableSaleItems.getModel().getValueAt(rowIndex, 0).toString();
		if (!id.isEmpty())
		{
			SaleItem saleItem = transaction.getSaleItem(id);
			if (saleItem != null)
			{
				productPanel.setSaleItem(saleItem);
				btnDelete.setEnabled(true);
				return;
			}
		}
		else
		{
			btnDelete.setEnabled(false);
		}		
	}
	private void handleDeleteSaleItem() {
		int rowIndex = tableSaleItems.getSelectedRow();
		if (rowIndex < 0 ) {
			btnDelete.setEnabled(false);
			return;
		}
		String id = tableSaleItems.getModel().getValueAt(rowIndex, 0).toString();
		if (!id.isEmpty())
		{
			SaleItem saleItem = transaction.getSaleItem(id);
			try {
				transaction.removeSaleItem(saleItem);
			} catch (TransactionException e) {
				JOptionPane.showMessageDialog(getRootPane(),
			   			 "Fail to Add Sale Item \n" + e.getMessage(),
							"Exception", JOptionPane.ERROR_MESSAGE);
			}
			UpdateSaleItemTable();
			productPanel.reset();
		}
	}
	
	private void checkAndGoToPayment() {
		if (memberPanel.getMemeber() == null) {
			if (JOptionPane.showConfirmDialog(getRootPane(), "Do you have a member ID?", "Alert",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				switchPanel("Member");
				return;
			}
		}
		if (paymentDialog == null) paymentDialog = new CheckoutPayDialog();
		paymentDialog.show(transaction.getNetAmount());
	}
	
	private void handlePaymentComplete() {
		TransactionManager manager = TransactionManager.getInstance();
		try {
			if ( manager.closeTransaction(transaction) )
			{
				printTransaction(transaction);
				JOptionPane.showMessageDialog(getRootPane(), "Transaction Completed #" +  transaction.getId(), "Success", JOptionPane.INFORMATION_MESSAGE);
				checkForLowInventoryProduct(transaction.getSaleItems());
				
				transaction = manager.getNewTransaction();
				switchPanel("Product");
				UpdateSaleItemTable();		
				paymentDialog.reset();
				memberPanel.reset();
				productPanel.reset();				
			}
			else {
				JOptionPane.showMessageDialog(getRootPane(),
			   			 "Fail to Save transaction",
							"Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (TransactionException e) {
			JOptionPane.showMessageDialog(getRootPane(),
		   			 "Fail to Close transaction\n" + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	private void checkForLowInventoryProduct(ArrayList<SaleItem> saleItems) {
		ArrayList<Product> productList = new ArrayList<Product>();
		for(SaleItem saleitem : saleItems) {
			productList.add(saleitem.getProduct());
		}
		ArrayList<Product> lowInventoryProducts = ProductManager.getInstance().getLowerInventoryProducts(productList);
		if (lowInventoryProducts.size() > 0) {
			String productString = "";
			for(Product product : lowInventoryProducts) {
				productString += product.getProductId() + ", " + product.getProductName() + ", " + " [" + product.getQty() + "]\n";
			}
			JOptionPane.showMessageDialog(getRootPane(), "Inventory Low Alert! \n" +  productString, "Inventory", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void handlePaymentAbort() {
		paymentDialog.reset();
	}
	private void handlePaymentCancel() {
		if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to reset current checkout?", "WARNING",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			transaction = TransactionManager.getInstance().getNewTransaction();
			switchPanel("Product");
			UpdateSaleItemTable();		
			paymentDialog.reset();
			memberPanel.reset();
			productPanel.reset();
		} else {
			paymentDialog.reset();
		}
		
	}
	
	private void printTransaction(TransactionInterface transaction) {
		String data = "\n ============================================================" + 
				"\n\t\t\t University Store  " + 
				"\n\t\t\t    Receipt # " + transaction.getId() + " \n "+ 
				"\n From: SE24PT8 " + 
				"\n Date: " +  transaction.getDate().toString() +  "\n\n " + transaction.getSaleItems().size() + " SaleItems: " +
				"\n ------------------------------------------------------------ \n" ;
				
		for( SaleItem saleitem : transaction.getSaleItems()) {
			data += " \t" + saleitem.getSaleQuantity() + " x " + saleitem.getProduct().getProductName() + "\t\t " + formatDollar(saleitem.getSubTotal()) + "\n";
		}
		data += " ------------------------------------------------------------ "; 	
		data += "\n\t\t\t\t    Total Amount: " + formatDollar(transaction.getTotalAmount());
		data += "\n\t\t\t\t   Redeem Amount: " + formatDollar(transaction.getRedeemedAmount());
		data += "\n\t\t\t\t Discount Amount: " + formatDollar(transaction.getDiscountAmount());
		data += "\n\t\t\t\t      Net Amount: " + formatDollar(transaction.getNetAmount());
		data += "\n ============================================================"; 	
		printer.print(data);
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
				Object[] row = new Object[5];
				Product product = saleItem.getProduct();
				row[0] = product.getProductId();
				row[1] = product.getProductName();
				row[2] = formatDollar(product.getPrice());
				row[3] = saleItem.getSaleQuantity();
				row[4] = formatDollar(saleItem.getSubTotal());
				dataModel.addRow(row);
			}

			if (saleItems.size() > 0) this.btnPage.setEnabled(true);
		}
		tableSaleItems.setVisible(true); // Show when it is updated

		UpdateTransationSummary();
	}

	private void UpdateTransationSummary() {
		textFieldTotalAmount.setText(formatDollar(transaction.getTotalAmount()));
		textFieldRedeemPoint.setText(formatDollar(transaction.getRedeemedAmount())); 		
		textFieldDiscountAmount.setText(formatDollar(transaction.getDiscountAmount()));
		textFieldNetAmount.setText(formatDollar(transaction.getNetAmount())); 	
		UpdatePaymentButton();
	}
	
	private void UpdatePaymentButton()
	{
		btnPay.setEnabled(transaction.getSaleItems().size() > 0 && !btnPage.getText().equalsIgnoreCase("Next")  );
	}

	private String formatDollar(double value) {
		DecimalFormat df = new DecimalFormat("##0.00"); 
		return "$ " + df.format(value); 
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
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
						.addComponent(panelLeft, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
					.addGap(7))
		);
		panelLeft.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setName("");
		scrollPane_2.setRequestFocusEnabled(false);
		scrollPane_2.setFocusTraversalKeysEnabled(false);
		scrollPane_2.setFocusable(false);

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
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDeleteSaleItem();
			}
		});
		btnDelete.setEnabled(false);
		
		btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAndGoToPayment();
			}
		});
		btnPay.setEnabled(false);
		
				btnPage = new JButton("Next");
				btnPage.setEnabled(false);
				btnPage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (btnPage.getText().equalsIgnoreCase("Next"))
							switchPanel("Member");
						else
							switchPanel("Product");

					}
				});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
							.addComponent(lblDiscount)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldDiscountAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblRedeemPoint)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
							.addComponent(lblTotal)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnPage, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnPay, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldNetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotal)
						.addComponent(btnDelete))
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldDiscountAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiscount))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRedeemPoint)
						.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldNetAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(btnPage)
						.addComponent(btnPay))
					.addContainerGap())
		);

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
				{null, null, null, null, null},
			},
			new String[] {
				"ID", "Name", "Price", "Quantity", "Sub Total"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSaleItems.getColumnModel().getColumn(0).setMaxWidth(100);
		tableSaleItems.getColumnModel().getColumn(1).setMinWidth(50);
		tableSaleItems.getColumnModel().getColumn(1).setMaxWidth(800);
		tableSaleItems.getColumnModel().getColumn(2).setMaxWidth(200);
		tableSaleItems.getColumnModel().getColumn(3).setMaxWidth(300);
		tableSaleItems.getColumnModel().getColumn(4).setMaxWidth(300);
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
		setLayout(groupLayout);
				
		
		SubjectManager.getInstance().addNotification("CheckOutPanel", "SaleItem", this);
		SubjectManager.getInstance().addNotification("CheckOutPanel", "Member", this);
		SubjectManager.getInstance().addNotification("CheckOutPanel", "Discount", this);
		SubjectManager.getInstance().addNotification("CheckOutPanel", "Payment", this);
		switchPanel("Product");
	}

	
}
