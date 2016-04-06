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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.text.NumberFormatter;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.domain.MemberDiscount;
import edu.nus.iss.SE24PT8.universityStore.domain.OtherDiscount;
import edu.nus.iss.SE24PT8.universityStore.domain.Product;
import edu.nus.iss.SE24PT8.universityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.manager.DiscountManager;
import edu.nus.iss.SE24PT8.universityStore.manager.ProductManager;
import edu.nus.iss.SE24PT8.universityStore.util.ComboItem;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class DiscountEditDialog extends BaseDialogBox{
	
	private JTextField txtID;
	private JTextField txtDes;
	
	private ButtonGroup btnGrpApplyFor;
	private JRadioButton radioApplyMember;
	private JRadioButton radioApplyPulic;
	
	//private ButtonGroup bntGrpStratDate;
	private JToggleButton chkDateIsAlways;
	private JCheckBox chkDatePick;
	//private JTextField txtStartDate;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
    JFormattedTextField txtStartDate;
	
	//private ButtonGroup btnGrpPeriod;
	private JToggleButton chkPeroidAlways;
	private JFormattedTextField txtPeriod;
	private JFormattedTextField txtPercent;
	
	private JLabel lblDateFormat;
	Discount discount;
	
	public DiscountEditDialog (Discount updateDiscount) {
        super (MainWindow.getInstance(), "Edit Discount","Modify");
        super.setModalityType(Dialog.ModalityType.MODELESS);
        this.discount = updateDiscount;
        add ("Center" , createFormPanel());
        updateJPanel();
    }
	
	private void updateJPanel(){
		if (discount != null) {
			if (discount instanceof MemberDiscount) {
				radioApplyMember.setSelected(true);
			} else if (discount instanceof OtherDiscount) {
				radioApplyPulic.setSelected(true);
			}
        
        
        txtID.setText(discount.getDiscountCode());
        txtID.setEditable(false);
        txtDes.setText(discount.getDiscountDes());
        txtPercent.setValue(discount.getDiscountPercent());
        if(discount.isIsStartDateAlways()){
        	chkDateIsAlways.setSelected(true);
        }else{
        	txtStartDate.setText( dateFormat.format( discount.getDiscountStartDate()));
        }
        if (discount.isIsPeriodAlways()){
        	chkPeroidAlways.setSelected(true);
        }else {
        	txtPeriod.setValue(discount.getDiscountPeriod());
        }
        txtPercent.setValue(discount.getDiscountPercent());
        
		}
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
       // GridBagLayout layout = new GridBagLayout();
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
        String startDateStr;
        if ( txtStartDate.getValue() != null){
        	   startDateStr = txtStartDate.getValue().toString();
        }else {
        	   startDateStr ="";
        }
       
        Date startDate = null;
        
        int percent;
        
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
       
    
        boolean isStartDateAlways = chkDateIsAlways.isSelected();
        boolean isPeriodAlways = chkPeroidAlways.isSelected();
        String applicableFor ="A";
        
        
        
        if ( radioApplyMember.isSelected()){
        	applicableFor = "M";
        }else if ( radioApplyPulic.isSelected()){
        	applicableFor = "A";
        }else{
        	JOptionPane.showMessageDialog(rootPane,
					"Please Choose Discount Type",
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
		try {
			if (!isStartDateAlways && (!isValidDate(txtStartDate.getValue().toString(), dateFormat))) {
				JOptionPane.showMessageDialog(rootPane, "Please Enter the Correct Date Format", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (!isStartDateAlways && !startDateStr.equals("")
					&& (dateFormat.parse(startDateStr) instanceof Date)) {
				startDate = dateFormat.parse(startDateStr);

			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       int period = 0 ;
        
        if ((!isPeriodAlways  &&  txtPeriod.getValue() == null )  || (!isPeriodAlways && ( txtPeriod.getValue().toString().equals("") || (Integer.parseInt(txtPeriod.getValue().toString())  <0))) ){
    		JOptionPane.showMessageDialog(rootPane,
					"Please Enter the periods of discount in Days",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
    	}
    	else if (!isPeriodAlways && (Integer.parseInt(txtPeriod.getValue().toString()) >=0)){
			period = Integer.parseInt(txtPeriod.getValue().toString());
        
    	}
        
         try {
			ReturnObject returnObj = Store.getInstance().getMgrDiscount().upDateDiscount(code, des, percent, startDate, period, isStartDateAlways, isPeriodAlways, applicableFor);
			if (returnObj.isSuccess()){
				SubjectManager.getInstance().Update("DiscountPanel", "Discount", "Add");
				return true;
			}else{
				JOptionPane.showMessageDialog(rootPane,
						returnObj.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (BadDiscountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
