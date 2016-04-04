package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class DiscountEntryPanel extends BaseDialogBox{
	
	private JTextField txtID;
	private JTextField txtDes;
	
	private ButtonGroup btnGrpApplyFor;
	private JRadioButton radioApplyMember;
	private JRadioButton radioApplyPulic;
	
	private ButtonGroup bntGrpStratDate;
	private JCheckBox chkDateIsAlways;
	private JCheckBox chkDatePick;
	private JTextField txtStartDate;
	
	private ButtonGroup btnGrpPeriod;
	private JCheckBox chkPeroidAlways;
	private JCheckBox chkPeriodInput;
	private JTextField txtPeriod;
	
	private JLabel lblDateFormat;
	
	Discount d;
	
	
	public DiscountEntryPanel () {
        super (MainWindow.getInstance(), "Add Cetegory","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
    }

    protected JPanel createFormPanel  ()  {
    	txtID = new JTextField();
    	txtDes =new JTextField();
    	btnGrpApplyFor = new ButtonGroup();
    	radioApplyMember = new JRadioButton("Member Only");
    	radioApplyPulic = new JRadioButton("Public");
    	bntGrpStratDate = new ButtonGroup();
    	chkDateIsAlways = new JCheckBox("Always");
    	chkDatePick = new JCheckBox();
    	txtStartDate = new JTextField();
    	btnGrpPeriod = new ButtonGroup();
    	chkPeroidAlways = new JCheckBox("Always");
    	txtPeriod = new JTextField();
    	chkPeriodInput = new JCheckBox();
    	lblDateFormat =new JLabel("DD/MM/YYY");
    	
    	
    	JPanel panel = new JPanel();
        panel.setSize(300,300);
       // GridBagLayout layout = new GridBagLayout();
        GridLayout layout = new GridLayout(0, 2);

        panel.setLayout(layout); 
        panel.add(new JLabel("Discount Code"));
        panel.add(txtID);
         
        panel.add(new JLabel("Description"));
        panel.add(txtDes);
        
        btnGrpApplyFor.add(radioApplyMember);
        btnGrpApplyFor.add(radioApplyPulic);
      
        
        JPanel compApplyFor = new JPanel();
        compApplyFor.add(radioApplyMember);
        compApplyFor.add(radioApplyPulic);
        panel.add(compApplyFor);
        
        bntGrpStratDate = new ButtonGroup();
        bntGrpStratDate.add(chkDateIsAlways);
        btnGrpPeriod.add(chkDatePick);
        
        chkDatePick.setVisible(false);
        
        
        
        JPanel compStartDate = new JPanel();
        compApplyFor.add(chkDateIsAlways);
        compApplyFor.add(chkDatePick);
        compApplyFor.add(txtStartDate);
        compApplyFor.add(lblDateFormat);
        panel.add(compApplyFor);
        
        panel.add(compStartDate);
        
        btnGrpPeriod.add(chkDateIsAlways);
        btnGrpPeriod.add(chkDatePick);
        
        JPanel compPeriod = new JPanel();
        compPeriod.add(chkPeroidAlways);
        compPeriod.add(chkDatePick);
        chkDatePick.setVisible(false);
        compPeriod.add(txtPeriod);
        panel.add(compPeriod);
        
        panel.add(compPeriod);
       
         
        
        
        

        
        return panel;
    }

    protected boolean performCreateUpdateAction () {/*
        String name = nameField.getText();
        String code = codeField.getText();
        if ((name.length() == 0) || (code.length() == 0)) {
        	JOptionPane.showMessageDialog(rootPane,
					Constants.CONST_CAT_ERR_INVALID_DETAILS,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (code.length() != 3) {
        	JOptionPane.showMessageDialog(rootPane,
					Constants.CONST_CAT_ERR_LONG_CODE,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        ReturnObject  returnObject = Store.getInstance().getMgrCategory().addCategory(code, name);
        if (returnObject.isSuccess()) {
        	JOptionPane.showMessageDialog(rootPane,
        			returnObject.getMessage(),
					"Success", JOptionPane.INFORMATION_MESSAGE);
        	SubjectManager.getInstance().Update("CategoryPanel", "Category", "Add");
        	return true;
        } else {
        	JOptionPane.showMessageDialog(rootPane,
        			returnObject.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    */
    return false;
}
}
