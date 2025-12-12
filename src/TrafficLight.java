/*
 * File: TrafficLight.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Model class representing a traffic light in the simulation.
 *          Stores position, color, and duration constants. Provides cycling behavior.
 */

public class TrafficLight {
  // Fields
  private int position;
  private String color;
  private String name;

  // Final fields (duration in seconds)
  public static final int GREENDURATION = 10;
  public static final int YELLOWDURATION = 3;
  public static final int REDDURATION = 5;

  // Constructor
  public TrafficLight(int position, String color, String name) {
    this.position = position;
    this.color = color;
    this.name = name;
  }

  // Behavior
  public void cycle() {
    if (color.equals("GREEN")) {
      color = "YELLOW";
    }
    else if (color.equals("YELLOW")) {
      color = "RED";
    }
    else if (color.equals("RED")) {
      color = "GREEN";
    }
  }

  // light Checks
  public boolean isGreen() {
    return color.equals("GREEN");
  }

  public boolean isYellow() {
    return color.equals("YELLOW");
  }

  public boolean isRed() {
    return color.equals("RED");
  }

  // Getters
  public String getColor() {
    return color;
  }

  public int getPosition() {
    return position;
  }

  public String getName() {
    return name;
  }
}
