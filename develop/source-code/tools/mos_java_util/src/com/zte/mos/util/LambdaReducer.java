package com.zte.mos.util;

/**
 * Created by olinchy on 16-5-23.
 */
public interface LambdaReducer<T>
{
    T reduce(T x, T y);
}
