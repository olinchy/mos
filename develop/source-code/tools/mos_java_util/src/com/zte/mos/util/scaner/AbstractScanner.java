package com.zte.mos.util.scaner;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.Scaner;

import java.util.Set;

/**
 * Created by root on 14-11-5.
 */
public abstract class AbstractScanner<T> implements Scaner<T>
{

    public void scan(Set<Class<T>> classesSet) throws MOSException
    {
        for (Class<T> one : classesSet)
        {
            handle(one);
        }
    }
}
