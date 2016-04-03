package edu.nus.iss.SE24PT8.universityStore.gui.components.vendor;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.*;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.common.EntryJPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.ComboItem;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.Item;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
/**
*
* @author Mugunthan
*/
public class AddVendorDialog extends BaseDialogBox {

	private static final long serialVersionUID = 1L;
	
    private JTextField nameField;
    private JTextArea descField;
	private Store manager;
    private JComboBox<Item<String>> categoryField;

    public AddVendorDialog () {
        super (MainWindow.getInstance(), "Add Vedndor","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
    }

    protected JPanel createFormPanel  ()  {
    	manager = Store.getInstance();
    	JPanel p = new JPanel ();
    	categoryField = new JComboBox<Item<String>>();
        p.setLayout (new GridLayout (0, 2));

        p.add(new JLabel ("Category"));
    	p.add(categoryField);
        p.add(new JLabel ("Vendor Name"));
        nameField = new JTextField (20);
        p.add (nameField);
        p.add (new JLabel ("Description"));
        descField = new JTextArea();
        p.add (descField);
        

    	ArrayList<Category> categories= manager.getMgrCategory().getCategories();
    	for(Category cat : categories){
    		Item<String> item = new Item<String>(cat.getCategoryCode(), cat.getCategoryName());
    		categoryField.addItem(item);
    	}
        
        return p;
    }

    protected boolean performCreateUpdateAction () {
    	Item cat = (Item)categoryField.getSelectedItem();
    	String catCode = cat.getValue().toString();
        String name = nameField.getText();
        String desc = descField.getText();
        System.out.println(catCode);
        if (name.length() == 0) {
        	JOptionPane.showMessageDialog(rootPane,
					Constants.CONST_VENDOR_ERR_NAMEMISSING,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        ReturnObject returnObject = Store.getInstance().getMgrVendor().addVendor(catCode, name, desc);
        if (returnObject.isSuccess()) {
        	JOptionPane.showMessageDialog(rootPane,
        			returnObject.getMessage(),
					"Success", JOptionPane.INFORMATION_MESSAGE);
        	SubjectManager.getInstance().Update("VendorPanel", "Vendor", "Add");
        	return true;
        } else {
        	JOptionPane.showMessageDialog(rootPane,
        			returnObject.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    }
}