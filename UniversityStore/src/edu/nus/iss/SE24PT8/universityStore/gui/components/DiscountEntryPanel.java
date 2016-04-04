package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.Login;
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
	
	//private ButtonGroup bntGrpStratDate;
	private JToggleButton chkDateIsAlways;
	private JCheckBox chkDatePick;
	//private JTextField txtStartDate;
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    JFormattedTextField txtStartDate;
	
	//private ButtonGroup btnGrpPeriod;
	private JToggleButton chkPeroidAlways;
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
    	chkDateIsAlways = new JCheckBox("Always");
    	chkDatePick = new JCheckBox();
    	//txtStartDate = new JTextField();
    	txtStartDate = new JFormattedTextField(dateFormat);
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
        
        panel.add(new JLabel("Apply For"));
        
        btnGrpApplyFor.add(radioApplyMember);
        btnGrpApplyFor.add(radioApplyPulic);
      
        
        JPanel compApplyFor = new JPanel();
        compApplyFor.setLayout(new GridLayout(1, 2));
        compApplyFor.setSize(new Dimension(25, 8));
        compApplyFor.add(radioApplyMember);
        compApplyFor.add(radioApplyPulic);
        panel.add(compApplyFor);
        
        
        panel.add(new JLabel("Start Date"));
        chkDatePick.setVisible(false);
        
        JPanel compStartDate = new JPanel();
        compStartDate.setSize(new Dimension(25, 8));
        compStartDate.setLayout(new GridLayout(1, 3));
        compStartDate.add(chkDateIsAlways);
        compStartDate.add( chkDatePick);
        compStartDate.add(txtStartDate);
        compStartDate.add(lblDateFormat);
       
        
        panel.add(compStartDate);
        
        panel.add(new JLabel("Period"));
        
        
        JPanel compPeriod = new JPanel();
        compPeriod.setSize(new Dimension(25, 8));
        compPeriod.setLayout(new GridLayout(1, 3));
        compPeriod.add(chkPeroidAlways);
       // compPeriod.add(chkPeriodInput);
        chkDatePick.setVisible(false);
        compPeriod.add(txtPeriod);
        compPeriod.add(new JLabel ("Days"));
        
        panel.add(compPeriod);
        
        
        chkDateIsAlways.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkDateIsAlways.isSelected()){
					txtStartDate.setEditable(false);
				}else{
					txtStartDate.setEditable(true);
				}
				
			}
		});
        
        chkPeroidAlways.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkPeroidAlways.isSelected()){
					txtPeriod.setEditable(false);
				}else{
					txtPeriod.setEditable(true);
				}
				
			}
		});
        
        return panel;
    }

    protected boolean performCreateUpdateAction ()  {
        String code = txtID.getText();
        String des = txtDes.getText();
        Date startDate = null;
        int percent  = 10;
        int period = 10;
        boolean isStartDateAlways = chkDateIsAlways.isSelected();
        boolean isPeriodAlways = chkPeroidAlways.isSelected();
        String applicableFor ;
        if ( radioApplyMember.isSelected()){
        	applicableFor = "M";
        }else {
        	applicableFor = "A";
        }
        
        try {
			if (!isStartDateAlways && txtStartDate.getText() != "" && !(dateFormat.parse(txtStartDate.getText()) instanceof Date)  ){
				JOptionPane.showMessageDialog(rootPane,
						"Please Enter the Correct Date Format",
						"Error", JOptionPane.ERROR_MESSAGE);
			}else if (!isStartDateAlways &&  txtStartDate.getText() != "" && dateFormat.parse(txtStartDate.getText()) instanceof Date){
				startDate = dateFormat.parse(txtStartDate.getText()) ;
				
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         try {
			Store.getInstance().getMgrDiscount().addNewiDscount(code, des, percent, startDate, period, isStartDateAlways, isPeriodAlways, applicableFor);
			
		} catch (BadDiscountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    return true;
}
}
