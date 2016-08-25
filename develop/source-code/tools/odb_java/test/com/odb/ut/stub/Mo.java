package com.odb.ut.stub;

import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

import java.util.Collection;

/**
 * Created by olinchy on 5/16/14.
 */
public class Mo implements Group
{
    public String name;
    public String id;

    @SuppressWarnings("unused")
    public Mo()
    {
    }

    public Mo(String name)
    {
        this.name = name;
        this.id = String.valueOf((int) name.toCharArray()[0]);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Mo mo = (Mo) o;

        return id.equals(mo.id) && name.equals(mo.name);

    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override public String toString()
    {
        return "Mo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override public void destroy()
    {

    }

    @Override public Object[] ls() throws MOSException
    {
        return new Object[0];
    }

    @Override public boolean add(Object o)
    {
        return false;
    }

    @Override public boolean remove(Object o)
    {
        return false;
    }

    @Override public boolean addAll(Collection c)
    {
        return false;
    }

    @Override public boolean contains(Object o)
    {
        return false;
    }

    @Override public boolean isGroup()
    {
        return false;
    }
}
