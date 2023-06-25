package com.github.divios.neptune_framework.core.impl;

import com.github.divios.neptune_framework.bukkit.IBukkitSchedulerFacade;
import com.github.divios.neptune_framework.core.annotations.Component;
import com.github.divios.neptune_framework.core.annotations.Scheduled;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class SchedulerAnnotationDispatcher {

    @Inject
    private Set<Component> components;

    @Inject
    private IBukkitSchedulerFacade bukkitSchedulerFacade;

    @PostConstruct
    private void init() {
        for (var component : components)
            for (var scheduledMethod : findScheduledMethods(component.getClass()))
                processMethods(component, scheduledMethod);
    }

    private List<Method> findScheduledMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Scheduled.class))
                .collect(Collectors.toList());
    }

    private void processMethods(Object instance, Method method) {
        Scheduled scheduledAnnotation = extractAnnotation(method);
        if (scheduledAnnotation.rate() <= 0) return;

        bukkitSchedulerFacade.scheduleAtFixedRate(
                new InvokeRunnable(instance, method),
                scheduledAnnotation.initialDelay(),
                scheduledAnnotation.delayUnit(),
                scheduledAnnotation.rate(),
                scheduledAnnotation.delayUnit()
        );
    }

    private Scheduled extractAnnotation(Method method) {
        return method.getAnnotation(Scheduled.class);
    }

    @AllArgsConstructor
    private static final class InvokeRunnable implements Runnable {

        private final Object object;
        private final Method method;

        @SneakyThrows
        @Override
        public void run() {
            method.invoke(object);
        }
    }

}
