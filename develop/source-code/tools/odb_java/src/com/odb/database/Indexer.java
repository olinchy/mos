package com.odb.database;


import com.zte.mos.exception.MOSException;

import java.util.Comparator;

/**
 * Created by olinchy on 5/16/14.
 */
public interface Indexer<T> extends Comparator<Key>
{
    Key key(T data) throws MOSException;
}
