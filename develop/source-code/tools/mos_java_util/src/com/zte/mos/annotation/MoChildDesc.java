package com.zte.mos.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by olinchy on 15-9-14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoChildDesc
{
    String className();

    String key();

    String template() default "";
}
