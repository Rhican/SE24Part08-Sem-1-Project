package edu.nus.iss.SE24PT8.universityStore.gui.components.Checkout;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;
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
import javax.swing.JSeparator;

/**
* CheckoutMemberPanel for member and discount GUI
*
* @author Zehua
*/
public class CheckoutMemberPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldRedeemPoint;
	private JTextField textFieldDiscount;
	private JLabel labelMaxRedeemPoint;
	private Member member = null;
	private Discount discount = null;
	private Discount defaultDiscount = null;
	private JTextField textFieldDiscountDescription;
	private JTextField textFieldDiscountType;

	public void reset() {
		member = null;
		textFieldID.setText("");
		SearchForMemeber("");
		focusID();
	}

	public void focusID() {
		UpdateDiscount();
		textFieldID.requestFocus();
	}

	public Member getMemeber() {
		return this.member;
	}

	public Discount getDiscount() {
		return this.discount;
	}

	public int getRedeemPoint() {
		return Integer.valueOf(textFieldRedeemPoint.getText().replace(",", ""));
	}

	public void setDefaultDiscount(Discount discount) {
		defaultDiscount = discount;
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
			textFieldRedeemPoint.setEditable(maxPoint > 0);
		} else {
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
		discount = (member != null) ? DiscountManager.getInstance().getMaxDiscount(new Date(), member)
				: defaultDiscount;

		if (discount == null)
		{
			textFieldDiscount.setText("");
			textFieldDiscountDescription.setText("No Discount Available.");
			textFieldDiscountType.setText("");
		}
		else {
			textFieldDiscount.setText(String.valueOf(discount.getDiscountPercent()) + "%");
			textFieldDiscountDescription.setText(discount.getDiscountDes());
			String type = discount.getApplicableFor().replace("M", "Member").replace("A", "Public");
			if (discount.isIsPeriodAlways() && discount.isIsStartDateAlways()) type += " [Always]";
			else type += " [Period]";
			textFieldDiscountType.setText(type);
		}
		SubjectManager.getInstance().Update("CheckOutPanel", "Discount", "Update"); // no
																					// modification,
																					// no
																					// required
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
				} else {
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
				if (text.isEmpty())
					return;
				SubjectManager.getInstance().Update("CheckOutPanel", "Member", "Update");
			}
		});
		textFieldRedeemPoint.setColumns(10);
		textFieldRedeemPoint.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int point = 0;
				try {
					point = Integer.parseInt(textFieldRedeemPoint.getText().replace(",", ""));
				} catch (NumberFormatException ex) {
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
				if (text.isEmpty())
					return;
				int point = Integer.parseInt(text.replace(",", ""));
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
		
		JLabel lblNewLabel = new JLabel("Name: ");
		
		textFieldDiscountDescription = new JTextField();
		textFieldDiscountDescription.setEditable(false);
		textFieldDiscountDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldDiscountDescription.setBackground(SystemColor.menu);
		textFieldDiscountDescription.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Type:");
		
		JSeparator separator = new JSeparator();
		
		textFieldDiscountType = new JTextField();
		textFieldDiscountType.setBackground(SystemColor.menu);
		textFieldDiscountType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldDiscountType.setEditable(false);
		textFieldDiscountType.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMemberId, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
								.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
								.addComponent(lblRedeemPoint, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblDiscount, Alignment.LEADING)
									.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldID, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelMaxRedeemPoint, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
								.addComponent(textFieldDiscountDescription, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addComponent(textFieldDiscountType, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))))
					.addGap(16))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMemberId)
						.addComponent(textFieldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldRedeemPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRedeemPoint)
						.addComponent(labelMaxRedeemPoint))
					.addGap(13)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiscount)
						.addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textFieldDiscountDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textFieldDiscountType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(61))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
					.addGap(2))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		// setFocusTraversalPolicy(new FocusTraversalOnArray(new
		// Component[]{textFieldID, textFieldRedeemPoint, textFieldDiscount,
		// textFieldName, panel, lblDiscount, lblRedeemPoint, lblMemberId,
		// label_1, labelMaxRedeemPoint}));

	}
}
