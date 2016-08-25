package com.zte.mos.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Mo
{
    int tid();

    String escape() default "";
}
