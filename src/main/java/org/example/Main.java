package org.example;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter Round Robin Time Quantum: ");
        int RRTQ = scanner.nextInt();

        System.out.print("Enter context switching time: ");
        int context = scanner.nextInt();

        List<Process> processes = new ArrayList<>();
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] processD = line.split(" ");
                Process process = new Process(processD[0], processD[1] ,Integer.parseInt(processD[2]), Integer.parseInt(processD[3]), Integer.parseInt(processD[4]), Integer.parseInt(processD[5]));
                processes.add(process);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true) {
            System.out.println("Choose Scheduler: ");
            System.out.println("1. Non-Preemptive Priority Scheduling");
            System.out.println("2. Non-Preemptive Shortest Job First (SJF)");
            System.out.println("3. Shortest Remaining Time First (SRTF)");
            System.out.println("4. FCAI Scheduling");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 ->{
                    NPP npp = new NPP();
                    npp.execute(processes, context);}
                case 4->{
                    FCAI fcai = new FCAI();
                    fcai.execute(processes, context);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }


    }
}