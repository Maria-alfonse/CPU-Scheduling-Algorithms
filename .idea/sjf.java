package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJF implements Scheduler {
    private final List<Process> completedProcesses = new ArrayList<>();

    @Override
    public void execute(List<Process> processes, int contextSwitchingTime) {
        processes.sort(Comparator.comparingInt(p -> p.arrival)); // Sort by arrival time

        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Get processes that have arrived by the current time
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrival <= currentTime) {
                    availableProcesses.add(p);
                }
            }

            // If no processes are available, move to the next process arrival time
            if (availableProcesses.isEmpty()) {
                currentTime = processes.get(0).arrival;
                continue;
            }

            // Find the shortest burst time among available processes
            availableProcesses.sort(Comparator.comparingInt(p -> p.burst));
            Process selectedProcess = availableProcesses.get(0);

            // Remove the selected process from the queue
            processes.remove(selectedProcess);

            // Execute the process
            if (selectedProcess.arrival > currentTime) {
                currentTime = selectedProcess.arrival;
            }
            selectedProcess.startTime = currentTime;
            selectedProcess.Waiting = currentTime - selectedProcess.arrival;
            selectedProcess.turnaround = selectedProcess.Waiting + selectedProcess.burst;
            currentTime += selectedProcess.burst + contextSwitchingTime;
            selectedProcess.completionTime = currentTime;

            // Add the completed process to the completed list
            completedProcesses.add(selectedProcess);
        }

        // Display results after scheduling
        showResults();
    }

    private void showResults() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Non-Preemptive SJF Results");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            // Add Gantt Chart
            frame.add(new GanttChartPanel(), BorderLayout.NORTH);

            // Add Table
            JScrollPane tableScrollPane = new JScrollPane(createTable());
            frame.add(tableScrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private JTable createTable() {
        String[] columnNames = {"Name", "Arrival", "Burst", "Priority", "Waiting", "Turnaround"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Process p : completedProcesses) {
            tableModel.addRow(new Object[]{
                    p.name, p.arrival, p.burst, p.priority, p.Waiting, p.turnaround
            });
        }

        return new JTable(tableModel);
    }

    class GanttChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            int startX = 50;
            int startY = 100;
            int barHeight = 30;
            int spacing = 10;
            int currentX = startX;
    
            for (Process p : completedProcesses) { // No error now
                // Set color for the process
                g.setColor(getColorFromString(p.color));
    
                // Draw the process block
                int barWidth = p.burst * 10; // Scale burst time for visualization
                g.fillRect(currentX, startY, barWidth, barHeight);
    
                // Draw the process name
                g.setColor(Color.BLACK);
                g.drawString(p.name, currentX + barWidth / 2 - 10, startY + barHeight / 2);
    
                // Move to the next process block
                currentX += barWidth + spacing;
            }
        }
    
        private Color getColorFromString(String color) {
            return switch (color.toLowerCase()) {
                case "red" -> Color.RED;
                case "blue" -> Color.BLUE;
                case "green" -> Color.GREEN;
                case "yellow" -> Color.YELLOW;
                default -> Color.LIGHT_GRAY;
            };
        }
    }
    
}
