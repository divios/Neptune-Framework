package com.github.divios.neptune_framework.guice.impl;

import com.github.divios.neptune_framework.guice.IModuleScannerService;
import com.google.common.collect.Lists;
import com.google.inject.Module;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.Set;

@Singleton
public class ModuleScannerServiceImpl implements IModuleScannerService {

    private static final Reflections reflection =
            new Reflections("io.github.divios.neptune_framework");

    @Override
    public Module[] getAllModules(String packageName) {
        Reflections reflectionOuter = new Reflections(packageName);

        return Lists.newArrayList(reflection.getSubTypesOf(Module.class),
                        reflectionOuter.getSubTypesOf(Module.class))
                .stream()
                .flatMap(Set::stream)
                .map(this::instantiateClassObject)
                .toArray(Module[]::new);
    }

    @SneakyThrows
    private Module instantiateClassObject(Class<? extends Module> module) {
        return module.getConstructor().newInstance();
    }

}
