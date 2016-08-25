package com.zte.mos.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoAction
{
    String name() default "";

    Class<?>[] moClass();
}
