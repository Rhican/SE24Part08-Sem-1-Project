package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Discount;
import edu.nus.iss.SE24PT8.universityStore.exception.BadDiscountException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.AddCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.ModifyCategoryDialog;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;


public class DiscountPanel extends BaseModulePanel implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane discountPane;
	private JTable discountList;
	private Store manager = Store.getInstance();
	private Object[][] discounts;
	private DefaultTableModel dataModel;

	private final static String[] columnNames = { "Name","Code","Amount" ,"Type","From Date" , "During"};
	private final static String[] allowedOperations = {Constants.ADD_OPERATION, Constants.MODIFY_OPERATION, Constants.DELETE_OPERATION};

	public DiscountPanel() {
		super("Discount", allowedOperations);
		SubjectManager.getInstance().addNotification("DiscountPanel", "Discount", this);
	}

	public void refresh() {
		discounts = manager.getMgrDiscount().prepareDiscountTableModel();
		discountList.setVisible(false);
		dataModel.setDataVector(discounts, columnNames);
		discountList.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager = Store.getInstance();
		discounts =  manager.getMgrDiscount().prepareDiscountTableModel();
		discountPane = new JScrollPane();
		{	
			dataModel = new DefaultTableModel(discounts, columnNames);
			dataModel.setDataVector(discounts, columnNames);
			discountList = new BaseTable(dataModel);
			discountPane.setViewportView(discountList);
		}
		return discountPane;
	}
	
	protected void performAddAction (){
		DiscountEntryPanel d = new DiscountEntryPanel();
		d.pack();
		d.setVisible(true);
		refresh();
	}
	
	protected void performModifyAction (){
		if (discountList.getSelectedRow() != -1) {
            String code = discountList.getValueAt(discountList.getSelectedRow(), 0).toString();
            Discount discount = manager.getMgrDiscount().getDiscountByCode(code);
    		DiscountEditDialog d = new DiscountEditDialog(discount);
    		d.pack();
    		d.setVisible(true);
        }
		refresh();
	}
	
	protected void performDeleteAction (){
		String code;
		if (discountList.getSelectedRow() != -1) {
             code = discountList.getValueAt( discountList.getSelectedRow(),0).toString();
            try{
            	manager.getMgrDiscount().deleteDiscount(code);
            }catch (BadDiscountException e){
            	
            }
            refresh();
        }
	}

	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("DiscountPanel") && topic.equals("Discount")) {
			if (data.equalsIgnoreCase("Add")) {
				refresh();
			}
		}

		System.out.println("Group: " + group + "Topic: " + topic + " \n data: " + data);

	}

}
