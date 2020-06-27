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
import antoniorojas.smarthouse.model.observer.IObserverSensorTemp;

public class Courtain extends Device implements IObserverSensorTemp {

    public Courtain(String id, String location) {
        super(id, location);
        this.setStatus(false);
    }

    public void checkSensTempColdAction (String action){
        if("COLD".equals(action) && isStatus()){
            setStatus(false);
            setMessage(getId() + ":" + getLocation() + ":status changed: expanded");
        }
    }

    public void checkSensTempHotAction (String action){
        if("HOT".equals(action) && !isStatus()){
            setStatus(true);
            setMessage(getId() + ":" + getLocation() + ":status changed: collapsed");
        }
    }

    public String getNotification(String sensorType, String action) {
        if ("SENTEMP".equals(sensorType)){
            checkSensTempColdAction(action);
            checkSensTempHotAction(action);
        }
        return getMessage();
    }
}
