import java.util.*;

class Process {
    int pid;    // Process ID
    int arrivalTime;   // Arrival Time
    int burstTime;     // Burst Time
    int completionTime; // Completion Time
    int turnaroundTime; // Turnaround Time
    int waitingTime;    // Waiting Time

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class SJFNonPreemptive {

    public static void main(String[] args) {
        // Hardcoded inputs for processes
        Process[] processes = {
            new Process(1, 0, 8),  // Process ID 1, Arrival Time 0, Burst Time 8
            new Process(2, 1, 9),  // Process ID 2, Arrival Time 1, Burst Time 4
            new Process(3, 2, 4),  // Process ID 3, Arrival Time 2, Burst Time 9
            new Process(4, 3, 5)   // Process ID 4, Arrival Time 3, Burst Time 5
        };

        // Sorting processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        // Compute the finish time, turnaround time, and waiting time
        int currentTime = 0;
        int completedProcesses = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        boolean[] isCompleted = new boolean[processes.length];
        while (completedProcesses < processes.length) {
            int index = -1;
            int minBurstTime = Integer.MAX_VALUE;

            // Find the process with the shortest burst time among the ready processes
            for (int i = 0; i < processes.length; i++) {
                if (!isCompleted[i] && processes[i].arrivalTime <= currentTime && processes[i].burstTime < minBurstTime) {
                    minBurstTime = processes[i].burstTime;
                    index = i;
                }
            }

            // If no process is found (i.e., all processes have arrived), increment time
            if (index == -1) {
                currentTime++;
                continue;
            }

            // Update process with shortest burst time
            Process p = processes[index];
            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            // Mark the process as completed
            isCompleted[index] = true;
            completedProcesses++;
            currentTime = p.completionTime;

            // Accumulate total turnaround time and waiting time
            totalTurnaroundTime += p.turnaroundTime;
            totalWaitingTime += p.waitingTime;
        }

        // Output the results
        System.out.println("PID  Arrival Time  Burst Time  Completion Time  Turnaround Time  Waiting Time");
        for (Process p : processes) {
            System.out.printf("%-4d %-13d %-11d %-16d %-17d %-13d\n", p.pid, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }

        // Calculate average turnaround time and waiting time
        double avgTAT = (double) totalTurnaroundTime / processes.length;
        double avgWT = (double) totalWaitingTime / processes.length;

        System.out.println("\nAverage Turnaround Time: " + avgTAT);
        System.out.println("Average Waiting Time: " + avgWT);
    }
}
