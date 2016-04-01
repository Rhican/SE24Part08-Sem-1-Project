package edu.nus.iss.SE24PT8.universityStore.gui.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
/**
*
* @author Mi Site
*/
public class EntryJPanel {
	
	private JPanel panel;
	private  Dimension txtDimenstion  = new Dimension(200,300);
	  
	  public EntryJPanel(){
		  this.panel = new JPanel();
		  panel.setLayout (new GridLayout (0, 2));
	  }
	  
	  public void addCompCol1(Component com){
	      panel.add (com);
	  }
	  	  
	  public JPanel getPanel() {
		return panel;
	}
	  public Dimension getDimension() {
			return txtDimenstion;
		}
	public static void main(String args[]){
		  JPanel p = new JPanel ();
		  Frame f = new Frame();
		  f.add(p);
		  f.pack();
		  
	  }
	  
     
      /*p.add (new JLabel ("Code"));
      codeField = new JTextField (3);
      p.add (codeField);
      p.add (new JLabel ("Name"));
      nameField = new JTextField (20);
      p.add (nameField);*/
}
