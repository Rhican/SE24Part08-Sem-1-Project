package edu.nus.iss.SE24PT8.universityStore.gui.components;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
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
public class MemberPanel extends BaseModulePanel implements INotificable {

	private static final long serialVersionUID = 1L;
	private JScrollPane memberPane;
	private JTable memberList;
	private Store manager=Store.getInstance();
	private Object[][] members;
	private DefaultTableModel dataModel;
	
	private final static String[] columnNames={"MemberName","MemberID","LoyaltyPoint"};
	private final static String[] allowedOperations = {Constants.ADD_OPERATION, Constants.MODIFY_OPERATION, Constants.DELETE_OPERATION};

	public MemberPanel(){
		super("Member", allowedOperations);
		SubjectManager.getInstance().addNotification("MemberPanel", "Member", this);
		SubjectManager.getInstance().addNotification("CheckOutPanel", "CheckOut", this);
		refresh();
	}
    
	public void refresh(){
		members=manager.getMgrMember().prepareMemberTableModel();
		memberList.setVisible(false);
		dataModel.setDataVector(members, columnNames);
		memberList.setVisible(true);
	}
	
	protected JScrollPane addList(){
		manager=Store.getInstance();
		members=manager.getMgrMember().prepareMemberTableModel();
		memberPane=new JScrollPane();
		{
			dataModel=new DefaultTableModel(members,columnNames);
			dataModel.setDataVector(members, columnNames);
			memberList=new BaseTable(dataModel);
			memberPane.setViewportView(memberList);
		}
		return memberPane;
	}
	protected void performAddAction(){
		MemberEntryDialog m=new MemberEntryDialog();
		m.pack();
		m.setVisible(true);
		refresh();
	}
	
	protected void performModifyAction(){
		if(memberList.getSelectedRow() != -1){
			String memberID=memberList.getValueAt(memberList.getSelectedRow(), 1).toString();
			Member member=manager.getMgrMember().getMember(memberID);
			MemberUpdateDialog memberDialog=new MemberUpdateDialog(member);
			memberDialog.pack();
			memberDialog.setVisible(true);
		}
		refresh();
	}
	
	protected void performDeleteAction(){
		
		if(memberList.getSelectedRow()!= -1){
			String memberID=memberList.getValueAt(memberList.getSelectedRow(), 1).toString();
			 try {
	        	 Store.getInstance().getMgrMember().removeMember(memberID);
	        	 JOptionPane.showMessageDialog(getRootPane(),
	         			"Member Registration Cancelled successfully",
	 					"Success", JOptionPane.INFORMATION_MESSAGE);
	        	 refresh();
	         } catch (BadMemberRegistrationException ex) {
	        	 JOptionPane.showMessageDialog(getRootPane(),
	        			 ex.getMessage().toString(),
	 					"Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
	}
	
	@Override 
	public void update(String group,String topic,String data){
		if(group.equals("MemberPanel") && topic.equals("Member")){
			if(data.equals("Add")){
				refresh();
			}
		}
		if(group.equals("CheckOutPanel") && topic.equals("CheckOut")){
			if(data.equals("Complete")){
				refresh();
			}
		}
	}
}

