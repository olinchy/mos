package com.zte.mos.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoAttribute
{
    String field() default ""; // fieldName

    int attrId() default 0;

    Type type() default Type.WORD16;

    boolean mutable() default true;

    long minimum() default 0;

    long maximum() default Long.MAX_VALUE;

    long length() default 255;

    String[] items() default {};

    Class<? extends MoEnum> enums() default MoEnum.class;

    Class<? extends FieldToAndFroWithValidator> converter() default FieldToAndFroWithValidator.class;

    String range() default "";
}
