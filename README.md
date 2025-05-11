# CPU Scheduling Simulator (Java)
A Java program that simulates key CPU scheduling algorithms with performance analysis.

## ✅ Implemented Schedulers
- Non-Preemptive Priority
- Non-Preemptive Shortest Job First (SJF)
- Shortest Remaining Time First (SRTF)
- FCAI Scheduling (Custom, dynamic factor-based)

## 🧾 Input
For each process:
- Name, Arrival Time, Burst Time, Priority, Color

Global:
- Number of Processes
- Quantum Time (used in FCAI)
- Context Switch Time

## 📤 Output
- Execution order for each algorithm
- Waiting & Turnaround Time per process
- Averages for both
- FCAI: dynamic quantum history
- *Optional*: Graphical timeline
