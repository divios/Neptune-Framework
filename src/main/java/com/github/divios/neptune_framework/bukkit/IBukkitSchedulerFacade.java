package com.github.divios.neptune_framework.bukkit;

import com.github.divios.neptune_framework.bukkit.impl.BukkitSchedulerFacadeImpl;
import com.google.inject.ImplementedBy;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@ImplementedBy(BukkitSchedulerFacadeImpl.class)
public interface IBukkitSchedulerFacade {

    BukkitTask schedule(Runnable task);

    default void scheduleAtFixedRate(Runnable task, long period) {
        scheduleAtFixedRate(task, period, TimeUnit.SECONDS);
    }

    BukkitTask scheduleAtFixedRate(Runnable task, long period, TimeUnit timeUnit);

    default BukkitTask scheduleAtFixedRate(Runnable task, long delay, long period) {
        return scheduleAtFixedRate(task, delay, TimeUnit.SECONDS, period, TimeUnit.SECONDS);
    }

    BukkitTask scheduleAtFixedRate(Runnable task, long delay, TimeUnit delayUnit, long period, TimeUnit periodUnit);

    default void scheduleWithFixedDelay(Runnable task, long delay) {
        scheduleWithFixedDelay(task, delay, TimeUnit.SECONDS);
    }

    BukkitTask scheduleWithFixedDelay(Runnable task, long delay, TimeUnit timeUnit);

}
