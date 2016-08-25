package com.zte.mos.annotation;

/**
 * Created by olinchy on 15-8-14.
 */
public interface FieldToAndFro
{
    Object fro(String fieldValue);

    Object to(Object value);
}
