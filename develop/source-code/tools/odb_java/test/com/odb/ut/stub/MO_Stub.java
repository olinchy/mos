package com.odb.ut.stub;

import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

import java.util.Collection;

/**
 * Created by olinchy on 11/12/14 for MO_JAVA.
 */
public class MO_Stub implements Group
{
    public String name;
    public MO_Stub()
    {
    }

    public MO_Stub(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MO_Stub))
        {
            return false;
        }

        MO_Stub mo_stub = (MO_Stub) o;

        return name.equals(mo_stub.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
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

    @Override public void destroy() throws MOSException
    {

    }

}
