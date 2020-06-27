/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.observer;

import antoniorojas.smarthouse.model.response.ResponseMessage;

public class Device {
    private String id;
    private String location;
    private boolean status;
    private ResponseMessage message;

    public Device(String id, String location) {
        this.id = id;
        this.location = location;
        this.status = false;
        message = new ResponseMessage(null);
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message.getMessage();
    }

    public void setMessage(String message) {
        this.message.setMessage(message);
    }
}
