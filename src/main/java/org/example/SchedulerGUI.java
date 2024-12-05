package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SchedulerGUI {
    public static void display(List<Process> executedProcesses, int awt, int ata) {
        JFrame frame = new JFrame("CPU Scheduling Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Main panel with timeline
        TimelinePanel timelinePanel = new TimelinePanel(executedProcesses);
        timelinePanel.setPreferredSize(new Dimension(800, 300));

        // Add process information
        String[] columnNames = {"Name", "Arrival", "Burst", "Priority", "Waiting", "Turnaround"};
        String[][] data = new String[executedProcesses.size()][6];
        for (int i = 0; i < executedProcesses.size(); i++) {
            Process p = executedProcesses.get(i);
            data[i] = new String[]{p.name, String.valueOf(p.arrival), String.valueOf(p.burst),
                    String.valueOf(p.priority), String.valueOf(p.Waiting), String.valueOf(p.turnaround)};
        }

        JTable processTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(processTable);

        // Add stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 1));
        statsPanel.add(new JLabel("Average Waiting Time: " + awt));
        statsPanel.add(new JLabel("Average Turnaround Time: " + ata));

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(timelinePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
