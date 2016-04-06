package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SuccessMessageDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SuccessMessageDialog dialog = new SuccessMessageDialog("MAIN");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	JButton okButton;
	JPanel buttonPane;
	JLabel lblMessage;

	/**
	 * Create the dialog.
	 */
	public SuccessMessageDialog(String message) {
		setBounds(100, 100, 250, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblMessage = new JLabel();
		lblMessage.setText("MEsage");
		contentPanel.add(lblMessage);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			 buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				 okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				destroy();
				
			}
		});
		
	}
	
	public void destroy(){
		setVisible(false);
		dispose();
	}

}
