/*
 * File: CarThread.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Thread class that controls car movement in the simulation.
 *          Moves cars, checks for red lights, and updates the GUI table.
 */

import java.util.ArrayList;

public class CarThread implements Runnable {
  // Fields
  private TrafficSimGUI gui;
  private Car car;
  private ArrayList<TrafficLight> lights;
  private int tableRow;
  private boolean running;
  private boolean paused;

  // Constructor
  public CarThread(TrafficSimGUI gui, Car car, ArrayList<TrafficLight> lights, int tableRow) {
    this.gui = gui;
    this.car = car;
    this.lights = lights;
    this.tableRow = tableRow;
    this.running = true;
    this.paused = false;
  }

  // Thread entry point
  public void run() {
    while (running) {
      if (!paused) {
        // Find next light ahead of car
        TrafficLight nextLight = null;
        for (TrafficLight light : lights) {
          if (light.getPosition() > car.getX()) {
            nextLight = light;
            break;
          }
        }

        // Red = stop, Yellow/Green = go
        if (nextLight != null && nextLight.isRed()) {
          car.stop();
        } else {
          car.go();
          car.move();
        }

        // Update GUI table
        updateCarTable();

        // Sleep for 1 second (simulation tick)
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("ERROR");
        }

      } else {
        // When paused, sleep briefly to avoid busy-waiting
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          System.out.println("ERROR");
        }
      }
    }
  }

  // Update the car's row in the GUI table
  private void updateCarTable() {
    gui.getCarTableModel().setValueAt(car.getX(), tableRow, 1);      // X position
    gui.getCarTableModel().setValueAt(car.getY(), tableRow, 2);      // Y position
    gui.getCarTableModel().setValueAt(car.getSpeed(), tableRow, 3);  // Speed
    gui.getCarTableModel().setValueAt(car.getStatus(), tableRow, 4); // Status
  }

  // Control methods
  public void pause() { paused = true; }
  public void resume() { paused = false; }
  public void stop() { running = false; }
}
