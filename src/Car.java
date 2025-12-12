/*
 * File: Car.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Model class representing a car in the traffic simulation.
 *          Stores position, speed, and status. Provides methods for movement control.
 */

public class Car {
  // Fields
  private String name;
  private int speed;
  private int originalSpeed;  // Store original speed for restoration
  private int x = 0;
  private int y = 0;
  private String status = "WAITING";

  // Constructor
  public Car(String name, int speed) {
    this.name = name;
    this.speed = speed;
    this.originalSpeed = speed;  // Save original speed
  }

  // Behavior
  public void move() {
    x = x + speed;
    status = "MOVING";
  }

  public void stop() {
    speed = 0;
    status = "STOPPED";
  }

  public void go() {
    speed = originalSpeed;  // Restore original speed
    status = "MOVING";
  }

  // Reset car to initial state
  public void reset() {
    x = 0;
    y = 0;
    speed = originalSpeed;
    status = "WAITING";
  }

  // Getters
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String getName() {
    return name;
  }

  public int getSpeed() {
    return speed;
  }

  public String getStatus() {
    return status;
  }

  public int getOriginalSpeed() {
    return originalSpeed;
  }
}
