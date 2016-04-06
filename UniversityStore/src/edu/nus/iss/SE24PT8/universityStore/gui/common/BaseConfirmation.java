package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class BaseConfirmation extends JDialog {
	
	/**
	 * 
	 */
	private JPanel p ;

	private JButton btnOk;
	private JButton btnCancle;
	
	private static final long serialVersionUID = 1L;
	public BaseConfirmation(JFrame parent, String title) {
		super(parent, title);
		super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		super.setLocationRelativeTo(parent);
		 add ("Center",  createButtonPanel());

	}
	
	public BaseConfirmation (JFrame parent) {
        this (parent, "");
    }

	private JPanel createButtonPanel() {
		p = new JPanel();
		ActionListener l;
		btnCancle = new JButton("Cancle");
		btnOk = new JButton("OK");
		l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(btnCancle)) {
					destroyDialog();
				} else if (e.getSource().equals(btnOk)) {
					boolean success = performCreateUpdateAction();
					if (success) {
						destroyDialog();
					}
				}

			}
		};
		btnCancle.addActionListener(l);
		btnOk.addActionListener(l);

		return p;
	}
	
	
	
    private void destroyDialog () {
        setVisible (false);
        dispose();
    }
    protected abstract boolean performCreateUpdateAction() ;
}
