/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.controller;

import antoniorojas.smarthouse.model.observer.device.Courtain;
import antoniorojas.smarthouse.model.observer.device.Light;
import antoniorojas.smarthouse.model.observer.IObserver;
import antoniorojas.smarthouse.model.observer.device.TV;
import antoniorojas.smarthouse.model.sensors.movement.SensorMotion;
import antoniorojas.smarthouse.model.sensors.weather.SensorTemperature;

import java.io.*;
import java.util.HashMap;

public class Simulation {
    private final static int TIMETOSLEEP = 30000;
    private final static int NUMBERSTOREAD = 5;
    private int count = 0 ;
    private HashMap<String, SensorMotion> sensorMotion;
    private HashMap<String, SensorTemperature> sensorTemperature;
    private HashMap<String, IObserver> observer;

    public void loadSensor(String fileName) {
        sensorMotion = new HashMap<>();
        sensorTemperature = new HashMap<>();
        observer = new HashMap<>();
        String linea;
        String typeObject;

        try {
            while (count <= NUMBERSTOREAD) {
                File file = new File(fileName);
                FileReader fr = null;
                BufferedReader br = null;
                fr = new FileReader (file);
                br = new BufferedReader(fr);
                System.out.println("----------Read # "+count+"----------------");
                if (count==0)
                    System.out.println("Creating sensors and observers");
                while((linea=br.readLine())!=null) {
                    typeObject = (linea.split("="))[0];
                    if ("sensor".equals(typeObject)) {
                        getSensorObjects(linea);
                    }
                    if ("observer".equals(typeObject)) {
                        getObserverObjects(linea);
                    }
                }
                count++;
                //Pause for 1 min
                Thread.sleep(TIMETOSLEEP);
                file = null;
                fr.close();
                br.close();
                System.out.println("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File " + fileName + " not found!");
        } catch (IOException e) {
            System.out.println("ERROR: File " + fileName + " cannot be read!");
        } catch (InterruptedException e) {
            System.out.println("ERROR: sleep exception!");
        }
    }

    private void getSensorObjects(String linea){
        String[] sensorProperty = (linea.split("="))[1].split(",");
        if ("mov".equals(sensorProperty[0])) {
            checkSensorMovement(sensorProperty);
        }
        if ("temp".equals(sensorProperty[0])) {
            checkSensorTemperature(sensorProperty);
        }
    }

    private void getObserverObjects(String linea){
        String[] observerProperty = (linea.split("="))[1].split(",");
        if ("light".equals(observerProperty[0])){
            checkLightObserver(observerProperty);
        }
        if ("tv".equals(observerProperty[0])){
            checkTVObserver(observerProperty);
        }
        if ("courtain".equals(observerProperty[0])){
            checkCourtainObserver(observerProperty);
        }
    }

    private void checkLightObserver(String[] observerProperty){
        if (observer.get(observerProperty[1])==null) {
            observer.put(observerProperty[1], new Light(observerProperty[1], observerProperty[2]));
            registerObserver(observerProperty[3], observerProperty[1]);
        }
    }

    private void checkTVObserver(String[] observerProperty){
        if (observer.get(observerProperty[1])==null) {
            observer.put(observerProperty[1], new TV(observerProperty[1], observerProperty[2]));
            registerObserver(observerProperty[3], observerProperty[1]);
        }
    }

    private void checkCourtainObserver(String[] observerProperty){
        if (observer.get(observerProperty[1])==null) {
            observer.put(observerProperty[1], new Courtain(observerProperty[1], observerProperty[2]));
            registerObserver(observerProperty[3], observerProperty[1]);
        }
    }

    private void registerObserver(String sensorId, String observerId){
        if (sensorMotion.get(sensorId) != null && sensorMotion.get(sensorId).register(observer.get(observerId))){
            System.out.println("Observer " + observerId + " was registered to Sensor Movement: " + sensorId);
        }
        if (sensorTemperature.get(sensorId) != null && sensorTemperature.get(sensorId).register(observer.get(observerId))){
            System.out.println("Observer " + observerId + " was registered to Sensor Temperature: " + sensorId);
        }
    }

    private void checkSensorMovement(String[] sensorProperty){
        if (sensorMotion.get(sensorProperty[1])==null) {
            sensorMotion.put(sensorProperty[1], new SensorMotion(sensorProperty[1], sensorProperty[2]));
        }else{
            if (sensorProperty[3].equals("true") && !sensorMotion.get(sensorProperty[1]).isState()){
                sensorMotion.get(sensorProperty[1]).open();
                System.out.println(sensorMotion.get(sensorProperty[1]).getSensorMessage());
                System.out.println(sensorMotion.get(sensorProperty[1]).getObserverMessage());
                sensorMotion.get(sensorProperty[1]).setSensorMessage(null);
                sensorMotion.get(sensorProperty[1]).cleanObserverMessage();
            }else if (sensorProperty[3].equals("false") && sensorMotion.get(sensorProperty[1]).isState()){
                sensorMotion.get(sensorProperty[1]).close();
                System.out.println(sensorMotion.get(sensorProperty[1]).getSensorMessage());
                System.out.println(sensorMotion.get(sensorProperty[1]).getObserverMessage());
                sensorMotion.get(sensorProperty[1]).setSensorMessage(null);
                sensorMotion.get(sensorProperty[1]).cleanObserverMessage();
            }
        }
    }

    private void checkSensorTemperature(String[] sensorProperty){
        if (sensorTemperature.get(sensorProperty[1])==null) {
            sensorTemperature.put(sensorProperty[1], new SensorTemperature(sensorProperty[1], sensorProperty[2]));
        }else{
            sensorTemperature.get(sensorProperty[1]).setThresholdWeather(Double.valueOf(sensorProperty[3]));
            sensorTemperature.get(sensorProperty[1]).setCurrentWeather(Double.valueOf(sensorProperty[4]));
            sensorTemperature.get(sensorProperty[1]).checkThreshold();
            if (sensorTemperature.get(sensorProperty[1]).getSensorMessage()!=null &&
                    sensorTemperature.get(sensorProperty[1]).getObserverMessage()!=null) {
                System.out.println(sensorTemperature.get(sensorProperty[1]).getSensorMessage());
                System.out.println(sensorTemperature.get(sensorProperty[1]).getObserverMessage());
                sensorTemperature.get(sensorProperty[1]).setSensorMessage(null);
                sensorTemperature.get(sensorProperty[1]).cleanObserverMessage();
            }
        }
    }
}
