package com.zte.mos.util;

/**
 * Created by olinchy on 16-3-22.
 */
public interface LambdaFilter<T>
{
    boolean isAccept(T t);
}
