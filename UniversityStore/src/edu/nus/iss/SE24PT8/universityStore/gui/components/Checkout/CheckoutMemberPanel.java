package edu.nus.iss.SE24PT8.universityStore.gui.components.Checkout;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;
import javax.swing.SwingConstants;
import java.awt.Component;
//import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManager;

import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class CheckoutMemberPanel extends JPanel {
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldRedeemPoint;
	private JTextField textFieldDiscount;
	private JLabel labelMaxRedeemPoint;
	private Member member = null;
	private Discount discount = null;

	public void reset() {
		member = null;
		textFieldID.setText("");
		SearchForMemeber("");
		focusID();
	}
	public void focusID() {
		textFieldID.requestFocus();
	}
	
	private void SearchForMemeber(String memberID) {
		member = MemberManager.getInstance().getMember(memberID);
		if (member != null) {
			textFieldID.setText(member.getID());
			textFieldName.setText(member.getName());
			int maxPoint = member.getLoyaltyPoints();
			textFieldRedeemPoint.setText("0");
			labelMaxRedeemPoint.setText("/" + String.valueOf(maxPoint));	
			textFieldRedeemPoint.setEditable(true);
		}
		else {
			member = null;
			textFieldName.setText("");
			textFieldRedeemPoint.setText("0");
			labelMaxRedeemPoint.setText("/ Max");	
			textFieldRedeemPoint.setEditable(false);
		}
		SubjectManager.getInstance().Update("CheckOutPanel", "Member", "Update");
		UpdateDiscount();
	}
	private void UpdateDiscount() {
		discount = DiscountManager.getInstance().getMaxDiscount(new Date(), member);
		if (discount == null)  textFieldDiscount.setText("");
		else {
			textFieldDiscount.setText(String.valueOf(discount.getDiscountPercent()) + "%");
		}
		SubjectManager.getInstance().Update("CheckOutPanel", "Discount", "Update"); // no modification, no required 		
	}
	
	public Member getMemeber() {
		return this.member;
	}
	
	public Discount getDiscount() {
		return this.discount;
	}
	
	public int getRedeemPoint() {
		return Integer.valueOf(textFieldRedeemPoint.getText());
	}
	
	/**
	 * Create the panel.
	 */
	public CheckoutMemberPanel() {
		
		JPanel panel = new JPanel();
		
		JLabel lblMemberId = new JLabel("Member ID:");
		
		JLabel label_1 = new JLabel("Name:");
		
		JLabel lblRedeemPoint = new JLabel("Redeem:");
		
		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SearchForMemeber(textFieldID.getText());
				}
				else {
					super.keyReleased(e);
				}
			}
		});
		textFieldID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				SearchForMemeber(textFieldID.getText());
			}
		});
		textFieldID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		textFieldName.setBackground(SystemColor.menu);
		
		NumberFormat format = NumberFormat.getIntegerInstance();
		NumberFormatter pointFormater = new NumberFormatter(format);
		pointFormater.setAllowsInvalid(false); // this is the key!!
		pointFormater.setMinimum(0); // Optional

		textFieldRedeemPoint = new JFormattedTextField(pointFormater);
		textFieldRedeemPoint.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldRedeemPoint.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String text = textFieldRedeemPoint.getText();
				if (text.isEmpty()) return;
				SubjectManager.getInstance().Update("CheckOutPanel", "Member", "Update");
			}
		});
		textFieldRedeemPoint.setColumns(10);
		textFieldRedeemPoint.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int point = 0;
				try {
					point = Integer.parseInt(textFieldRedeemPoint.getText());
				} catch(NumberFormatException ex) {
					textFieldRedeemPoint.setText(Integer.toString(member.getLoyaltyPoints()));
				}
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:					
					point++;
					textFieldRedeemPoint.setText(Integer.toString(point));
					break;
				case KeyEvent.VK_DOWN:
					point--;
					break;
				case KeyEvent.VK_ENTER:
					SubjectManager.getInstance().Update("CheckOutPanel", "Member", "Update");
					break;
				default:
					super.keyReleased(e);					
					return;
				}
				textFieldRedeemPoint.setText(Integer.toString(point));
			}
		});
				
		textFieldRedeemPoint.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				String text = textFieldRedeemPoint.getText();
				if (text.isEmpty()) return;
				int point = Integer.parseInt(text);
				if (member == null && !textFieldRedeemPoint.getText().equals("")) {
					textFieldRedeemPoint.setText("");
					return;
				}
				int max = member.getLoyaltyPoints();
				if (point > max) {
					textFieldRedeemPoint.setText(Integer.toString(max));
					return;
				}
			}
		});
		
		labelMaxRedeemPoint = new JLabel("/ Max");
		
		JLabel lblDiscount = new JLabel("Discount: ");
		
		textFieldDiscount = new JTextField();
		textFieldDiscount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDiscount.setBackground(SystemColor.menu);
		textFieldDiscount.setEditable(false);
		textFieldDiscount.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblMemberId, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblRedeemPoint, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDiscount)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldRedeemPoint, 94, 94, 94)
								.addComponent(textFieldDiscount, 94, 94, 94))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelMaxRedeemPoint, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(textFieldID, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
						.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMemberId))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelMaxRedeemPoint)
						.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRedeemPoint))
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiscount))
					.addGap(23))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
					.addGap(3))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
					.addGap(6))
		);
		setLayout(groupLayout);
		//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldID, textFieldRedeemPoint, textFieldDiscount, textFieldName, panel, lblDiscount, lblRedeemPoint, lblMemberId, label_1, labelMaxRedeemPoint}));

	}
}
