package com.github.divios.neptune_framework.core.impl;

import com.github.divios.neptune_framework.bukkit.IBukkitEventManagerFacade;
import com.github.divios.neptune_framework.core.annotations.Component;
import com.github.divios.neptune_framework.core.annotations.EventListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ListenerAnnotationDispatcher {

    @Inject
    private Set<Component> components;

    @Inject
    private IBukkitEventManagerFacade bukkitEventManagerFacade;

    @PostConstruct
    public void init() {
        for (var component : components) {
            for (Method method : findScheduledMethods(component.getClass())) {
                EventListener eventListener = extractAnnotation(method);

                Class<? extends Event> event = eventListener.value();
                EventPriority priority = eventListener.priority();

                bukkitEventManagerFacade.register(event,
                        (listener, event1) -> callListenerMethod(component, method, event1), priority);
            }
        }
    }

    private List<Method> findScheduledMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(EventListener.class))
                .collect(Collectors.toList());
    }

    private EventListener extractAnnotation(Method method) {
        return method.getAnnotation(EventListener.class);
    }

    private static final class DummyListener implements Listener {
    }

    @SneakyThrows
    private void callListenerMethod(Object instance, Method method, Event event) {
        method.invoke(instance, event);
    }

    @SneakyThrows
    private Object createMethodInstance(Method method) {
        return method.getDeclaringClass().getConstructor().newInstance();
    }

}
