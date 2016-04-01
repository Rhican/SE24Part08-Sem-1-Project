package edu.nus.iss.SE24PT8.universityStore.gui.components.category;

import javax.swing.*;

import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.common.EntryJPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
/**
*
* @author Mugunthan
*/
public class AddCategoryDialog extends BaseDialogBox {

	private static final long serialVersionUID = 1L;
	
    private JTextField nameField;
    private JTextField codeField;

    public AddCategoryDialog () {
        super (MainWindow.getInstance(), "Add Cetegory","add");
    }

    protected JPanel createFormPanel  ()  {
    	EntryJPanel e = new EntryJPanel();
    	
        e.addCompCol1(new JLabel ("Code"));
        e.addCompCol1(new JTextField (3));
        
        e.addCompCol1(new JLabel ("Name"));
        e.addCompCol1( new JTextField (20));
        return e.getPanel();
    }

    protected boolean performCreateUpdateAction () {
        String name = nameField.getText();
        String code = codeField.getText();
        if ((name.length() == 0) || (code.length() == 0)) {
        	JOptionPane.showMessageDialog(new JFrame(),
					Constants.CONST_CAT_ERR_INVALID_DETAILS,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (code.length() != 3) {
        	JOptionPane.showMessageDialog(new JFrame(),
					Constants.CONST_CAT_ERR_LONG_CODE,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        ReturnObject  returnObject = Store.getInstance().getMgrCategory().addCategory(code, name);
        if (returnObject.isSuccess()) {
        	JOptionPane.showMessageDialog(new JFrame(),
        			returnObject.getMessage(),
					"Success", JOptionPane.INFORMATION_MESSAGE);
        	SubjectManager.getInstance().Update("CategoryPanel", "Category", "Add");
        	return true;
        } else {
        	JOptionPane.showMessageDialog(new JFrame(),
        			returnObject.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    }

}