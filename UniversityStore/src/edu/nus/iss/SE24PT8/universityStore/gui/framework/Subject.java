/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.gui.framework;

import java.util.ArrayList;

/**
 *
 * @author SE24PT8
 */
public class Subject {

    private final String group, topic;
    private ArrayList<INotificable> observers;

    public Subject(String group, String topic) {
        this.group = group;
        this.topic = topic;
        observers = new ArrayList<INotificable>();
    }

    public void addObserver(INotificable observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    public ArrayList<INotificable> getObservers() {
        return observers;
    }

    public String getTopic() {
        return topic;
    }

    public String getGroup() {
        return group;
    }

    public boolean update(String data) {
        for (INotificable observer : observers) {
            try {
                observer.update(group, topic, data);
            } catch (Exception ex) {
                // TODO
                return false;
            }
        }
        return true;
    }
}
