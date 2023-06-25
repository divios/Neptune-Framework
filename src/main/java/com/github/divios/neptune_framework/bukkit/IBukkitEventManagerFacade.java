package com.github.divios.neptune_framework.bukkit;

import com.github.divios.neptune_framework.bukkit.impl.BukkitEventManagerFacadeImpl;
import com.google.inject.ImplementedBy;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

@ImplementedBy(BukkitEventManagerFacadeImpl.class)
public interface IBukkitEventManagerFacade {

    void post(Event event);

    void register(Class<? extends Event> event, EventExecutor executor, EventPriority priority);

    void unregister(Listener listener);

}
