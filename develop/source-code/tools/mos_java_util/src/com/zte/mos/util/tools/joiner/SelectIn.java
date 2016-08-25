package com.zte.mos.util.tools.joiner;

import java.util.HashMap;

/**
 * Created by olinchy on 16-2-24.
 */
public class SelectIn<T>
{
    public SelectIn(HashMap<String, T> in, String... toSelect)
    {
        this.in = in;
        this.toSelect = toSelect;
    }

    private SelectIn(String... toSelect)
    {
        this.in = new HashMap<String, T>();
        this.toSelect = toSelect;
        for (String s : toSelect)
        {
            this.in.put(s, null);
        }
    }

    private final String[] toSelect;
    private HashMap<String, T> in = new HashMap<String, T>();

    public static <T> SelectIn<T> selectIn(HashMap<String, T> in, String... toSelect)
    {
        return new SelectIn<T>(in, toSelect);
    }

    public HashMap<String, T> merge(SelectIn<T> that)
    {
        HashMap<String, T> toSelect = toSelects();
        toSelect.putAll(that.toSelects());
        return toSelect;
    }

    private HashMap<String, T> toSelects()
    {
        HashMap<String, T> map = new HashMap<String, T>();
        for (String s : toSelect)
        {
            if (in.containsKey(s))
                map.put(s, in.get(s));
            else
            {
                if (s.equals("*"))
                {
                    map.putAll(in);
                }
                else
                    throw new IllegalArgumentException(s + " is not in table");
            }
        }
        return map;
    }

    public static <T> SelectIn<T> createEmptyOne(HashMap<String, T> local, String[] toSelect)
    {
        return new SelectIn<T>(toSelect);
    }
}
