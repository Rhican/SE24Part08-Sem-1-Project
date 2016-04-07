package edu.nus.iss.SE24PT8.universityStore.gui.components.category;

import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.nus.iss.SE24PT8.universityStore.UniversityStore;
import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.Item;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class ModifyCategoryDialog extends BaseDialogBox {

	private static final long serialVersionUID = 1L;
	
    private JTextField nameField;
    private static Category cat;
	private Store manager;
    private JComboBox<Item<String>> vendor;
    private ArrayList<Vendor> vendorList;

    public ModifyCategoryDialog (Category cat) {
        super (MainWindow.getInstance(), "Modify Cetegory","modify");
        this.cat = cat;
        add ("Center", createFormPanel());
    }

    protected JPanel createFormPanel () {
    	manager = Store.getInstance();
    	vendorList= manager.getMgrVendor().getVendorsListByCategory(cat.getCategoryCode());
        JPanel p = new JPanel ();
        
        p.setLayout (new GridLayout (0, 2));
        vendor = new JComboBox<Item<String>>();
        p.add (new JLabel ("Code"));
        p.add(new Label(cat.getCategoryCode()));
        //codeField = new JTextField (3);
        //p.add (codeField);
        p.add (new JLabel ("Name"));
        nameField = new JTextField (cat.getCategoryName(), 20);
        p.add (nameField);
        if(vendorList != null && vendorList.size() > 0){
            populateVendorList(cat.getCategoryCode());
	        p.add (new JLabel ("Preference Vendor"));
	    	p.add(vendor);
        } else {
	        p.add (new JLabel ("Note-No vendors available for this category"));        	
        }
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    private void populateVendorList(String catCode) {
		for(Vendor v : vendorList){
			Item<String> item = new Item<String>(v.getVendorName(), v.getVendorName());
			vendor.addItem(item);
		}
		
	}

	protected boolean performCreateUpdateAction () {
        String name = nameField.getText();
        if ((name.length() == 0)) {
        	JOptionPane.showMessageDialog(new JFrame(),
					Constants.CONST_CAT_ERR_INVALID_DETAILS,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        cat.setCategoryName(name);
    	if (vendor.getSelectedItem() != null){
        	Item selectedVendor = (Item)vendor.getSelectedItem();
        	String selectedVendorName = selectedVendor.getValue().toString();
        	updateVendorPreference(cat, selectedVendorName);
    	}
        try {
			Category  newCat = Store.getInstance().getMgrCategory().updateCategory(cat);
        	JOptionPane.showMessageDialog(rootPane,
        			Constants.CONST_CAT_MSG_UPDATE_SUCUESS,
					"Success", JOptionPane.INFORMATION_MESSAGE);
        	SubjectManager.getInstance().Update("CategoryPanel", "Category", "Add");
        	return true;
		} catch (BadCategoryException e) {
        	JOptionPane.showMessageDialog(rootPane,
        			e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
		}
    }

	private void updateVendorPreference(Category cat, String selectedVendorName) {
		ArrayList<Vendor> vs = cat.getVendorList();
		Vendor toMove =  null;
		int i = 0;
		 for(Vendor v : vs){
		        if(v.getVendorName().equals(selectedVendorName)){
		        	i = vs.indexOf(v);
		        	toMove = v;
		        }
		           //something here
		    }
		if(i > 0) {
		    vs.set(i, vs.get(0));
		    vs.set(0, toMove);
		}
		cat.setVendorList(vs);
	}
}
