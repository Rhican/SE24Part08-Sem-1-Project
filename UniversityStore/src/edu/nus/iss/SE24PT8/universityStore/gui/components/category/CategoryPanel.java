package edu.nus.iss.SE24PT8.universityStore.gui.components.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

/**
 *
 * @author Mugunthan
 */
public class CategoryPanel extends BaseModulePanel implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane catPane;
	private JTable catList;
	private Store manager = Store.getInstance();
	private Object[][] categories;
	private DefaultTableModel dataModel;

	private final static String[] columnNames = { "Category Code", "Category Name" };
	private final static String[] allowedOperations = {Constants.ADD_OPERATION, Constants.MODIFY_OPERATION, Constants.DELETE_OPERATION};

	public CategoryPanel() {
		super("Category", allowedOperations);
		SubjectManager.getInstance().addNotification("CategoryPanel", "Category", this);
	}

	public void refersh() {
		categories = manager.getMgrCategory().prepareCategoryTableModel();
		catList.setVisible(false);
		dataModel.setDataVector(categories, columnNames);
		catList.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager = Store.getInstance();
		categories = manager.getMgrCategory().prepareCategoryTableModel();
		catPane = new JScrollPane();
		{	
			dataModel = new DefaultTableModel(categories, columnNames);
			dataModel.setDataVector(categories, columnNames);
			catList = new BaseTable(dataModel);
			catPane.setViewportView(catList);
		}
		return catPane;
	}
	
	protected void performAddAction (){
		AddCategoryDialog d = new AddCategoryDialog();
		d.pack();
		d.setVisible(true);
		refersh();
	}
	
	protected void performModifyAction (){
		if (catList.getSelectedRow() != -1) {
            String code = catList.getValueAt(catList.getSelectedRow(), 0).toString();
            Category cat = manager.getMgrCategory().getCategory(code);
    		ModifyCategoryDialog d = new ModifyCategoryDialog(cat);
    		d.pack();
    		d.setVisible(true);
        }
		refersh();
	}
	
	protected void performDeleteAction (){
		if (catList.getSelectedRow() != -1) {
            String code = catList.getValueAt(catList.getSelectedRow(), 0).toString();
            ReturnObject  returnObject = manager.getMgrCategory().deleteCategory(code);
            if (returnObject.isSuccess()) {
            	JOptionPane.showMessageDialog(new JFrame(),
            			returnObject.getMessage(),
    					"Success", JOptionPane.INFORMATION_MESSAGE);
        	refersh();
            } else {
            	JOptionPane.showMessageDialog(new JFrame(),
            			returnObject.getMessage(),
    					"Error", JOptionPane.ERROR_MESSAGE);
            }
        }
	}

	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("CategoryPanel") && topic.equals("Category")) {
			if (data.equalsIgnoreCase("Add")) {
				refersh();
			}
		}

		System.out.println("Group: " + group + "Topic: " + topic + " \n data: " + data);

	}
}
