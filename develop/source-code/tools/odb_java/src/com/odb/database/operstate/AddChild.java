package com.odb.database.operstate;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public class AddChild<T extends Group> implements ChildrenState<T>
{
    protected final T parent;
    protected final T child;

    public AddChild(T data, T child)
    {
        this.parent = data;
        this.child = child;
    }

    @Override
    public void commitTo(Odb<T> odb) throws MOSException
    {
        odb.addChild(parent, child);
    }

    @Override
    public void merge(T data)
    {
        data.add(child);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if ((o instanceof AddChild) && !o.getClass().equals(this.getClass()))
        {
            return false;
        }

        AddChild addChild = (AddChild) o;

        if (child != null ? !child.equals(addChild.child) : addChild.child != null)
        {
            return false;
        }
        return !(parent != null ? !parent.equals(addChild.parent) : addChild.parent != null);
    }

    @Override
    public int hashCode()
    {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{" +
                "parent=" + parent +
                ", child=" + child +
                '}';
    }
}
