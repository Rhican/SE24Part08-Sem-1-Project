package edu.nus.iss.SE24PT8.universityStore.gui.components.Checkout;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;

//import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.domain.SaleItem;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;

/**
* CheckoutProductPanel for product GUI
* 
* @author Zehua
*/
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

	
	public void reset() {
		product = null;
		quantity = 1;
		resetUI();
	}
	public void focusID() {
		textFieldID.requestFocus();
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
			resetUI();
		}
		else
		{
			this.product = saleItem.getProduct();
			this.quantity = saleItem.getSaleQuantity();
			displayProductDetail(product, quantity);
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
		textFieldQuantity.requestFocus();
	}
	
	private boolean updateProductFound(Product product) {
		if (product == null) return false;
		if (this.product == null || 
			this.product.getProductId() != product.getProductId()) {
			this.product = product;

			displayProductDetail(product, 1);	  	
			SubjectManager.getInstance().Update("CheckOutPanel", "SaleItem", "Found");
			
			return true;
		}
		return false;
	}

	private void resetUI() {
		
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
		focusID();
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
		textFieldBarcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					getProductByBarcode(textFieldBarcode.getText());
				}
				else {
					super.keyReleased(e);
				}
			}
		});
		textFieldBarcode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				getProductByBarcode(textFieldBarcode.getText());
			}
		});
		textFieldBarcode.setColumns(10);

		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					getProductByID(textFieldID.getText());
				}
				else {
					super.keyReleased(e);
				}	
			}
		});
		textFieldID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				getProductByID(textFieldID.getText());
			}
		});
		textFieldID.setColumns(10);

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		textFieldName.setBackground(SystemColor.menu);

		textFieldPrice = new JTextField();
		textFieldPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		textFieldQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
					break;
				case KeyEvent.VK_DOWN:
					quantity--;
					break;
				case KeyEvent.VK_ENTER:
					buttonAdd.requestFocus();
					return;
					
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
				if (text.isEmpty() || product == null) return;
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
		textFieldSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
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

		
		buttonAdd = new JButton("Save");
		buttonAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					quantity = Integer.parseInt(textFieldQuantity.getText());
					SubjectManager.getInstance().Update("CheckOutPanel", "SaleItem", "Add");
					resetUI();
					return;
				}
				super.keyReleased(e);					
				return;
			}
		});
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quantity = Integer.parseInt(textFieldQuantity.getText());
				SubjectManager.getInstance().Update("CheckOutPanel", "SaleItem", "Add");
				resetUI();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblBarcode, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
										.addComponent(label))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
										.addGap(1)))
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(label_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_panel.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addComponent(textFieldID, Alignment.LEADING)
									.addComponent(textFieldBarcode, Alignment.LEADING)
									.addComponent(textFieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(textFieldSubTotal, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(textFieldPrice, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(textFieldQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(labelMaxQuantity, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonCancel)
							.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
							.addComponent(buttonAdd, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
					.addGap(21))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBarcode))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(labelMaxQuantity))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldSubTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonAdd)
						.addComponent(buttonCancel))
					.addGap(5))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
					.addGap(1))
		);
		/*panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textFieldID, textFieldBarcode,
				textFieldQuantity, buttonAdd, buttonCancel, textFieldName, textFieldPrice, textFieldSubTotal,
				labelMaxQuantity, label, lblBarcode, label_1, label_3, label_4, label_5 }));*/
		setFocusCycleRoot(true);
		setLayout(groupLayout);
		//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldID, textFieldBarcode, textFieldQuantity, buttonAdd, buttonCancel, panel, label_1, label, lblBarcode, label_5, label_4, label_3, textFieldName, textFieldSubTotal, textFieldPrice, labelMaxQuantity}));

	}

}
