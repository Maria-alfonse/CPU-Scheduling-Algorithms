package org.example;

import java.util.*;

public interface Scheduler {
    void execute(List<Process> processes, int context);
}
