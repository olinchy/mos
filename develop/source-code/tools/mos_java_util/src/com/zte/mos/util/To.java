package com.zte.mos.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by olinchy on 16-2-19.
 */
public class To
{
    public static <T, C> List<T> map(Collection<C> content, LambdaConverter<T, C> func)
    {
        ArrayList<T> list = new ArrayList<T>();
        for (C c : content)
        {
            list.add(func.to(c));
        }
        return list;
    }

    public static <T> List<T> filter(Collection<T> content, LambdaFilter<T> func)
    {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : content)
        {
            if (func.isAccept(t))
                list.add(t);
        }
        return list;
    }

    public static <T> T reduce(Collection<T> content, LambdaReducer<T> func)
    {
        ArrayList<T> list = new ArrayList<T>(content);
        T result = list.get(list.size() - 1);
        for (int i = list.size() - 2; i >= 0; i--)
        {
            result = func.reduce(result, list.get(i));
        }
        return result;
    }
}
