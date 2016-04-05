package edu.nus.iss.SE24PT8.universityStore.gui.components;

import java.awt.Dialog;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
public class MemberEntryDialog extends BaseDialogBox{
	
	private static final long serialVersionUID = 1L;
	private JTextField membernameField;
	private JTextField memberIDField;
	
	public MemberEntryDialog(){
		super(MainWindow.getInstance(),"New member","add");
		super.setModalityType(Dialog.ModalityType.MODELESS);
	}
	
	protected JPanel createFormPanel(){
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new JLabel("MemberName"));
		membernameField=new JTextField(20);
		p.add(membernameField);
		p.add(new JLabel("MemberID"));
		memberIDField=new JTextField(3);
		p.add(memberIDField);
		return p;
	}
   
	protected boolean performCreateUpdateAction(){
		String membername=membernameField.getText();
		String memberid=memberIDField.getText();
		try {
			 Store.getInstance().getMgrMember().addMember(membername,memberid);
			 JOptionPane.showMessageDialog(rootPane,
	        			"User has been registered successfully",
						"Success", JOptionPane.INFORMATION_MESSAGE);
	        	SubjectManager.getInstance().Update("MemberPanel", "Member", "Add");
	        	return true;
        } catch (BadMemberRegistrationException ex) {
        	JOptionPane.showMessageDialog(rootPane,
        			ex.getMessage().toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
      }
	}
}