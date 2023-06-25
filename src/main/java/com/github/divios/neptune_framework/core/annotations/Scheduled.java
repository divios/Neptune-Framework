package com.github.divios.neptune_framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {

    int initialDelay() default 0;
    TimeUnit delayUnit() default TimeUnit.SECONDS;
    int rate() default -1;  //rate
    TimeUnit rateUnit() default TimeUnit.SECONDS;

}
