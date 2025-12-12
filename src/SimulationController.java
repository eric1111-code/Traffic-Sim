/*
 * File: SimulationController.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Controller class that manages the simulation.
 *          Handles button actions, creates/manages threads, and coordinates all components.
 */

import java.util.ArrayList;

public class SimulationController {
  // Fields
  private TrafficSimGUI gui;
  private TimeThread timeThread;
  private Thread timeThreadRunner;
  private ArrayList<TrafficLight> lights = new ArrayList<>();
  private ArrayList<TrafficLightThread> lightThreads = new ArrayList<>();
  private ArrayList<Thread> lightThreadRunners = new ArrayList<>();
  private ArrayList<Car> cars = new ArrayList<>();
  private ArrayList<CarThread> carThreads = new ArrayList<>();
  private ArrayList<Thread> carThreadRunners = new ArrayList<>();

  // Constructor
  public SimulationController(TrafficSimGUI gui) {
    this.gui = gui;
    setupButtonListeners();

    // Create initial traffic lights (1000m apart per requirements)
    lights.add(new TrafficLight(1000, "GREEN", "Light 1"));
    lights.add(new TrafficLight(2000, "YELLOW", "Light 2"));
    lights.add(new TrafficLight(3000, "RED", "Light 3"));

    // Create initial cars (speeds in m/s - higher speeds for faster simulation)
    cars.add(new Car("Car 1", 100));
    cars.add(new Car("Car 2", 150));
    cars.add(new Car("Car 3", 200));
  }

  // Button setup
  private void setupButtonListeners() {
    gui.getStartButton().addActionListener(e -> start());
    gui.getPauseButton().addActionListener(e -> pause());
    gui.getStopButton().addActionListener(e -> stop());
    gui.getContinueButton().addActionListener(e -> resume());
    gui.getAddCarButton().addActionListener(e -> addCar());
    gui.getAddIntersectionButton().addActionListener(e -> addIntersection());
  }

  // Button logic
  private void start() {
    // Start time thread
    if (timeThread == null) {
      timeThread = new TimeThread(gui);
      timeThreadRunner = new Thread(timeThread);
      timeThreadRunner.start();
    }

    // Start light threads
    if (lightThreads.isEmpty()) {
      for (int i = 0; i < lights.size(); i++) {
        TrafficLightThread lt = new TrafficLightThread(gui, lights.get(i), i);
        lightThreads.add(lt);
        Thread t = new Thread(lt);
        lightThreadRunners.add(t);
        t.start();
      }
    }

    // Start car threads
    if (carThreads.isEmpty()) {
      for (int i = 0; i < cars.size(); i++) {
        CarThread ct = new CarThread(gui, cars.get(i), lights, i);
        carThreads.add(ct);
        Thread t = new Thread(ct);
        carThreadRunners.add(t);
        t.start();
      }
    }
  }
  
  private void pause() {
    if (timeThread != null) {
      timeThread.pause();
    }
    for (TrafficLightThread lt : lightThreads) {
      lt.pause();
    }
    for (CarThread ct : carThreads) {
      ct.pause();
    }
  }
  
  private void resume() {
    if (timeThread != null) {
      timeThread.resume();
    }
    for (TrafficLightThread lt : lightThreads) {
      lt.resume();
    }
    for (CarThread ct : carThreads) {
      ct.resume();
    }
  }
  
  private void stop() {
    // Stop time thread
    if (timeThread != null) {
      timeThread.stop();
      timeThread = null;
      timeThreadRunner = null;
      gui.updateTime("00:00:00");
    }

    // Stop light threads
    for (TrafficLightThread lt : lightThreads) {
      lt.stop();
    }
    lightThreads.clear();
    lightThreadRunners.clear();

    // Stop car threads
    for (CarThread ct : carThreads) {
      ct.stop();
    }
    carThreads.clear();
    carThreadRunners.clear();

    // Reset cars to initial state
    for (int i = 0; i < cars.size(); i++) {
      cars.get(i).reset();  // Reset the Car object
      gui.getCarTableModel().setValueAt(0, i, 1);          // X = 0
      gui.getCarTableModel().setValueAt(0, i, 2);          // Y = 0
      gui.getCarTableModel().setValueAt(cars.get(i).getOriginalSpeed(), i, 3);  // Original speed
      gui.getCarTableModel().setValueAt("Waiting", i, 4);  // Status
    }
  }

  // Add a new car to the simulation
  private void addCar() {
    int carNum = cars.size() + 1;
    int speed = 125 + (carNum * 25);  // Each new car is slightly faster
    Car newCar = new Car("Car " + carNum, speed);
    cars.add(newCar);

    // Add row to GUI table
    gui.getCarTableModel().addRow(new Object[]{"Car " + carNum, 0, 0, speed, "Waiting"});

    // If simulation is running, start thread for new car
    if (timeThread != null) {
      int tableRow = cars.size() - 1;
      CarThread ct = new CarThread(gui, newCar, lights, tableRow);
      carThreads.add(ct);
      Thread t = new Thread(ct);
      carThreadRunners.add(t);
      t.start();
    }
  }

  // Add a new intersection to the simulation
  private void addIntersection() {
    int lightNum = lights.size() + 1;
    int position = lightNum * 1000;  // Each light 1000m apart
    TrafficLight newLight = new TrafficLight(position, "GREEN", "Light " + lightNum);
    lights.add(newLight);

    // Add to GUI
    gui.addTrafficLight(position + "m", "GREEN");

    // If simulation is running, start thread for new light
    if (timeThread != null) {
      int index = lights.size() - 1;
      TrafficLightThread lt = new TrafficLightThread(gui, newLight, index);
      lightThreads.add(lt);
      Thread t = new Thread(lt);
      lightThreadRunners.add(t);
      t.start();
    }
  }
}
