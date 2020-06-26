/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.sensors;

import antoniorojas.smarthouse.model.observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Sensor implements ISensor {
    protected String id;
    protected String location;
    protected List<IObserver> listeners;
    protected String sensorMessage;
    protected List<String> observerMessage;

    public Sensor(String id, String location) {
        this.id = id;
        this.location = location;
        this.observerMessage = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public Sensor(String id, String location, List<IObserver> listeners) {
        this.id = id;
        this.location = location;
        this.observerMessage = new ArrayList<>();
        this.listeners = listeners;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<IObserver> getListeners() {
        return listeners;
    }

    public void setListeners(List<IObserver> listeners) {
        this.listeners = listeners;
    }

    public abstract String getSensorMessage();

    public boolean register(IObserver observ) {
        return listeners.add(observ);
    }

    public boolean unregister(IObserver observ) {
        return listeners.remove(observ);
    }

    public void unregisterAll() {
        listeners.clear();
    }

    public void setSensorMessage(String sensorMessage) {
        this.sensorMessage = sensorMessage;
    }

    public void cleanObserverMessage() {
        this.observerMessage.clear();
    }

    public String getObserverMessage(){
        String messageObserver = null;
        for (int i=0;i<observerMessage.size();i++){
            if (i==0)
                messageObserver = observerMessage.get(i);
            else
                messageObserver = messageObserver + System.getProperty("line.separator") + observerMessage.get(i);
        }
        return messageObserver;
    }

    public void notifyObservers(String triggerType, String triggerAction) {
        for (IObserver IObserver : this.listeners) {
            observerMessage.add(IObserver.getNotification(triggerType, triggerAction));
        }
    }

}
