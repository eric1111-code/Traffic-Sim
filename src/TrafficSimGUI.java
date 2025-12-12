/*
 * File: TrafficSimGUI.java
 * Date: 12/9/2025
 * Author: Eric Sampson
 * Purpose: Main GUI class for the Traffic Simulator application.
 *          Displays time, traffic lights, and car status table.
 *          Provides buttons for simulation control and adding cars/intersections.
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TrafficSimGUI extends JFrame {
  // Buttons
  JButton startButton = new JButton("Start");
  JButton pauseButton = new JButton("Pause");
  JButton stopButton = new JButton("Stop");
  JButton continueButton = new JButton("Continue");
  JButton addCarButton = new JButton("+ Car");
  JButton addIntersectionButton = new JButton("+ Intersection");

  // Labels
  JLabel timeLabel = new JLabel("Time: 00:00:00");

  // Panels
  JPanel topPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel bottomPanel = new JPanel();

  // Center sub-panels
  JPanel trafficLightPanel = new JPanel();
  JPanel carStatusPanel = new JPanel();

  // Car table
  String[] carColumns = {"Car", "X (m)", "Y (m)", "Speed (m/s)", "Status"};
  DefaultTableModel carTableModel = new DefaultTableModel(carColumns, 0);
  JTable carTable = new JTable(carTableModel);

  // Container for traffic light indicators
  JPanel lightsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));

  // Store light labels for updating
  private ArrayList<JLabel> lightLabels = new ArrayList<>();

  // Constructor
  public TrafficSimGUI() {
    // Main setup
    setTitle("Traffic Simulator");
    setSize(700, 500);
    setLayout(new BorderLayout(5, 5));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // ==================== TOP PANEL ====================
    topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
    timeLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
    topPanel.add(timeLabel);
    add(topPanel, BorderLayout.NORTH);

    // ==================== CENTER PANEL ====================
    centerPanel.setLayout(new BorderLayout(5, 5));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    // Traffic light sub-panel (top of center)
    trafficLightPanel.setLayout(new BorderLayout());
    trafficLightPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.GRAY),
        "Traffic Lights",
        TitledBorder.LEFT,
        TitledBorder.TOP
    ));

    // Add 3 sample traffic lights (1000m apart per requirements)
    lightsContainer.add(createLightIndicator("1000m", "GREEN"));
    lightsContainer.add(createLightIndicator("2000m", "YELLOW"));
    lightsContainer.add(createLightIndicator("3000m", "RED"));
    trafficLightPanel.add(lightsContainer, BorderLayout.CENTER);

    centerPanel.add(trafficLightPanel, BorderLayout.NORTH);

    // Car status sub-panel (bottom of center)
    carStatusPanel.setLayout(new BorderLayout());
    carStatusPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.GRAY),
        "Car Status",
        TitledBorder.LEFT,
        TitledBorder.TOP
    ));

    // Configure table
    carTable.setRowHeight(25);
    carTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
    carStatusPanel.add(new JScrollPane(carTable), BorderLayout.CENTER);

    // Add sample data to table (speeds in m/s)
    carTableModel.addRow(new Object[]{"Car 1", 0, 0, 100, "Waiting"});
    carTableModel.addRow(new Object[]{"Car 2", 0, 0, 150, "Waiting"});
    carTableModel.addRow(new Object[]{"Car 3", 0, 0, 200, "Waiting"});

    centerPanel.add(carStatusPanel, BorderLayout.CENTER);
    add(centerPanel, BorderLayout.CENTER);

    // ==================== BOTTOM PANEL ====================
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

    // Control buttons
    JPanel controlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    controlButtons.add(startButton);
    controlButtons.add(pauseButton);
    controlButtons.add(stopButton);
    controlButtons.add(continueButton);

    // Separator
    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
    separator.setPreferredSize(new Dimension(2, 25));

    // Add buttons
    JPanel addButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    addButtons.add(addCarButton);
    addButtons.add(addIntersectionButton);

    bottomPanel.add(controlButtons);
    bottomPanel.add(separator);
    bottomPanel.add(addButtons);
    add(bottomPanel, BorderLayout.SOUTH);

    // Simulation controls
    new SimulationController(this);
    
    // Show frame
    setVisible(true);
  }

  // Helper method to create a traffic light indicator
  private JPanel createLightIndicator(String position, String color) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setPreferredSize(new Dimension(80, 55));

    // Color text label
    JLabel colorLabel = new JLabel(color, SwingConstants.CENTER);
    colorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
    colorLabel.setOpaque(true);
    colorLabel.setBackground(getColorFromString(color));

    // Save reference for later updates
    lightLabels.add(colorLabel);

    // Position label
    JLabel posLabel = new JLabel(position, SwingConstants.CENTER);
    posLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

    panel.add(colorLabel, BorderLayout.CENTER);
    panel.add(posLabel, BorderLayout.SOUTH);
    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

    return panel;
  }

  // Helper to convert color string to Color object
  private Color getColorFromString(String color) {
    switch (color) {
      case "GREEN": return new Color(34, 139, 34);   // Forest green
      case "YELLOW": return new Color(255, 215, 0); // Gold
      case "RED": return new Color(178, 34, 34);    // Firebrick red
      default: return Color.GRAY;
    }
  }

  // Getter methods for buttons (for adding action listeners later)
  public JButton getStartButton() { return startButton; }
  public JButton getPauseButton() { return pauseButton; }
  public JButton getStopButton() { return stopButton; }
  public JButton getContinueButton() { return continueButton; }
  public JButton getAddCarButton() { return addCarButton; }
  public JButton getAddIntersectionButton() { return addIntersectionButton; }

  // Getter for table model (for updating car data)
  public DefaultTableModel getCarTableModel() { return carTableModel; }

  // Getter for lights container (for adding new intersections)
  public JPanel getLightsContainer() { return lightsContainer; }

  // Method to update time display
  public void updateTime(String time) {
    timeLabel.setText("Time: " + time);
  }

  // Method to update a traffic light's color
  public void updateTrafficLight(int index, String color) {
    if (index >= 0 && index < lightLabels.size()) {
      JLabel label = lightLabels.get(index);
      label.setText(color);
      label.setBackground(getColorFromString(color));
    }
  }

  // Method to add a new traffic light indicator
  public void addTrafficLight(String position, String color) {
    lightsContainer.add(createLightIndicator(position, color));
    lightsContainer.revalidate();
    lightsContainer.repaint();
  }

  public static void main(String[] args) {
    // Create the frame on the event dispatching thread
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new TrafficSimGUI();
      }
    });
  }
}
