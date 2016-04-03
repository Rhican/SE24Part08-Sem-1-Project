package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
 *
 * @author Mugunthan
 */
public abstract class BaseModulePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String title;
	private JLabel titleLabel;
	private JScrollPane dataPane;
	private JButton addButton;
	private JButton deleteButton;
	private JButton modifyButton;
	private String[] allowedOperations;

	public BaseModulePanel(String title, String[] allowedOperations) {
		super();
		this.title = title;
		this.allowedOperations = allowedOperations;
		initComponents();

	}

	protected void initComponents() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) this);
			this.setLayout(thisLayout);
			setLocation(500, 500);
			this.setPreferredSize(new java.awt.Dimension(500, 491));
			titleLabel = new JLabel();
			titleLabel.setText(title + " List");
			titleLabel.setVerifyInputWhenFocusTarget(false);
			titleLabel.setForeground(Constants.STORE_APP_TITLE_COLOR);
			titleLabel.setFont(Constants.STORE_APP_TITLE_FONT);
			dataPane = addList();
			if (Arrays.asList(allowedOperations).contains(Constants.ADD_OPERATION)) {
				addButton = new JButton();
				addButton.setText("Add " + title);
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						performAddAction();
					}
				});
			}

			if (Arrays.asList(allowedOperations).contains(Constants.DELETE_OPERATION)) {
				deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						performDeleteAction();
					}
				});
			}

			if (Arrays.asList(allowedOperations).contains(Constants.MODIFY_OPERATION)) {
				modifyButton = new JButton("Modify");
				modifyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						performModifyAction();
					}
				});
			}
			Group group1 = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(titleLabel,
					GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
					GroupLayout.PREFERRED_SIZE);
			if (Arrays.asList(allowedOperations).contains(Constants.ADD_OPERATION))
				group1.addComponent(addButton);

			Group group2 = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
			if (Arrays.asList(allowedOperations).contains(Constants.MODIFY_OPERATION))
				group2.addComponent(modifyButton);
			if (Arrays.asList(allowedOperations).contains(Constants.DELETE_OPERATION))
				group2.addComponent(deleteButton).addGap(105, 105, 105);

			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap().addGroup(group1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(dataPane, 0, 440, Short.MAX_VALUE).addGap(20).addGroup(group2));

			Group group3 = thisLayout.createSequentialGroup().addGap(102).addComponent(titleLabel,
					GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addGap(152);
			if (Arrays.asList(allowedOperations).contains(Constants.ADD_OPERATION))
				group3.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE);
			Group group4 = thisLayout.createSequentialGroup();
			if (Arrays.asList(allowedOperations).contains(Constants.MODIFY_OPERATION))
				group4.addComponent(modifyButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGap(25);
			if (Arrays.asList(allowedOperations).contains(Constants.DELETE_OPERATION))
				group4.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE);
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
					.addComponent(dataPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 500,
							GroupLayout.PREFERRED_SIZE)
					.addGroup(GroupLayout.Alignment.LEADING, group3).addGroup(GroupLayout.Alignment.CENTER, group4));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected abstract JScrollPane addList();

	protected abstract void performAddAction();

	protected abstract void performDeleteAction();

	protected abstract void performModifyAction();

}
