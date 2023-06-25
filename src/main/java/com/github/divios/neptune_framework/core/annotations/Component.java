package com.github.divios.neptune_framework.core.annotations;

import com.google.inject.Singleton;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    String named() default "";

    Class<? extends Annotation> scope() default Singleton.class;

}
