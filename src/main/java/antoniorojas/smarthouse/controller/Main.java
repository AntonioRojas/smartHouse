/*
 *  Copyright (c) 2020 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jalasoft.
 */

package antoniorojas.smarthouse.controller;

public class Main {
    public static void main( String[] args ) {
        Simulation simulation = new Simulation();
        simulation.loadSensor("E:\\TechGroupMod1\\src\\main\\resources\\smartHouseSensors");
    }
}
