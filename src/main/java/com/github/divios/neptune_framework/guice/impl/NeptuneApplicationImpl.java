package com.github.divios.neptune_framework.guice.impl;

import com.github.divios.neptune_framework.core.ComponentDiscoveryService;
import com.github.divios.neptune_framework.guice.NeptuneApplication;
import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;

@Slf4j
public class NeptuneApplicationImpl implements NeptuneApplication {

    private final JavaPlugin plugin;
    private final String pluginPath;

    private Injector injector;

    public NeptuneApplicationImpl(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginPath = plugin.getClass().getPackageName();
        
        create();
    }

    @SneakyThrows
    @Override
    public void create() {
        injector = LifecycleInjector.builder()
                .usingBasePackages(pluginPath)
                .withModules(new ModuleScannerServiceImpl().getAllModules(pluginPath))    // scans for all modules in project
                .withAdditionalModules(createJavapluginBinder())
                .withAdditionalModules(new ComponentDiscoveryService(pluginPath).asModule())
                .build().createInjector();

        injector.getInstance(LifecycleManager.class).start();
    }

    private com.google.inject.Module createJavapluginBinder() {
        return binder -> binder.bind(JavaPlugin.class).toInstance(plugin);
    }

    @Override
    public Injector getInjector() {
        return injector;
    }

    @Override
    public void destroy() {
        injector.getInstance(LifecycleManager.class).close();
    }

}
