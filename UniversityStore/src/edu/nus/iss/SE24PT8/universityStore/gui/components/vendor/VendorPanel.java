package edu.nus.iss.SE24PT8.universityStore.gui.components.vendor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Group;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.AddCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.Item;

/**
*
* @author Mugunthan
*/
public class VendorPanel extends JPanel  implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane vendorPane;
	private JLabel titleLabel;
	private JTable vendorList;
	private Store manager = Store.getInstance();
	private Object[][] vendors;
	private DefaultTableModel dataModel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton modifyButton;
	private JComboBox<Item<String>> categories;
	
	private final static String[] columnNames = {"Vendor Name", "Category"};
	private final static String[] allowedOperations = {Constants.ADD_OPERATION};
	
	public VendorPanel() {
		super();
		SubjectManager.getInstance().addNotification("VendorPanel", "Vendor", this);
		initComponents();
	}
	
	protected void initComponents() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(780, 491));
			titleLabel = new JLabel();
			titleLabel.setText("Vendor List");
			titleLabel.setVerifyInputWhenFocusTarget(false);
			titleLabel.setForeground(Constants.STORE_APP_TITLE_COLOR);
			titleLabel.setFont(Constants.STORE_APP_TITLE_FONT);
			JLabel catLabel = new JLabel();
			catLabel.setText("Categories");
			categories = new JComboBox<Item<String>>();
			categories.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	Item cat = (Item)categories.getSelectedItem();
			        populateData(cat.getValue().toString());
			    }
			});
			vendorPane = new JScrollPane();
			populateCategories();
			if (Arrays.asList(allowedOperations).contains(Constants.ADD_OPERATION)) {
				addButton = new JButton();
				addButton.setText("Add Vendor");
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
			Group catGroup = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(catLabel,
					GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
					GroupLayout.PREFERRED_SIZE).addComponent(categories);
			Group group2 = thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
			if (Arrays.asList(allowedOperations).contains(Constants.MODIFY_OPERATION))
				group2.addComponent(modifyButton);
			if (Arrays.asList(allowedOperations).contains(Constants.DELETE_OPERATION))
				group2.addComponent(deleteButton).addGap(105, 105, 105);

			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup().addContainerGap().addGroup(group1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGap(20).addGroup(catGroup)
					.addComponent(vendorPane, 0, 440, Short.MAX_VALUE).addGap(20).addGroup(group2));

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
			Group hCatGroup = thisLayout.createSequentialGroup().addComponent(catLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(25).addComponent(categories, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE);
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
					.addComponent(vendorPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 500,
							GroupLayout.PREFERRED_SIZE).addGroup(GroupLayout.Alignment.CENTER, hCatGroup)
					.addGroup(GroupLayout.Alignment.LEADING, group3).addGroup(GroupLayout.Alignment.CENTER, group4));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void populateCategories() {
		ArrayList<Category> catList = manager.getMgrCategory().getCategories();
		for(Category cat : catList){
			Item<String> item = new Item<String>(cat.getCategoryCode(), cat.getCategoryName());
			categories.addItem(item);
		}
		
	}
	
	private void populateData(String catCode){
			Object[][] categories = manager.getMgrVendor().prepareListToTableModel(catCode);
			dataModel = new DefaultTableModel(categories, columnNames);
			dataModel.setDataVector(categories, columnNames);
			vendorPane.setViewportView(new BaseTable(dataModel));
	}
	
	public void refersh() {
    	Item cat = (Item)categories.getSelectedItem();
		vendors = manager.getMgrVendor().prepareListToTableModel(cat.getValue().toString());
		vendorPane.setVisible(false);
		dataModel.setDataVector(vendors, columnNames);
		vendorPane.setVisible(true);
	}

	protected void performAddAction() {
		AddVendorDialog d = new AddVendorDialog();
		d.pack();
		d.setVisible(true);
		refersh();
	}

	protected void performDeleteAction() {
		throw new UnsupportedOperationException("Not supported yet.");		
	}

	protected void performModifyAction() {
		throw new UnsupportedOperationException("Not supported yet.");		
	}
	
	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("VendorPanel") && topic.equals("Vendor")) {
			if (data.equalsIgnoreCase("Add")) {
				refersh();
			}
		}

		System.out.println("Group: " + group + "Topic: " + topic + " \n data: " + data);		
	}
}
