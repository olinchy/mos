package com.zte.mos.annotation;

public interface MoEnum<T>
{
    String name();

    String value();

    MoEnum valuesOf(String name);

    MoEnum contentOf(T content);
}
