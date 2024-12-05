import java.util.*;

public class FCAI implements Scheduler{
    @Override
    public void execute(List<Process> processes,  int context) {

        int lastArrivalTime = processes.stream().max(Comparator.comparingInt(p -> p.arrival)).get().arrival;
        int maxBurstTime = processes.stream().max(Comparator.comparingInt(p -> p.burst)).get().burst;
        int v1 = (int) Math.ceil((double) lastArrivalTime / 10);
        int v2 = (int) Math.ceil((double) maxBurstTime / 10);

        Queue<Process> readyQueue = new LinkedList<>();
        List<Process> completedProcesses = new ArrayList<>();

        int currentTime = 0;
        while (completedProcesses.size() < processes.size()) {
            for (Process process : processes) {
                if (process.arrival <= currentTime && !readyQueue.contains(process) && !process.completed) {
                    readyQueue.add(process);
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process currentProcess = null;
            double bestFactor = Double.MAX_VALUE;
            for (Process process : readyQueue) {
                double factor = calculateFCAIFactor(process, v1, v2);
                if (factor < bestFactor) {
                    bestFactor = factor;
                    currentProcess = process;
                }
            }

            readyQueue.remove(currentProcess);

            if (currentProcess.startTime == -1) {
                currentProcess.startTime = currentTime;
            }

            int executionTime = (int) Math.ceil(currentProcess.q * 0.4);
            executionTime = Math.min(executionTime, currentProcess.remainingB);

            currentTime += executionTime + context;
            currentProcess.remainingB -= executionTime;

            if (currentProcess.remainingB > 0) {
                if (executionTime < currentProcess.q) {
                    currentProcess.updateQuantum(currentProcess.q - executionTime);
                } else {
                    currentProcess.updateQuantum(2);
                }
                readyQueue.add(currentProcess);
            } else {
                currentProcess.completed = true;
                currentProcess.completionTime = currentTime;
                currentProcess.turnaround = currentProcess.completionTime - currentProcess.arrival;
                currentProcess.Waiting = currentProcess.turnaround - currentProcess.burst;
                completedProcesses.add(currentProcess);
            }

        }
        // Calculate average times
        double avgWaitingTime = completedProcesses.stream().mapToInt(p -> p.Waiting).average().orElse(0);
        double avgTurnaroundTime = completedProcesses.stream().mapToInt(p -> p.turnaround).average().orElse(0);

        // Print results
        System.out.println("\nProcesses execution order:");
        completedProcesses.forEach(p -> System.out.println(p.name));

        System.out.println("\nProcess Details:");
        for (Process p : completedProcesses) {
            System.out.println("Process " + p.name + " -> Waiting Time: " + p.Waiting + ", Turnaround Time: " + p.turnaround);
        }

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
        private static double calculateFCAIFactor (Process process,int v1, int v2){
            return (10 - process.priority) + ((double) process.arrival / v1) + ((double) process.remainingB / v2);
        }

    }