package com.github.divios.neptune_framework.guice;

import com.github.divios.neptune_framework.guice.impl.NeptuneApplicationImpl;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public interface NeptuneApplication {

    static NeptuneApplication run(JavaPlugin plugin) {
        return new NeptuneApplicationImpl(plugin);
    }

    void create();

    Injector getInjector();

    void destroy();

}
