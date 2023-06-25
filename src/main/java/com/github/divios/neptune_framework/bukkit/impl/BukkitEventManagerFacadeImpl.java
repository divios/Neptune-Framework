package com.github.divios.neptune_framework.bukkit.impl;

import com.github.divios.neptune_framework.bukkit.IBukkitEventManagerFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@Singleton
public class BukkitEventManagerFacadeImpl implements IBukkitEventManagerFacade {

    @Inject
    private PluginManager pluginManager;

    @Inject
    private Plugin plugin;

    @Override
    public void post(Event event) {
        pluginManager.callEvent(event);
    }

    @Override
    public void register(Class<? extends Event> event, EventExecutor executor, EventPriority priority) {
        pluginManager.registerEvent(event, new DummyListener(), priority, executor, plugin);
    }


    @Override
    public void unregister(Listener listener) {
        // pass
    }

    private static final class DummyListener implements Listener {
    }


}
