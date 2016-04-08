package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
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
import javax.swing.text.NumberFormatter;

import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

public class DiscountEntryPanel extends BaseDialogBox{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtDes;
	
	private ButtonGroup btnGrpApplyFor;
	private JRadioButton radioApplyMember;
	private JRadioButton radioApplyPulic;
	
	private JToggleButton chkDateIsAlways;
	private JCheckBox chkDatePick;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
    JFormattedTextField txtStartDate;
	
	private JToggleButton chkPeroidAlways;
	private JFormattedTextField txtPeriod;
	private JFormattedTextField txtPercent;
	
	private JLabel lblDateFormat;
	
	Discount d;
	
	
	public DiscountEntryPanel () {
        super (MainWindow.getInstance(), "Add Discount","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
    }

    protected JPanel createFormPanel  ()  {
    	NumberFormat intFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(intFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
    	
    	
    	txtID = new JTextField();
    	txtDes =new JTextField();
    	btnGrpApplyFor = new ButtonGroup();
    	radioApplyMember = new JRadioButton("Member Only");
    	radioApplyPulic = new JRadioButton("Public");
    	chkDateIsAlways = new JCheckBox("Always");
    	chkDatePick = new JCheckBox();
    	txtStartDate = new JFormattedTextField(dateFormat);
    	txtPercent = new JFormattedTextField(formatter);
    	chkPeroidAlways = new JCheckBox("Always");
    	txtPeriod = new JFormattedTextField(formatter);
    	lblDateFormat =new JLabel("YYYY-MM-DD");
    	
    	
    	JPanel panel = new JPanel();
        panel.setSize(300,300);
        GridLayout layout = new GridLayout(0, 2);

        panel.setLayout(layout); 
        panel.add(new JLabel("Discount Code"));
        panel.add(txtID);
         
        panel.add(new JLabel("Description"));
        panel.add(txtDes);
        
        panel.add(new JLabel("Discount Amount"));
        JPanel discountPercent = new JPanel();
        discountPercent.setLayout(new GridLayout(1, 4));
        discountPercent.setSize(new Dimension(25, 8));
        discountPercent.add(txtPercent);
        discountPercent.add(new JLabel("%"));
        panel.add(discountPercent);
        
        
        panel.add(new JLabel("Apply For"));
        
        btnGrpApplyFor.add(radioApplyMember);
        btnGrpApplyFor.add(radioApplyPulic);
      
        
        JPanel compApplyFor = new JPanel();
        compApplyFor.setLayout(new GridLayout(1, 4));
        compApplyFor.setSize(new Dimension(25, 8));
        compApplyFor.add(radioApplyMember);
        compApplyFor.add(radioApplyPulic);
        panel.add(compApplyFor);
        
        
        panel.add(new JLabel("Start Date"));
        chkDatePick.setVisible(false);
        
        JPanel compStartDate = new JPanel();
        compStartDate.setSize(new Dimension(25, 8));
        compStartDate.setLayout(new GridLayout(1, 4));
        compStartDate.add(chkDateIsAlways);
        compStartDate.add(txtStartDate);
        compStartDate.add(lblDateFormat);
       
        
        panel.add(compStartDate);
        
        panel.add(new JLabel("Period"));
        
        
        JPanel compPeriod = new JPanel();
        compPeriod.setSize(new Dimension(25, 8));
        compPeriod.setLayout(new GridLayout(1, 4));
        compPeriod.add(chkPeroidAlways);
        chkDatePick.setVisible(false);
        compPeriod.add(txtPeriod);
        compPeriod.add(new JLabel ("Days"));
        
        panel.add(compPeriod);
        
		chkDateIsAlways.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkDateIsAlways.isSelected()) {
					txtStartDate.setEditable(false);
					txtStartDate.setEnabled(false);
					chkPeroidAlways.setSelected(true);
					chkPeroidAlways.setEnabled(false);
					txtPeriod.setEditable(false);
					txtPeriod.setEnabled(false);
				} else {
					txtStartDate.setEditable(true);
					txtStartDate.setEnabled(true);
					chkPeroidAlways.setSelected(true);
					chkPeroidAlways.setEnabled(true);

				}

			}
		});
        
        
        chkPeroidAlways.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkPeroidAlways.isSelected()){
					txtPeriod.setEditable(false);
					txtPeriod.setEnabled(false);
				}else{
					txtPeriod.setEditable(true);
					txtPeriod.setEnabled(true);
				}
				
			}
		});
        
        return panel;
    }

    protected boolean performCreateUpdateAction ()  {
        String code = txtID.getText();
        String des = txtDes.getText();
        Date startDate = null;
        String applicableFor ="A";
        int percent;
        int period = 0 ;
        
        if ( code.equals("")){
        	JOptionPane.showMessageDialog(rootPane,
					"Please Enter discount Code",
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        if ( txtPercent.getValue() == null || txtPercent.getValue().toString().equals("")){
        	JOptionPane.showMessageDialog(rootPane,
					"Please Enter discount amount in Percentage",
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        	
        }else{
        	percent = Integer.parseInt(txtPercent.getValue().toString());
        }
       
        
        if ( radioApplyMember.isSelected()){
        	applicableFor = Constants.CONST_CUST_TYPE_MEMBER;
        }else if ( radioApplyPulic.isSelected()){
        	applicableFor = Constants.CONST_CUST_TYPE_PUBLIC;
        }else{
        	JOptionPane.showMessageDialog(rootPane,
					"Please Choose Discount Type",
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        try {
        	 if (!chkDateIsAlways.isSelected() && (!isValidDate(txtStartDate.getText(), dateFormat))){
				JOptionPane.showMessageDialog(rootPane,
						"Please Enter the Correct Date Format",
						"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}else if (!chkDateIsAlways.isSelected() &&  (isValidDate(txtStartDate.getText(), dateFormat))){
				startDate = dateFormat.parse(txtStartDate.getText());
				
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(rootPane,
					"Please Enter the Correct Date Format",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch (NullPointerException e){
			JOptionPane.showMessageDialog(rootPane,
					"Please Enter the Correct Date Format",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
        
      
        
		if ((!chkPeroidAlways.isSelected() && txtPeriod.getValue() == null)
				
				|| (!chkPeroidAlways.isSelected() && (txtPeriod.getValue().toString().equals("")
						|| (Integer.parseInt(txtPeriod.getValue().toString()) < 0)))) {
			JOptionPane.showMessageDialog(rootPane, "Please Enter the periods of discount in Days", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!chkPeroidAlways.isSelected() && (Integer.parseInt(txtPeriod.getValue().toString()) >= 0)) {
			period = Integer.parseInt(txtPeriod.getValue().toString());

		}
        
		try {
			Store.getInstance().getMgrDiscount().addNewiDscount(code, des, percent, startDate, period,
					chkDateIsAlways.isSelected(), chkPeroidAlways.isSelected(), applicableFor);
			SubjectManager.getInstance().Update("DiscountPanel", "Discount", "Add");
		} catch (BadDiscountException e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
      
    return true;
}

	public boolean isValidDate(String date, DateFormat format) {
		try {
			format.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
    
}
