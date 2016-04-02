package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Dialog;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nus.iss.SE24PT8.universityStore.exception.BadStoreKeeperAdminException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;

public class StoreKeeperEntryDialog extends BaseDialogBox{
	
	private static final long serialVersionUID = 1L;
	private JTextField storeKeeperNameField;
	private JTextField storeKeeperPasswordField;
	
	public StoreKeeperEntryDialog(){
		super(MainWindow.getInstance(),"Add NewStoreKeeper","add");
		super.setModalityType(Dialog.ModalityType.MODELESS);
	}
	
	protected JPanel createFormPanel(){
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new JLabel("StoreKeeperName"));
		storeKeeperNameField=new JTextField(20);
		p.add(storeKeeperNameField);
		p.add(new JLabel("StoreKeeperPassword"));
		storeKeeperPasswordField=new JTextField(3);
		p.add(storeKeeperPasswordField);
		return p;
	}
   
	protected boolean performCreateUpdateAction() {
		String storeKeeperName=storeKeeperNameField.getText();
		String storeKeeperPassword=storeKeeperPasswordField.getText();
		try {
			 Store.getInstance().getMgrStoreKeeper().AddStoreKeeper(storeKeeperName, storeKeeperPassword);
			 JOptionPane.showMessageDialog(rootPane,
	        			"New StoreKeeper has been registered successfully",
						"Success", JOptionPane.INFORMATION_MESSAGE);
	        	SubjectManager.getInstance().Update("StoreKeeperPanel", "StoreKeeper", "Add");
	        	return true;
        } catch (BadStoreKeeperAdminException ex) {
        	JOptionPane.showMessageDialog(rootPane,
        			ex.getMessage().toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
      }
	}
}
