package edu.nus.iss.SE24PT8.universityStore.gui.common;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
*
* @author Mugunthan
*/
public class BaseTable extends JTable{
	
	public BaseTable(DefaultTableModel categories){
		super();
		super.setModel(categories);
		//super(categories, columnNames);
	}
	
	//make row as un edittable
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
