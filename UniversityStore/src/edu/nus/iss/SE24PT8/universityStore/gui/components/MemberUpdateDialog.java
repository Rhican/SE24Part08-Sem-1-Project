package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;

/**
*
* @author THIRI LWIN
* @date 2016-April-02
*/
public class MemberUpdateDialog extends BaseDialogBox{

	private static final long serialVersionUID = 1L;
	private JTextField membernameField;
	private Member member;
	
	public MemberUpdateDialog(Member member){
		super(MainWindow.getInstance(),"Update MemberName","modify");
		this.member=member;
		add("Center",createFormPanel());
	}

	protected JPanel createFormPanel(){
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new JLabel("MemberID"));
		p.add(new JLabel(member.getCustomerID()));
		p.add(new JLabel("MemberName"));
		membernameField=new JTextField(20);
		p.add(membernameField);
		return p;
	}
   
	protected boolean performCreateUpdateAction(){
		String memberName=membernameField.getText();
		String memberID=member.getCustomerID();
        try {
        	 Store.getInstance().getMgrMember().editMemberName(memberName,memberID);
        	 JOptionPane.showMessageDialog(new JFrame(),
         			"Member Name updated successfully",
 					"Success", JOptionPane.INFORMATION_MESSAGE);
         	SubjectManager.getInstance().Update("MemberPanel", "Member", "Add");
         	return true;
        	 
         } catch (BadMemberRegistrationException ex) {
        	 JOptionPane.showMessageDialog(new JFrame(),
        			 ex.getMessage().toString(),
 					"Error", JOptionPane.ERROR_MESSAGE);
         	return false;
        }
	}
}
