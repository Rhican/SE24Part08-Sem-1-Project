package edu.nus.iss.SE24PT8.universityStore.gui.components.vendor;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.AddCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
*
* @author Mugunthan
*/
public class VendorPanel extends BaseModulePanel implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane vendorPane;
	private JTable vendorList;
	private Store manager = Store.getInstance();
	private Object[][] vendors;
	private DefaultTableModel dataModel;
	
	private final static String[] columnNames = {"Vendor Name", "Category"};
	private final static String[] allowedOperations = {Constants.ADD_OPERATION};
	
	public VendorPanel() {
		super("Vendor", allowedOperations);
		SubjectManager.getInstance().addNotification("VendorPanel", "Vendor", this);
	}
	
	public void refersh() {
		vendors = manager.getMgrVendor().prepareListToTableModel();
		vendorList.setVisible(false);
		dataModel.setDataVector(vendors, columnNames);
		vendorList.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager = Store.getInstance();
		vendors = manager.getMgrVendor().prepareListToTableModel();
		vendorPane = new JScrollPane();
		{	
			dataModel = new DefaultTableModel(vendors, columnNames);
			dataModel.setDataVector(vendors, columnNames);
			vendorList = new BaseTable(dataModel);
			vendorPane.setViewportView(vendorList);
		}
		return vendorPane;
	}


	@Override
	protected void performAddAction() {
		AddVendorDialog d = new AddVendorDialog();
		d.pack();
		d.setVisible(true);
		refersh();
	}

	@Override
	protected void performDeleteAction() {
		throw new UnsupportedOperationException("Not supported yet.");		
	}

	@Override
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
