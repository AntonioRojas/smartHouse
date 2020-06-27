package antoniorojas.smarthouse.model.sensors.weather;

import antoniorojas.smarthouse.model.observer.IObserver;
import antoniorojas.smarthouse.model.observer.device.Courtain;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SensorTemperatureTest {
    public static List<IObserver> listeners;
    public static SensorTemperature temp;

    @BeforeClass
    public static void setUp() throws Exception {
        listeners = new ArrayList<>();
        listeners.add(new Courtain("c-1","room"));
        temp = new SensorTemperature("t-1","room", listeners);
    }

    @Test
    public void hot() {
        temp.setThresholdWeather(20.0);
        temp.setCurrentWeather(21.0);
        temp.checkThreshold();
        assertEquals("t-1:room:threshold exceeded:21.0",temp.getSensorMessage());
        assertEquals("c-1:room:status changed: collapsed",temp.getObserverMessage());
    }

    @Test
    public void cold() {
        temp.setThresholdWeather(20.0);
        temp.setCurrentWeather(11.0);
        temp.checkThreshold();
        assertEquals("t-1:room:threshold:11.0",temp.getSensorMessage());
        assertEquals("c-1:room:status changed: expanded",temp.getObserverMessage());
    }
}