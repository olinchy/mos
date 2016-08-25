package com.zte.mos.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoDnBuilder
{
    MoDnMap[] value();
}
