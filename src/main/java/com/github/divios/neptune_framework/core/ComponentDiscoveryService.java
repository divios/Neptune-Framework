package com.github.divios.neptune_framework.core;

import com.github.divios.neptune_framework.core.annotations.Component;
import com.google.common.collect.Sets;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;

import java.util.*;

/**
 * Responsible for discovering components in a specified package path and
 * binding them to their corresponding interfaces using Guice.
 */
public class ComponentDiscoveryService {

    /**
     * The package path to scan for components.
     */
    private final String packagePath;

    /**
     * The Reflections instance used for component discovery.
     */
    private final Reflections reflections;


    public ComponentDiscoveryService(String packagePath) {
        this.packagePath = packagePath;
        this.reflections = new Reflections(packagePath);
    }

    /**
     * Returns a Guice Module representing the component bindings.
     *
     * @return The Guice Module representing the component bindings.
     */
    public Module asModule() {
        return this::bind;
    }

    /**
     * Binds the discovered components to their corresponding interfaces using the provided Binder.
     *
     * @param binder The Guice Binder used for binding components.
     */
    public void bind(Binder binder) {
        Multibinder<Component> componentMultibinder = Multibinder.newSetBinder(binder, Component.class);
        var interfacesMap = new HashMap<Class, Set<Class<?>>>();

        for (var component : discoverComponents()) {
            recursiveAddInterfaces(component, component, interfacesMap);
            componentMultibinder.addBinding().to((Class<? extends Component>) component);        // add to Component binder
        }

        bindInterfacesToImplementations(binder, interfacesMap);
    }

    /**
     * Recursively adds the interfaces of a class and its superinterfaces to the interfaces map.
     *
     * @param clazz         The class to process.
     * @param upper         The current upper-level interface or class.
     * @param interfacesMap The map to store the interface-to-implementation mappings.
     */
    private void recursiveAddInterfaces(Class<?> clazz, Class<?> upper, Map<Class, Set<Class<?>>> interfacesMap) {
        interfacesMap.compute(upper, (k, v) -> mergeComponentIntoMap(v, clazz));

        for (var interfaze : upper.getInterfaces()) {
            recursiveAddInterfaces(clazz, interfaze, interfacesMap);
        }
    }

    /**
     * Merges a component class into the interfaces map.
     *
     * @param v         The current set of implementations for an interface.
     * @param component The component class to add to the set.
     * @return The updated set of implementations.
     */
    private Set<Class<?>> mergeComponentIntoMap(Set<Class<?>> v, Class<?> component) {
        if (v == null)
            return Sets.newHashSet(component);

        v.add(component);
        return v;
    }

    /**
     * Binds interfaces to their implementations using the provided Binder and interfaces map.
     *
     * @param binder        The Guice Binder used for binding.
     * @param interfacesMap The map containing the interface-to-implementation mappings.
     */
    private static void bindInterfacesToImplementations(Binder binder, HashMap<Class, Set<Class<?>>> interfacesMap) {
        interfacesMap.forEach((interfaze, implementations) -> {
            if (implementations.size() == 1) {
                var implementation = implementations.iterator().next();

                if (implementation == interfaze)
                    binder.bind(implementation).in(Singleton.class);
                else
                    binder.bind(interfaze).to(implementation).in(Singleton.class);

            } else {
                Multibinder multibinder = Multibinder.newSetBinder(binder, interfaze);

                for (Class<?> implementation : implementations)
                    multibinder.addBinding().to(implementation).in(Singleton.class);
            }
        });
    }

    /**
     * Discovers and returns the set of classes annotated with @Component.
     *
     * @return The set of classes annotated with @Component.
     */
    private Set<Class<?>> discoverComponents() {
        return reflections.getTypesAnnotatedWith(Component.class);
    }

}
