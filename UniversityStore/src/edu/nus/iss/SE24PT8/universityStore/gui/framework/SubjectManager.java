/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.gui.framework;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author SE24PT8
 */
public class SubjectManager {

    private HashMap<String, ArrayList<Subject>> subjects;

    private static SubjectManager instance = null;

    public static SubjectManager getInstance() {
        if (instance == null) {
            instance = new SubjectManager();
        }
        return instance;
    }

    /**
     * Updating the INotificable objects for the specific topic
     * @param group, the group of the topic
     * @param topic, the interest topic
     * @param data, the data content
     * @return  true if update successfully
     */
    public boolean Update(String group, String topic, String data) {
        if (!subjects.containsKey(group)) {
            return false;
        }
        ArrayList<Subject> subjectList = subjects.get(group);
        Subject subjectFound = null;
        for (Subject subject : subjectList) {
            if (subject.getTopic() == null ? topic == null : subject.getTopic().equals(topic)) {
                subjectFound = subject;
                break;
            }
        }
        return (subjectFound != null) ? subjectFound.update(data) : false;
    }

    private SubjectManager() {
        subjects = new HashMap<String, ArrayList<Subject>>();
    }

    /**
     * Adding new INotificable object (observer)
     * @param group, the group of the topic
     * @param topic, the interest topic
     * @param observer,the INotificable object
     */
    public void addNotification(String group, String topic, INotificable observer ) {
        addSubject(group, topic).addObserver(observer); // Add notificable object/ observer
    }
    
    /**
     * Adding new Subject
     * @param group, the group of the topic
     * @param topic, the interest topic
     * @param observer,the INotificable object
     */
    public Subject addSubject(String group, String topic) {
        if (!subjects.containsKey(group)) {
            subjects.put(group, new ArrayList<>()); // Create Group
        }

        ArrayList<Subject> subjectList = subjects.get(group);
        Subject subjectFound = null;
        for (Subject subject : subjectList) {
            if (subject.getTopic() == null ? topic == null : subject.getTopic().equals(topic)) {
                subjectFound = subject;
                break;
            }
        }
        if (subjectFound == null) {
            subjectFound = new Subject(group, topic); // Create topic /subject
            subjectList.add(subjectFound);
        }
        return subjectFound;
    }

}
