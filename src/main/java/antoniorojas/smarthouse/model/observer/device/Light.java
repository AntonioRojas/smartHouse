/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.observer.device;

import antoniorojas.smarthouse.model.observer.Device;
import antoniorojas.smarthouse.model.observer.IObserverSensorMov;

public class Light extends Device implements IObserverSensorMov {

    public Light(String id, String location) {
        super(id, location);
        this.setStatus(false);
    }

    public void checkSensMovOpenAction (String action){
        if("OPEN".equals(action) && !isStatus()){
            setStatus(true);
            setMessage(getId() + ":" + getLocation() + ":status changed: turn on");
        }
    }

    public void checkSensMovCloseAction (String action){
        if("CLOSE".equals(action) && isStatus()){
            setStatus(false);
            setMessage(getId() + ":" + getLocation() + ":status changed: turn off");
        }
    }

    public String getNotification(String sensorType, String action) {
        if ("SENMOV".equals(sensorType)){
            checkSensMovOpenAction(action);
            checkSensMovCloseAction(action);
        }
        return getMessage();
    }
}
