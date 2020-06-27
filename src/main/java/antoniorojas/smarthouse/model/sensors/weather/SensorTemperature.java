/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.sensors.weather;

import antoniorojas.smarthouse.model.observer.IObserver;
import antoniorojas.smarthouse.model.sensors.Sensor;

import java.util.List;

public class SensorTemperature extends Sensor {
    private final static String SENSORNAME = "SENTEMP";
    private final static String SENSORHOT = "HOT";
    private final static String SENSORCOLD = "COLD";
    private double currentWeather;
    private double thresholdWeather;
    private boolean thresholdExceed;
    private double previousState;

    public SensorTemperature(String id, String location) {
        super(id, location);
        this.currentWeather = 0.0;
        this.thresholdWeather = 0.0;
        this.thresholdExceed = false;
        this.previousState = currentWeather;
    }

    public SensorTemperature(String id, String location, List<IObserver> listeners) {
        super(id, location, listeners);
        this.currentWeather = 0.0;
        this.thresholdWeather = 0.0;
        this.thresholdExceed = false;
    }

    public double getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(double currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setThresholdWeather(double thresholdWeather) {
        this.thresholdWeather = thresholdWeather;
    }

    public double getThresholdWeather() {
        return thresholdWeather;
    }

    public boolean isThresholdExceed() {
        return thresholdExceed;
    }

    public void setThresholdExceed(boolean thresholdExceed) {
        this.thresholdExceed = thresholdExceed;
    }

    public double getPreviousState() {
        return previousState;
    }

    public void setPreviousState(double previousState) {
        this.previousState = previousState;
    }

    @Override
    public String getSensorMessage(){
        if (currentWeather >= thresholdWeather && isThresholdExceed() && previousState!=currentWeather)
            sensorMessage = getId() + ":" + getLocation() + ":threshold exceeded:" + getCurrentWeather();
        if (currentWeather <= thresholdWeather && !isThresholdExceed() && previousState!=currentWeather)
            sensorMessage = getId() + ":" + getLocation() + ":threshold:"+getCurrentWeather();
        previousState = currentWeather;
        return sensorMessage;
    }

    private void checkThresholdUp() {
        if (currentWeather >= thresholdWeather && !isThresholdExceed()) {
            this.setThresholdExceed(true);
            this.cleanObserverMessage();
            this.notifyObservers(SENSORNAME,SENSORHOT);
        }
    }

    private void checkThresholdDown() {
        if (currentWeather <= thresholdWeather && isThresholdExceed()) {
            this.setThresholdExceed(false);
            this.cleanObserverMessage();
            this.notifyObservers(SENSORNAME,SENSORCOLD);
        }
    }

    public void checkThreshold() {
        this.checkThresholdDown();
        this.checkThresholdUp();
    }
}
