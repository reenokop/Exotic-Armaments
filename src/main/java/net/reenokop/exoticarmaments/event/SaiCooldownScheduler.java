package net.reenokop.exoticarmaments.event;

import java.util.*;

public class SaiCooldownScheduler {
    private static final Map<Integer, List<Runnable>> scheduledTasks = new HashMap<>();
    private static int currentTick = 0;

    public static void tick() {
        currentTick++;
        List<Runnable> tasks = scheduledTasks.remove(currentTick);
        if (tasks != null) {
            for (Runnable task : tasks) {
                task.run();
            }
        }
    }

    public static void schedule(int delayTicks, Runnable task) {
        int targetTick = currentTick + delayTicks;
        scheduledTasks.computeIfAbsent(targetTick, t -> new ArrayList<>()).add(task);
    }
}


