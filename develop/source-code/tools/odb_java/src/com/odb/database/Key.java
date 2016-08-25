package com.odb.database;

/**
 * Created by olinchy on 5/19/14.
 */
public class Key implements Comparable<Key>
{
    private Object v;

    public Key(Object v)
    {
        this.v = v;
    }

    public static Key key(Object o)
    {
        return new Key(o);
    }

    @Override
    public int hashCode()
    {
        return v != null ? v.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Key)
        {
            return v.equals(((Key) o).v);
        }
        else
        {
            return false;
        }

    }

    @Override public String toString()
    {
        return this.v.toString();
    }

    @Override public int compareTo(Key o)
    {
        if (this.v instanceof Comparable)
        {
            return ((Comparable) this.v).compareTo(o.v);
        }
        return 0;
    }
}
