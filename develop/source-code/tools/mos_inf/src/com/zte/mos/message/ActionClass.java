package com.zte.mos.message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by olinchy on 4/22/15 for ems-mos.
 */
public class ActionClass implements Serializable
{
    public String name;
    public int actionId;
    public ArrayList<AttributeClass> attributes = new ArrayList<AttributeClass>();

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof ActionClass))
            return false;

        ActionClass that = (ActionClass) o;

        return name.equals(that.name);

    }

    @Override public int hashCode()
    {
        return name.hashCode();
    }
}
