import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimelinePanel extends JPanel {
    private final List<Process> executedProcesses;

    public TimelinePanel(List<Process> executedProcesses) {
        this.executedProcesses = executedProcesses;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 0; // Start x-coordinate for the first process
        int y = 20; // Start y-coordinate for the first process
        int height = 20; // Height of each process bar
        int verticalSpacing = 30; // Space between bars
        int processIndex = 0;

        for (Process sp : executedProcesses) {
            // Get color
            g.setColor(getColor(sp.color));

            // Draw the bar for the process
            int barWidth = sp.burst * 10; // Adjust width scaling factor
            g.fillRect(x, y + (processIndex * verticalSpacing), barWidth, height);

            // Draw the process name inside the bar
            g.setColor(Color.BLACK);
            g.drawString(sp.name, x + 5, y + 15 + (processIndex * verticalSpacing));

            // Update x for next bar
            x += barWidth;
            processIndex++;
        }
    }

    private Color getColor(String colorName) {
        switch (colorName.toLowerCase()) {
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "cyan": return Color.CYAN;
            case "magenta": return Color.MAGENTA;
            case "orange": return Color.ORANGE;
            case "red": return Color.RED;
            case "white": return Color.WHITE;
            case "black": return Color.BLACK;
            case "gray": return Color.GRAY;
            case "light gray": return Color.LIGHT_GRAY;
            case "dark gray": return Color.DARK_GRAY;
            case "green": return Color.GREEN;
            case "pink": return Color.PINK;
            case "lime": return new Color(0, 255, 0); // Custom lime color
            case "gold": return new Color(255, 215, 0); // Custom gold color
            case "purple": return new Color(128, 0, 128); // Custom purple color
            case "turquoise": return new Color(64, 224, 208); // Custom turquoise color
            case "navy": return new Color(0, 0, 128); // Custom navy blue color
            case "maroon": return new Color(128, 0, 0); // Custom maroon color
            case "olive": return new Color(128, 128, 0); // Custom olive color
            case "teal": return new Color(0, 128, 128); // Custom teal color
            case "hot pink": return new Color(255, 105, 180); // Custom hot pink color
            case "peach puff": return new Color(255, 218, 185); // Custom peach puff color
            case "lavender": return new Color(230, 230, 250); // Custom lavender color
            case "deep sky blue": return new Color(0, 191, 255); // Custom deep sky blue color
            default: return Color.BLACK; // Default to black if color not recognized
        }
    }


}
