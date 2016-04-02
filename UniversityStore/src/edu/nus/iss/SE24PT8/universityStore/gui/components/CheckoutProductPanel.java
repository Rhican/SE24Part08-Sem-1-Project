package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.manager.CategoryManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.beans.PropertyChangeEvent;
import javax.swing.JFormattedTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckoutProductPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldBarcode;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldPrice;
	private JFormattedTextField textFieldQuantity;
	private JTextField textFieldSubTotal;
	private JButton buttonCancel;
	private JButton buttonAdd;
	private JLabel labelMaxQuantity;
	private Product product = null;
	private int quantity = 0;

	{// Data insertions, for testing,to be removed
		CategoryManager.getInstance().addCategory("Cat1", "Category 1");
		CategoryManager.getInstance().addCategory("Cat2", "Category 2");
		CategoryManager.getInstance().addCategory("Cat3", "Category 3");

		ProductManager.getInstance().addNewProduct("Product1", "Product 1", 100, 2.0, "BC0001", 50, 20,
				CategoryManager.getInstance().getCategory("Cat1"));
		ProductManager.getInstance().addNewProduct("Product2", "Product 2", 100, 2.0, "BC0002", 50, 20,
				CategoryManager.getInstance().getCategory("Cat2"));
		ProductManager.getInstance().addNewProduct("Product3", "Product 3", 100, 2.0, "BC0003", 50, 20,
				CategoryManager.getInstance().getCategory("Cat3"));
		ProductManager.getInstance().addNewProduct("Product4", "Product 4", 100, 2.0, "BC0004", 50, 20,
				CategoryManager.getInstance().getCategory("Cat1"));

	}

	
	public Product getProduct() {
		return product;
	}
	
	public int getQuanity() {
		return quantity;
	}

	public void setSaleItem(SaleItem saleItem) {
		if (saleItem == null || saleItem.getProduct() == null)
		{
			reset();
		}
		else
		{
			//this.quantity = saleItem.getSaleQuantity();
			displayProductDetail(saleItem.getProduct(), this.quantity);
			textFieldQuantity.setText(Integer.toString(this.quantity));
			textFieldID.setEditable(false);
			textFieldBarcode.setEditable(false);
		}
	}
	
	private void getProductByID(String id) {
		Product product = ProductManager.getInstance().getProductByID(id);
		if (updateProductFound(product) ) {
			textFieldBarcode.setEditable(false);
			textFieldID.setEditable(true);
		}
	}

	private void getProductByBarcode(String barcode) {
		Product product = ProductManager.getInstance().getProductByBarcode(barcode);
		if (updateProductFound(product)) {
			textFieldID.setEditable(false);
			textFieldBarcode.setEditable(true);		
		}
	}

	private void displayProductDetail(Product product, int quantity) {
		if (product == null) return ;
		this.quantity = quantity;
		// Update GUI product fields
		textFieldBarcode.setText(product.getBarcode());
		textFieldID.setText(product.getProductId());
		textFieldName.setText(product.getProductName());
		String price = "$" + product.getPrice();
		textFieldPrice.setText(price);
		String maxQuantity = String.valueOf(product.getQty());
		textFieldQuantity.setText(Integer.toString(this.quantity));
		labelMaxQuantity.setText("/ " + maxQuantity);
		double subTotal = product.getPrice();
		textFieldSubTotal.setText(Double.toString(subTotal));
	}
	
	private boolean updateProductFound(Product product) {
		if (product == null) return false;
		if (this.product == null || 
			this.product.getProductId() != product.getProductId()) {
			this.product = product;

			displayProductDetail(product, 1);			
			return true;
		}
		return false;
	}

	private void reset() {
		// Reset GUI product fields
		textFieldBarcode.setText("");
		textFieldID.setText("");
		textFieldName.setText("");
		textFieldPrice.setText("");
		textFieldQuantity.setText("1");
		labelMaxQuantity.setText("/ Max");
		textFieldSubTotal.setText("");
		textFieldID.setEditable(true);
		textFieldBarcode.setEditable(true);
	}
	/**
	 * Create the panel.
	 */
	public CheckoutProductPanel() {

		JPanel panel = new JPanel();

		JLabel label = new JLabel("Product ID:");

		JLabel label_1 = new JLabel("Name:");

		JLabel lblBarcode = new JLabel("Barcode:");

		JLabel label_3 = new JLabel("Quantity:");

		JLabel label_4 = new JLabel("Price:");

		JLabel label_5 = new JLabel("Sub-total:");

		textFieldBarcode = new JTextField();
		textFieldBarcode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				getProductByBarcode(textFieldBarcode.getText());
			}
		});
		textFieldBarcode.setColumns(10);

		textFieldID = new JTextField();
		textFieldID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				getProductByID(textFieldID.getText());
			}
		});
		textFieldID.setColumns(10);

		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		textFieldName.setBackground(SystemColor.menu);

		textFieldPrice = new JTextField();
		textFieldPrice.setEditable(false);
		textFieldPrice.setColumns(10);
		textFieldPrice.setBackground(SystemColor.menu);

		NumberFormat format = NumberFormat.getIntegerInstance();
		NumberFormatter quantityFormater = new NumberFormatter(format);
		// numberFormatter.setValueClass(Integer.class); //optional, ensures you
		// will always get correct type
		quantityFormater.setAllowsInvalid(false); // this is the key!!
		quantityFormater.setMinimum(1); // Optional

		textFieldQuantity = new JFormattedTextField(quantityFormater);
		textFieldQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int quantity = 1;
				try {
					quantity = Integer.parseInt(textFieldQuantity.getText());
				} catch(NumberFormatException ex) {
					textFieldQuantity.setText(Integer.toString(product.getQty()));
				}
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:					
					quantity++;
					textFieldQuantity.setText(Integer.toString(quantity));
					break;
				case KeyEvent.VK_DOWN:
					quantity--;
					break;
				default:
					super.keyReleased(e);					
					return;
				}
				textFieldQuantity.setText(Integer.toString(quantity));
			}
		});
		textFieldQuantity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldQuantity.setSelectionStart(0);
				textFieldQuantity.setSelectionEnd(textFieldQuantity.getText().length());
			}
		});
		
		textFieldQuantity.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				String text = textFieldQuantity.getText();
				if (text.isEmpty()) return;
				int quantity = Integer.parseInt(text);
				int max = product.getQty();
				if (quantity > max) {
					textFieldQuantity.setText(Integer.toString(max));
					return;
				}
				else
				{
					double subTotal = quantity * product.getPrice();
					textFieldSubTotal.setText(Double.toString(subTotal));
				}
			}
		});
		textFieldQuantity.setColumns(10);

		textFieldSubTotal = new JTextField();
		textFieldSubTotal.setEditable(false);
		textFieldSubTotal.setColumns(10);
		textFieldSubTotal.setBackground(SystemColor.menu);

		labelMaxQuantity = new JLabel("/ Max");

		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});

		
		buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quantity = Integer.parseInt(textFieldQuantity.getText());
				SubjectManager.getInstance().Update("CheckOutPanel", "SaleItem", "Add");
				reset();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(10)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
										.addComponent(label)
										.addGroup(gl_panel.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblBarcode,
														GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
										.addGap(4))
								.addGroup(
										gl_panel.createSequentialGroup()
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(label_5, GroupLayout.DEFAULT_SIZE, 55,
																Short.MAX_VALUE)
												.addComponent(label_4, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
												.addComponent(label_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 55,
														Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
								.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(textFieldBarcode, Alignment.LEADING)
										.addComponent(textFieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 123,
												Short.MAX_VALUE)
										.addComponent(textFieldID, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 123,
												Short.MAX_VALUE))
								.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(textFieldSubTotal, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
										.addComponent(textFieldPrice, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
										.addComponent(textFieldQuantity, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(labelMaxQuantity, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE)
										.addGap(25))
								.addGroup(gl_panel.createSequentialGroup().addGap(57)
										.addComponent(buttonAdd, GroupLayout.PREFERRED_SIZE, 66,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())))
						.addComponent(buttonCancel))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(14).addComponent(label))
						.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(textFieldID,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGap(11)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBarcode))
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(18).addComponent(label_1))
						.addGroup(gl_panel.createSequentialGroup().addGap(11).addComponent(textFieldName,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(11)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textFieldQuantity, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(labelMaxQuantity))))
						.addGroup(gl_panel.createSequentialGroup().addGap(14).addComponent(label_3)))
				.addGap(11)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(label_4)))
				.addGap(11)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldSubTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(label_5))).addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(buttonCancel)
						.addComponent(buttonAdd)).addGap(13)));
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textFieldID, textFieldBarcode,
				textFieldQuantity, buttonAdd, buttonCancel, textFieldName, textFieldPrice, textFieldSubTotal,
				labelMaxQuantity, label, lblBarcode, label_1, label_3, label_4, label_5 }));
		setFocusCycleRoot(true);
		setLayout(groupLayout);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textFieldID, textFieldBarcode,
				textFieldQuantity, buttonAdd, buttonCancel, panel, label_1, label, lblBarcode, label_5, label_4,
				label_3, textFieldName, textFieldSubTotal, textFieldPrice, labelMaxQuantity }));

	}

}
