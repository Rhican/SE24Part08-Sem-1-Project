package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.Dialog;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
*
* @author Mugunthan
*/
public abstract class BaseDialogBox extends JDialog {

	private static final long serialVersionUID = 1L;

	public BaseDialogBox (JFrame parent, String title, String operation) {
        super (parent, title);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.setLocation(getParent().getX() + 300, getParent().getY()+ 170);
        if(operation.equals("add")) {
            add ("Center", createFormPanel());        	
        }
        add ("South",  createButtonPanel(operation));
    }

    public BaseDialogBox (JFrame parent) {
        this (parent, "","");
    }

    private JPanel createButtonPanel (String operation) {
        JPanel p = new JPanel ();

        JButton b;
        ActionListener l;
        if (operation.equals("add")){
	        b = new JButton ("Add");
	        l = new ActionListener () {
	            public void actionPerformed (ActionEvent e) {
	                boolean success = performCreateUpdateAction ();
	                if (success) {
	                    destroyDialog ();
	                }
	            }
	        };
	        b.addActionListener (l);
	        p.add (b);
        } else {
	        b = new JButton ("Modify");
	        l = new ActionListener () {
	            public void actionPerformed (ActionEvent e) {
	                boolean success = performCreateUpdateAction ();
	                if (success) {
	                    destroyDialog ();
	                }
	            }
	        };
	        b.addActionListener (l);
	        p.add (b);        	
        }

        b = new JButton ("Cancel");
        l = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                destroyDialog ();
            }
        };
        b.addActionListener (l);
        p.add (b);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    private void destroyDialog () {
        setVisible (false);
        dispose();
    }

    protected abstract JPanel createFormPanel() ;

    protected abstract boolean performCreateUpdateAction() ;

}