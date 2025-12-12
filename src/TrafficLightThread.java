/*
 * File: TrafficLightThread.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Thread class that controls traffic light cycling.
 *          Cycles through green, yellow, and red based on duration timers.
 */

public class TrafficLightThread implements Runnable {
  // Fields
  private TrafficSimGUI gui;
  private TrafficLight light;
  private int index;
  private boolean running;
  private boolean paused;

  // Constructor
  public TrafficLightThread(TrafficSimGUI gui, TrafficLight light, int index) {
    this.gui = gui;
    this.light = light;
    this.index = index;
    this.running = true;
    this.paused = false;
  }

  // Thread entry point
  public void run() {
    while (running) {
      if (!paused) {
        // Get sleep duration based on current color
        int sleepTime;
        if (light.isGreen()) {
          sleepTime = TrafficLight.GREENDURATION;
        } else if (light.isYellow()) {
          sleepTime = TrafficLight.YELLOWDURATION;
        } else {
          sleepTime = TrafficLight.REDDURATION;
        }

        // Sleep for the duration (convert seconds to milliseconds)
        try {
          Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
          System.out.println("ERROR");
        }

        // Cycle to next color
        light.cycle();

        // Update GUI to show new color
        gui.updateTrafficLight(index, light.getColor());

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

  // Control methods
  public void pause() { paused = true; }
  public void resume() { paused = false; }
  public void stop() { running = false; }
}
