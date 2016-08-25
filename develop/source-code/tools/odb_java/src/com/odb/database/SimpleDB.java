package com.odb.database;

import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import java.util.List;

/**
 * Created by luoqingkai on 15-8-6.
 */
public interface SimpleDB<K, T> {
    void put(T t) throws MOSException;
    T get(K key) throws BerkeleyDBException;
    void delete(K key) throws BerkeleyDBException;
    List<T> getAll() throws MOSException;
}
