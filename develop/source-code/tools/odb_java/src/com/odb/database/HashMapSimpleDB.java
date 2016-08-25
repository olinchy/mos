package com.odb.database;

import com.sleepycat.persist.model.PrimaryKey;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luoqingkai on 15-8-11.
 */
public class HashMapSimpleDB<K, T> implements SimpleDB<K, T> {
    private HashMap<K, T> data = new HashMap<K, T>();

    @Override
    public void put(T t) throws MOSException {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field f : fields){
            Annotation annotation = f.getAnnotation(PrimaryKey.class);
            if (annotation != null){
                try {
                    f.setAccessible(true);
                    Object key = f.get(t);
                    data.put((K)key, t);
                } catch (IllegalAccessException e) {
                    throw new MOSException(e);
                }
            }
        }
    }

    @Override
    public T get(K key) throws BerkeleyDBException {
        return data.get(key);
    }

    @Override
    public void delete(K key) throws BerkeleyDBException {
        data.remove(key);
    }

    @Override
    public List<T> getAll() throws MOSException {
        return new LinkedList<T>(data.values());
    }
}
