package com.zte.mos.domain;

import com.zte.mos.annotation.For;
import com.zte.mos.annotation.Specified;
import com.zte.mos.type.Pair;

import java.util.HashMap;

import static com.zte.mos.type.Pair.pair;

/**
 * Created by olinchy on 16-2-18.
 */
public class MappingHolder<T>
{
    private HashMap<Class<?>, T> map = new HashMap<Class<?>, T>();
    private HashMap<Class<?>, HashMap<Pair<String, String>, T>> map_for =
            new HashMap<Class<?>, HashMap<Pair<String, String>, T>>();

    public void mapping(Class<?> thisClass, T with)
    {
        if (with.getClass().isAnnotationPresent(Specified.class))
        {
            Specified specified = with.getClass().getAnnotation(Specified.class);
            for (For aFor : specified.value())
            {
                Pair<String, String> pair = pair(aFor.modelName(), aFor.modelVersion());
                HashMap<Pair<String, String>, T> entry_for = map_for.get(thisClass);
                if (entry_for == null)
                {
                    entry_for = new HashMap<Pair<String, String>, T>();
                    map_for.put(thisClass, entry_for);
                }
                entry_for.put(pair, with);
            }
        }
        else
        {
            map.put(thisClass, with);
        }
    }

    public <C> T get(Class<C> clazz)
    {
        return map.get(clazz);
    }

    public <C> T get(Class<C> clazz, Pair<String, String> forWhat)
    {
        T t = null;
        if (map_for.get(clazz) != null)
        {
            t = map_for.get(clazz).get(forWhat);
        }
        if (t == null)
        {
            return map.get(clazz);
        }
        return t;
    }
}
