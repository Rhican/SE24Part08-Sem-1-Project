package edu.nus.iss.SE24PT8.universityStore.gui.components.category;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nus.iss.SE24PT8.universityStore.UniversityStore;
import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.gui.common.BaseDialogBox;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;

public class ModifyCategoryDialog extends BaseDialogBox {

	private static final long serialVersionUID = 1L;
	
    private JTextField nameField;
    private static Category cat;

    public ModifyCategoryDialog (Category cat) {
        super (MainWindow.getInstance(), "Modify Cetegory","modify");
        this.cat = cat;
        add ("Center", createFormPanel());
    }

    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        
        p.setLayout (new GridLayout (0, 2));
        p.add (new JLabel ("Code"));
        p.add(new Label(cat.getCategoryCode()));
        //codeField = new JTextField (3);
        //p.add (codeField);
        p.add (new JLabel ("Name"));
        nameField = new JTextField (cat.getCategoryName(), 20);
        p.add (nameField);
        return p;
    }

    protected boolean performCreateUpdateAction () {
        String name = nameField.getText();
        if ((name.length() == 0)) {
        	JOptionPane.showMessageDialog(new JFrame(),
					Constants.CONST_CAT_ERR_INVALID_DETAILS,
					"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        cat.setCategoryName(name);
        ReturnObject  returnObject = Store.getInstance().getMgrCategory().updateCategory(cat);
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
