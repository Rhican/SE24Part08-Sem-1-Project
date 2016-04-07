package edu.nus.iss.SE24PT8.universityStore.gui.components.category;

import java.awt.Dialog;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.nus.iss.SE24PT8.universityStore.exception.BadCategoryException;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
/**
*
* @author Mugunthan
*/
public class AddCategoryDialog extends BaseDialogBox {

	private static final long serialVersionUID = 1L;
	
    private JTextField nameField;
    private JTextField codeField;

    public AddCategoryDialog () {
        super (MainWindow.getInstance(), "New Category","add");
        super.setModalityType(Dialog.ModalityType.MODELESS);
    }

    protected JPanel createFormPanel  ()  {
    	JPanel p = new JPanel ();
        
        p.setLayout ((new GridLayout (0, 2)));
        JLabel codeLabel = new JLabel ("Code");
        p.add(codeLabel);
        codeField = new JTextField (3);
        p.add (codeField);
        JLabel nameLabel = new JLabel ("Name");
        p.add(nameLabel);
        nameField = new JTextField (20);
        p.add (nameField);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    protected boolean performCreateUpdateAction () {
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
        try {
			Store.getInstance().getMgrCategory().addCategory(code, name);
        	JOptionPane.showMessageDialog(rootPane,
        			Constants.CONST_CAT_MSG_CREATION_SUCUESS,
					"Success", JOptionPane.INFORMATION_MESSAGE);
        	SubjectManager.getInstance().Update("CategoryPanel", "Category", "Add");
        	return true;
		} catch (BadCategoryException e) {
        	JOptionPane.showMessageDialog(rootPane,
        			e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
		}
    }

}