package com.github.divios.neptune_framework.bukkit;

import com.github.divios.neptune_framework.bukkit.impl.BukkitEventManagerFacadeImpl;
import com.github.divios.neptune_framework.bukkit.impl.BukkitSchedulerFacadeImpl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitConfiguration implements Module {

    @Singleton
    @Provides
    private BukkitScheduler bukkitScheduler() {
        return Bukkit.getScheduler();
    }

    @Singleton
    @Provides
    private PluginManager pluginManager() {
        return Bukkit.getServer().getPluginManager();
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(IBukkitSchedulerFacade.class).to(BukkitSchedulerFacadeImpl.class);
        binder.bind(IBukkitEventManagerFacade.class).to(BukkitEventManagerFacadeImpl.class);
    }

}
