# Smart House project

Smart house project works based on devices and sensors, where each device reacts due to some signals sent by sensors.

  - This app read the initial configuration from file input.
  - Any change on this file like update a sensor state, it needs to notify to devices.
  - Devices will receive the notifications and update their status.

# Diagram

![Diagram](/diagram/Package%20smarthouse.png?raw=true "Diagram")

### Project description

This project is based on Model-View-Controller(MVC)

Model
The central component of the pattern. It is the application's dynamic data structure, independent of the user interface. It directly manages the data, logic and rules of the application.

View
Any representation of information such as a chart, diagram or table. Multiple views of the same information are possible, such as a bar chart for management and a tabular view for accountants.

Controller
Accepts input and converts it to commands for the model or view.
In addition to dividing the application into these components, the model–view–controller design defines the interactions between them.

The model is responsible for managing the data of the application. It receives user input from the controller.

The view means presentation of the model in a particular format.

The controller responds to the user input and performs interactions on the data model objects. The controller receives the input, optionally validates it and then passes the input to the model.

### Installation

It requires java to run and optionally IntelliJ IDEA.

### How to run

Use the following configuration file [smartHouseSensors](/configuration/smartHouseSensors) to run this project.

```java
    public static void main( String[] args ) {
        Simulation simulation = new Simulation();
        simulation.loadSensor("smartHouseSensors file path here");
    }
```

where we have sensors

```
sensor=<sensor type>,<id>,<location>,<sensor action>
sensor=mov,mov-2,entrance,false
sensor=temp,temp-1,room,20.0,19.0
```

and we have observers

```
observer=<observer type>,<id>,<location>,<sensor where it will be registered>
observer=light,lgt-1,garage,mov-1
observer=light,lgt-2,entrance,mov-2
```

the idea without using the simulation is

```
public static void main( String[] args ) {
    List<IObserver> listeners = new ArrayList<>();
    listeners.add(new Light("l-1","garage"));
    listeners.add(new Light("l-2", "backyard"));
    SensorMotion garage = new SensorMotion("m-1","garage", listeners);
    garage.open();
    System.out.println(garage.getSensorMessage());
    System.out.println(garage.getObserverMessage());
    garage.close();
    System.out.println(garage.getSensorMessage());
    System.out.println(garage.getObserverMessage());
}
```