package com.zte.mos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// for OR mapping , a field is a table's field.

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field
{
    String name() default "";
}
