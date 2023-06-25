package com.github.divios.neptune_framework.bukkit.impl;

import com.github.divios.neptune_framework.bukkit.IBukkitSchedulerFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@Singleton
public class BukkitSchedulerFacadeImpl implements IBukkitSchedulerFacade {

    private static final long TICKS_IN_SECOND = 20;

    @Inject
    private Plugin plugin;

    @Inject
    private BukkitScheduler bukkitScheduler;

    @Override
    public BukkitTask schedule(Runnable task) {
        return bukkitScheduler.runTask(plugin, task);
    }

    @Override
    public BukkitTask scheduleAtFixedRate(Runnable task, long period, TimeUnit timeUnit) {
        long periodTicks = timeUnit.toSeconds(period) * TICKS_IN_SECOND;
        return bukkitScheduler.runTaskTimer(plugin, task, 0L, periodTicks);
    }

    @Override
    public BukkitTask scheduleAtFixedRate(Runnable task, long delay, TimeUnit delayUnit, long period, TimeUnit periodUnit) {
        long delayTicks = delayUnit.toSeconds(delay) * TICKS_IN_SECOND;
        long periodTicks = periodUnit.toSeconds(period) * TICKS_IN_SECOND;

        return bukkitScheduler.runTaskTimer(plugin, task, delayTicks, periodTicks);
    }

    @Override
    public BukkitTask scheduleWithFixedDelay(Runnable task, long delay, TimeUnit timeUnit) {
        long delayTicks = timeUnit.toSeconds(delay) * TICKS_IN_SECOND;
        return bukkitScheduler.runTaskLater(plugin, task, delayTicks);
    }

}
