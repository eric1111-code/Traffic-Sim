# CMSC 335 Project 3 - Traffic Simulation

A Java Swing GUI application that simulates traffic flow with multiple cars traversing intersections with traffic lights. The simulation demonstrates event handlers, listeners, and Java's concurrency functionality using threads.

## Author
**Eric Sampson**  
Date: December 9, 2025

---

## Features

- **Real-time clock display** updating every second
- **Traffic light simulation** for 3+ intersections cycling through GREEN → YELLOW → RED
- **Car tracking** displaying X position, Y position, speed, and status for multiple vehicles
- **Multithreaded design** with separate threads for time, traffic lights, and cars
- **Interactive controls**: Start, Pause, Stop, and Continue buttons
- **Dynamic simulation**: Add more cars and intersections via GUI buttons

---

## Requirements

- **Java JDK 8** or higher
- **Java Swing** (included in JDK)

---

## Project Structure

```
Project 3/
├── src/
│   ├── TrafficSimGUI.java       # Main GUI frame and entry point
│   ├── SimulationController.java # Controls simulation logic and threads
│   ├── Car.java                  # Car model class
│   ├── CarThread.java            # Thread for car movement
│   ├── TrafficLight.java         # Traffic light model class
│   ├── TrafficLightThread.java   # Thread for light cycling
│   └── TimeThread.java           # Thread for time display
└── README.md
```

## Usage

1. **Start** - Begins the simulation; time starts, lights cycle, cars move
2. **Pause** - Temporarily halts all threads (time, lights, cars)
3. **Continue** - Resumes a paused simulation
4. **Stop** - Ends the simulation and resets all values to initial state
5. **Add Car** - Adds a new car to the simulation
6. **Add Intersection** - Adds a new traffic light 1000m after the last one

---

## Simulation Details

### Units of Measure
- **Distance**: Meters (m)
- **Speed**: Meters per second (m/s)
- **Time**: Seconds (s)

### Traffic Light Timing
| Color | Duration |
|-------|----------|
| GREEN | 10 seconds |
| YELLOW | 3 seconds |
| RED | 5 seconds |

### Initial Configuration
- **3 Intersections** at 1000m, 2000m, and 3000m
- **3 Cars** with speeds of 100, 150, and 200 m/s
- **Y Position** = 0 (cars travel in a straight line)

---

## License

This project was created for educational purposes as part of CMSC 335 at UMGC.

