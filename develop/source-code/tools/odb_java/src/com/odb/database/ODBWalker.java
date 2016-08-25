package com.odb.database;

import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 15-7-13.
 */
public interface ODBWalker<T> extends Walker<Key, T>
{
    boolean contains(T data);

    void remove(T data);

    void add(T data) throws MOSException;
}
