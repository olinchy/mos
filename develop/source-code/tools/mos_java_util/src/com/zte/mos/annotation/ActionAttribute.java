package com.zte.mos.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ActionAttribute
{
    String field() default ""; // fieldName

    int attrId();

    Type type();

    boolean mutable() default true;

    long minValue() default 0;

    long maxValue() default Long.MAX_VALUE;

    long defValue() default 0;

    long length() default 0;

    String[] items() default { };

    Class<? extends MoEnum> enums() default MoEnum.class;
}
