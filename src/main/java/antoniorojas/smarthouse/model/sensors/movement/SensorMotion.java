/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.sensors.movement;

import antoniorojas.smarthouse.model.observer.IObserver;
import antoniorojas.smarthouse.model.sensors.Sensor;

import java.util.List;

public class SensorMotion extends Sensor {
    private final static String SENSORNAME = "SENMOV";
    private final static String SENSOROPEN = "OPEN";
    private final static String SENSORCLOSE = "CLOSE";
    private boolean state;

    public SensorMotion(String id, String location) {
        super(id, location);
        this.state = false;
    }

    public SensorMotion(String id, String location, List<IObserver> listeners) {
        super(id, location, listeners);
        this.state = false;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getState(){
        if (isState())
            return SENSOROPEN;
        else
            return SENSORCLOSE;
    }

    @Override
    public String getSensorMessage(){
        if (isState())
            sensorMessage = getId() + ":" + getLocation() + ":status changed:"+SENSOROPEN;
        else
            sensorMessage = getId() + ":" + getLocation() + ":status changed:"+SENSORCLOSE;
        return sensorMessage;
    }

    public void open() {
        if (!isState()) {
            setState(true);
            cleanObserverMessage();
            this.notifyObservers(SENSORNAME, getState());
        }
    }

    public void close() {
        if (isState()) {
            setState(false);
            cleanObserverMessage();
            this.notifyObservers(SENSORNAME,getState());
        }
    }
}
