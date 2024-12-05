public class Process {
    String name ;
    String color;
    int arrival ;
    int burst;
    int priority;
    int Waiting = 0;
    int turnaround = 0;
    int startTime;   // Start Time
    int completionTime; // Completion Time
    boolean completed = false;
    int q;
    int remainingB = burst;

    public Process(String name, String color, int arrival, int burst, int priority, int q) {
        this.name = name;
        this.color = color;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
        this.q = q;
    }

    public void updateQuantum(int increment) {
        this.q += increment;
    }

}
