package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.GroupLayout;
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
public abstract class BaseModulePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private String title;
	private JLabel titleLabel;
	private JScrollPane dataPane;
	private JButton addButton;
	private JButton deleteButton;
	private JButton modifyButton;
	
	public BaseModulePanel(String title) {
		this.title = title;
		initComponents();
		
	}

	protected void initComponents() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) this);
			this.setLayout(thisLayout);
			setLocation(500,500);	
			this.setPreferredSize(new java.awt.Dimension(500, 491));
//			{
				titleLabel = new JLabel();
				titleLabel.setText(title + " List");
				titleLabel.setVerifyInputWhenFocusTarget(false);
				titleLabel.setForeground(Constants.STORE_APP_TITLE_COLOR);
				titleLabel.setFont(Constants.STORE_APP_TITLE_FONT);
				dataPane = addList();
				addButton = new JButton();
				addButton.setText("Add " + title);
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
		                performAddAction ();
					}
				});
				
				deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent arg0) {
		                performDeleteAction ();
				    }
				});
				
				modifyButton = new JButton("Modify");
				modifyButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent arg0) {
				    	performModifyAction ();
				    }
				});
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap()
					.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(titleLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE,
									GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					// .addComponent(jBtnBckMain,
					// GroupLayout.Alignment.BASELINE,
					// GroupLayout.PREFERRED_SIZE, 22,
					// GroupLayout.PREFERRED_SIZE)
					.addComponent(addButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 31,
							GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(dataPane, 0, 440, Short.MAX_VALUE)
					.addComponent(modifyButton)
					.addComponent(deleteButton));
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
					.addComponent(dataPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 500,
							GroupLayout.PREFERRED_SIZE)
					.addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
							// .addPreferredGap(jScrollCatPane, jBtnBckMain,
							// LayoutStyle.ComponentPlacement.INDENT)
							// .addComponent(jBtnBckMain,
							// GroupLayout.PREFERRED_SIZE, 106,
							// GroupLayout.PREFERRED_SIZE)
							.addGap(102)
							.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(modifyButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							//.addGap(10)
							.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							/*.addContainerGap(412, Short.MAX_VALUE)*/));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		// setLayout(new BorderLayout());
		// JLabel titleLabel = new JLabel();
		// titleLabel.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		// titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// titleLabel.setText("Categories");
		// Vector columnNames = new Vector();
		// columnNames.add("Name");
		// columnNames.add("");
		// columnNames.add("");
		// Vector data = new Vector();
		// for (Category cat : categories) {
		// Vector row = new Vector(3);
		// row.add(cat.getCategoryName());
		// row.add("Delete");
		// row.add("Edit");
		// data.add(row);
		// }
		//
		// JTable table = new JTable(data, columnNames) {
		// public Class getColumnClass(int column) {
		// for (int row = 0; row < getRowCount(); row++) {
		// Object o = getValueAt(row, column);
		//
		// if (o != null) {
		// return o.getClass();
		// }
		// }
		// return Object.class;
		// }
		// };
		// table.getColumn("Action1").setCellRenderer(new ButtonRenderer());
		// table.getColumn("Action1").setCellEditor(new ButtonEditor(new
		// JCheckBox(), table));
		// table.getColumn("Action2").setCellRenderer(new ButtonRenderer());
		// table.getColumn("Action2").setCellEditor(new ButtonEditor(new
		// JCheckBox(), table));
		//
		// JScrollPane scrollPane = new JScrollPane(table);
		//
		// JButton addButton = new JButton("Add");
		// addButton.addActionListener (new ActionListener () {
		// public void actionPerformed (ActionEvent e) {
		// AddCategoryDialog d = new AddCategoryDialog ();
		// d.pack();
		// d.setVisible (true);
		// }
		// });
		// addButton.setSize(10, 10);
		// add(titleLabel, BorderLayout.PAGE_START);
		// add(addButton, BorderLayout.LINE_END);
		// add(scrollPane);

	}

	protected abstract JScrollPane addList() ;
    protected abstract void performAddAction() ;
    protected abstract void performDeleteAction() ;
    protected abstract void performModifyAction() ;


}
