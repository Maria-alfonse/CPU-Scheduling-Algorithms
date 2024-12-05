import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class NPP implements Scheduler {

    @Override
    public void execute(List<Process> processes, int context) {
        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;

        // Sort processes by Arrival Time first, then by Priority
        processes.sort(Comparator.comparingInt((Process p) -> p.arrival)
                .thenComparingInt(p -> p.priority));

        // Process execution loop
        List<Process> remainingProcesses = new ArrayList<>(processes);
        List<Process> executedProcesses = new ArrayList<>();

        while (!remainingProcesses.isEmpty()) {
            // Find the next process to execute based on current time and priority
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : remainingProcesses) {
                if (p.arrival <= currentTime) {
                    availableProcesses.add(p);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime = remainingProcesses.getFirst().arrival;
                continue;
            }

            // Sort available processes by priority (and arrival time if needed)
            availableProcesses.sort(Comparator.comparingInt((Process p) -> p.priority)
                    .thenComparingInt(p -> p.arrival));


            Process currentProcess = availableProcesses.getFirst();


            if (currentTime < currentProcess.arrival) {
                currentTime = currentProcess.arrival; // Wait for process to arrive
            }

            currentProcess.startTime = currentTime;
            currentProcess.completionTime = currentTime + currentProcess.burst;
            currentProcess.Waiting = currentTime - currentProcess.arrival;
            currentProcess.turnaround = currentProcess.burst + currentProcess.Waiting;


            totalWaitTime += currentProcess.Waiting;
            totalTurnaroundTime += currentProcess.turnaround;

            currentTime += currentProcess.burst + context;

            remainingProcesses.remove(currentProcess);
            executedProcesses.add(currentProcess);
        }

        print(executedProcesses, totalWaitTime, totalTurnaroundTime);
    }



    private void print(List<Process> processes, int totalWaitTime, int totalTurnaroundTime) {
        int numProcesses = processes.size();
        int avgWaitTime = totalWaitTime / numProcesses;
        int avgTurnaroundTime = totalTurnaroundTime / numProcesses;

        // Pass data to GUI
        SchedulerGUI.display(processes, avgWaitTime, avgTurnaroundTime);
    }


}
