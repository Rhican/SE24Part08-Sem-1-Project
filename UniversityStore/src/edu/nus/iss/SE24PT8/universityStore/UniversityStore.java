/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nus.iss.SE24PT8.universityStore;

import edu.nus.iss.SE24PT8.universityStore.gui.framework.INotificable;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.Login;
import edu.nus.iss.SE24PT8.universityStore.gui.mainWindow.MainWindow;
import edu.nus.iss.SE24PT8.universityStore.main.Store;

/**
 *
 * @author SE24PT8
 */
public class UniversityStore implements INotificable {

	private Login login = null;
	private MainWindow mainWindow = null;
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	new UniversityStore();
    }

    public UniversityStore() {
    	Store.getInstance();
    	login = new Login();
    	mainWindow = MainWindow.getInstance();
    	
    	SubjectManager.getInstance().addNotification("Top", "MainWindow", this);
		SubjectManager.getInstance().addNotification("Top", "Login", this);
    }
    
	@Override
	public void update(String group, String topic, String data) {
		if (group.equals("Top") && topic.equals("MainWindow")) {
			if (data.equalsIgnoreCase("LogOut")){
				login.ShowLogIn(true);
				mainWindow.HideMainWindow();
			}
		}
		else if (group.equals("Top") && topic.equals("Login")) {
			if (data.equalsIgnoreCase("Success")){
				mainWindow.ShowMainWindow(login.getID());
				login.HideLogIn();
			}
		}
		
	}
    
}
