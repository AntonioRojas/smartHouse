package antoniorojas.smarthouse.model.sensors.movement;

import antoniorojas.smarthouse.model.observer.IObserver;
import antoniorojas.smarthouse.model.observer.device.Light;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SensorMotionTest {
    public static List<IObserver> listeners;
    public static SensorMotion garage;

    @BeforeClass
    public static void setUp() throws Exception {
        listeners = new ArrayList<>();
        listeners.add(new Light("l-1","garage"));
        garage = new SensorMotion("m-1","garage", listeners);
    }

    @Test
    public void open() {
        garage.open();
        assertEquals("m-1:garage:status changed:OPEN",garage.getSensorMessage());
        assertEquals("l-1:garage:status changed: turn on",garage.getObserverMessage());
    }

    @Test
    public void close() {
        garage.close();
        assertEquals("m-1:garage:status changed:CLOSE",garage.getSensorMessage());
        assertEquals("l-1:garage:status changed: turn off",garage.getObserverMessage());
    }
}