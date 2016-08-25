package com.zte.mos.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Target({FIELD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoSet
{
    int count() default 0;
}
