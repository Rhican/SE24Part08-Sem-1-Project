package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CheckoutPayDialog extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldTotal;
	private JFormattedTextField textFieldPayment;
	private JTextField textFieldBalance;
	private JButton btnOkay;

	private double totalAmount, payAmount, balanceAmount;
	
	public void reset() {
		setVisible(false);
		totalAmount = 0;
		payAmount = 0;
		balanceAmount = 0;
	}
	public void show(double total) {
		totalAmount = total;
		textFieldTotal.setText(formatDecimal(totalAmount));
		textFieldPayment.setText(formatDecimal(totalAmount));
		setVisible(true);
	}
	
	private void UpdateBalance(double pay) {
		payAmount = pay;
		balanceAmount = totalAmount - payAmount;
		if (textFieldBalance != null) textFieldBalance.setText(formatDecimal(balanceAmount));
		if (btnOkay != null) btnOkay.setEnabled(balanceAmount <= 0.01f);
	}
	private String formatDecimal(double value) {
		DecimalFormat df = new DecimalFormat("##0.00"); 
		return df.format(value); 
	}
	/**
	 * Create the frame.
	 */
	public CheckoutPayDialog() {
		setAlwaysOnTop(true);
		setTitle("Payment");
		setBounds(100, 100, 286, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Total:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		textFieldTotal = new JTextField();
		textFieldTotal.setText("0");
		textFieldTotal.setEditable(false);
		textFieldTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldTotal.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Payment: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		NumberFormat format2 = NumberFormat.getNumberInstance();
		NumberFormatter formater2 = new NumberFormatter(format2);
		textFieldPayment = new JFormattedTextField(formater2);
		textFieldPayment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String payment = textFieldPayment.getText();
				if (payment == null || payment.isEmpty()) return;
				double pay = Double.valueOf(payment);
				UpdateBalance(pay);		
			}
		});
		textFieldPayment.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				String payment = textFieldPayment.getText();
				if (payment == null || payment.isEmpty()) return;
				double pay = Double.valueOf(payment);
				UpdateBalance(pay);				
			}
		});
		textFieldPayment.setText("0");
		textFieldPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldPayment.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Balance:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		textFieldBalance = new JTextField();
		textFieldBalance.setText("0");
		textFieldBalance.setEditable(false);
		textFieldBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
		textFieldBalance.setColumns(10);
		
		btnOkay = new JButton("Okay");
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubjectManager.getInstance().Update("CheckOutPanel", "Payment", "Done");
			}
		});
		btnOkay.setEnabled(false);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubjectManager.getInstance().Update("CheckOutPanel", "Payment", "Cancel");
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCancel)
							.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
							.addComponent(btnOkay)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, Alignment.LEADING)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldTotal, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addComponent(textFieldBalance, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addComponent(textFieldPayment, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textFieldTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textFieldPayment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldBalance, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2, Alignment.TRAILING))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnOkay))
					.addGap(28))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
