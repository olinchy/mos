package com.zte.mos.annotation;

import com.zte.mos.domain.DnCreator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoDn
{
    Class<? extends DnCreator> value();
}
