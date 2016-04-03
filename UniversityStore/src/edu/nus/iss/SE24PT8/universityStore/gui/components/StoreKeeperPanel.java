package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.nus.iss.SE24PT8.universityStore.exception.BadStoreKeeperAdminException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseModulePanel;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseTable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;

/**
*
* @author THIRI LWIN
* @date 2016-April-02
*/
public class StoreKeeperPanel extends BaseModulePanel implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane storeKeeperPane;
	private JTable storeKeeperList;
	private Store manager=Store.getInstance();
	private Object[][] storeKeepers;
	private DefaultTableModel dataModel;
	
	private final static String[] columnNames={"StoreKeeperName","Password"};
	private final static String[] allowedOperations = {Constants.ADD_OPERATION, Constants.MODIFY_OPERATION, Constants.DELETE_OPERATION};

	public StoreKeeperPanel(){
		super("StoreKeeper", allowedOperations);
		SubjectManager.getInstance().addNotification("StoreKeeperPanel", "StoreKeeper", this);
	}
    
	public void refresh(){
		storeKeepers=manager.getMgrStoreKeeper().prepareMemberTableModel();
		storeKeeperList.setVisible(false);
		dataModel.setDataVector(storeKeepers, columnNames);
		storeKeeperList.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager=Store.getInstance();
		storeKeepers=manager.getMgrStoreKeeper().prepareMemberTableModel();
		storeKeeperPane=new JScrollPane();
		{
			dataModel=new DefaultTableModel(storeKeepers,columnNames);
			dataModel.setDataVector(storeKeepers, columnNames);
			storeKeeperList=new BaseTable(dataModel);
			storeKeeperPane.setViewportView(storeKeeperList);
		}
		return storeKeeperPane;
	}
	protected void performAddAction(){
		StoreKeeperEntryDialog s=new StoreKeeperEntryDialog();
		s.pack();
		s.setVisible(true);
		refresh();
	}
	
	protected void performModifyAction(){
		JOptionPane.showMessageDialog(getRootPane(),
      			"StorekeeperName do not allow to modify! Please delete and create new record.",
					"Error", JOptionPane.INFORMATION_MESSAGE);
     	 refresh();
	}
	
	protected void performDeleteAction(){
		
		if(storeKeeperList.getSelectedRow()!= -1){
			String storeKeeperName=storeKeeperList.getValueAt(storeKeeperList.getSelectedRow(), 1).toString();
			 try {
	        	 Store.getInstance().getMgrStoreKeeper().DeleteStoreKeeper(storeKeeperName);
	        	 JOptionPane.showMessageDialog(getRootPane(),
	         			"StoreKeeper record deleted successfully",
	 					"Success", JOptionPane.INFORMATION_MESSAGE);
	        	 refresh();
	         } catch (BadStoreKeeperAdminException ex) {
	        	 JOptionPane.showMessageDialog(getRootPane(),
	        			 ex.getMessage().toString(),
	 					"Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
	}
	
	@Override 
	public void update(String group,String topic,String data){
		if(group.equals("StoreKeeperPanel") && topic.equals("StoreKeeper")){
			if(data.equals("Add")){
				refresh();
			}
		}
	}
}

