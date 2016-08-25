package com.zte.mos.util;

import java.util.LinkedHashSet;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public class DistinguishedList<T> extends LinkedHashSet<T>
{
    @Override
    public boolean add(T t)
    {
        super.remove(t);
        return super.add(t);
    }
}
