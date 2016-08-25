package com.zte.mos.util;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by olinchy on 5/16/14.
 */
@SuppressWarnings("unchecked")
public class CloneHelper
{
    public static <T> T clone(T t)
    {
        if (t instanceof Clone)
        {
            return ((Clone<T>) t).clone();
        }
        else
        {
            return deeplyCopy(t);
        }
    }

    private static <T> T deeplyCopy(T t)
    {
        T clone = null;
        try
        {
            clone = (T) t.getClass().newInstance();
            for (Field field : t.getClass().getFields())
            {
                boolean access_modified = false;
                if (!field.isAccessible())
                {
                    access_modified = true;
                    field.setAccessible(true);
                }

                if (field.get(t) instanceof Collection)
                {
                    field.set(clone, field.get(t).getClass().newInstance());
                    ((Collection) field.get(clone)).addAll(((Collection) field.get(t)));
                }
                else
                {
                    field.set(clone, field.get(t));
                }

                if (access_modified)
                {
                    field.setAccessible(false);
                }
            }
            if (clone instanceof Collection)
            {
                ((Collection) clone).addAll(((Collection) t));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return clone;
    }
}
