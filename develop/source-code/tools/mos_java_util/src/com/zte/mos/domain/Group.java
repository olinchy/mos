package com.zte.mos.domain;

import com.zte.mos.exception.MOSException;

import java.util.Collection;

/**
 * Created by olinchy on 8/7/14 for MO_JAVA.
 */
public interface Group<T> extends Destroy
{
    T[] ls() throws MOSException;

    boolean add(T t);

    boolean remove(T t);

    boolean addAll(Collection<? extends T> c);

    boolean contains(Object o);

    boolean isGroup();
}
