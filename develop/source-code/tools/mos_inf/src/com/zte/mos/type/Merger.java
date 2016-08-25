package com.zte.mos.type;

/**
 * Created by olinchy on 15-11-12.
 */
public interface Merger<T>
{
    T add(String... t);

    T del(String... t);
}
