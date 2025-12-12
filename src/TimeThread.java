/*
 * File: TimeThread.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Thread class that updates the simulation time display.
 *          Increments time every second and updates the GUI.
 */

public class TimeThread implements Runnable {
  // Fields
  private TrafficSimGUI gui;
  private int seconds;
  private boolean running;
  private boolean paused;

  // Constructor
  public TimeThread(TrafficSimGUI gui) {
    this.gui = gui;
    this.seconds = 0;
    this.running = true;
    this.paused = false;
  }

  // Thread entry point
  public void run() {
    while (running) {
      if (!paused) {
        seconds++;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
        gui.updateTime(time);
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("ERROR");
      }
    }
  }

  // Control methods
  public void pause() { paused = true; }
  public void resume() { paused = false; }
  public void stop() { running = false; }
}
