package com.zte.mos.persistent;

import com.zte.mos.domain.Group;

import static com.zte.mos.persistent.Log.LogType.*;

/**
 * Created by olinchy on 15-5-18.
 */
public class Log<T>
{
    private final T newValue;
    private final LogType type;
    private final T oldValue;

    public Log(T oldValue, T newValue, LogType type)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.type = type;
    }

    public static <T extends Group> Log<T> add(T t)
    {
        return new Log<T>(null, t, Insert);
    }

    public static <T extends Group> Log<T> set(T oldValue, T newValue)
    {
        return new Log<T>(oldValue, newValue, Update);
    }

    public static <T extends Group> Log<T> del(T t)
    {
        return new Log<T>(t, null, Delete);
    }

    public String type()
    {
        return this.type.name();
    }

    public T newValue()
    {
        return newValue;
    }

    public T oldValue()
    {
        return oldValue;
    }

    public enum LogType
    {
        Insert, Update, Delete
    }
}
