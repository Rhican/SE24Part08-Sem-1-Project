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
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class CheckoutMemberPanel extends JPanel {
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldRedeemPoint;
	private JTextField textFieldDiscount;
	private JLabel labelMaxRedeemPoint;

	/**
	 * Create the panel.
	 */
	public CheckoutMemberPanel() {
		
		JPanel panel = new JPanel();
		
		JLabel lblMemberId = new JLabel("Member ID:");
		
		JLabel label_1 = new JLabel("Name:");
		
		JLabel lblRedeemPoint = new JLabel("Redeem:");
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		textFieldName.setBackground(SystemColor.menu);
		
		textFieldRedeemPoint = new JTextField();
		textFieldRedeemPoint.setColumns(10);
		
		labelMaxRedeemPoint = new JLabel("/ Max");
		
		JLabel lblDiscount = new JLabel("Discount: ");
		
		textFieldDiscount = new JTextField();
		textFieldDiscount.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblDiscount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblRedeemPoint, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
						.addComponent(lblMemberId, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
					.addGap(4)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldRedeemPoint, 94, 94, 94)
								.addComponent(textFieldDiscount, 94, 94, 94))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelMaxRedeemPoint, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
						.addComponent(textFieldID, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(lblMemberId))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(textFieldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(label_1))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRedeemPoint)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelMaxRedeemPoint)
								.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDiscount))))
					.addGap(23))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 153, Short.MAX_VALUE)
					.addGap(6))
		);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldID, textFieldRedeemPoint, textFieldDiscount, labelMaxRedeemPoint, textFieldName, lblDiscount, lblRedeemPoint, lblMemberId, label_1}));
		setLayout(groupLayout);

	}
}
