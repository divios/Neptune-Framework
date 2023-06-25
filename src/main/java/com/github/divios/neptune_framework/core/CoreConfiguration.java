package com.github.divios.neptune_framework.core;

import com.github.divios.neptune_framework.core.impl.ListenerAnnotationDispatcher;
import com.github.divios.neptune_framework.core.impl.SchedulerAnnotationDispatcher;
import com.google.inject.Binder;
import com.google.inject.Module;

public class CoreConfiguration implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(SchedulerAnnotationDispatcher.class).asEagerSingleton();
        binder.bind(ListenerAnnotationDispatcher.class).asEagerSingleton();
    }

}
