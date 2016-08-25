package com.zte.mos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by olinchy on 2/3/15 for mosjava.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoReference
{
    String type();

    String field();

    Class<? extends MoReferencePolicy> under();

    boolean isMulti() default false;
}
