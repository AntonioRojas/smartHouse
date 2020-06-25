/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.model.observer;

public interface IObserverSensorMov extends IObserver {
    void checkSensMovOpenAction (String action);
    void checkSensMovCloseAction(String action);
}
