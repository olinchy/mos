package com.zte.container.ext.util;

import com.zte.mos.type.Pair;

/**
 * Created by ccy on 6/14/16.
 */
public class PairComparator
{
    public static boolean isEqual(Pair<String,Object>[] left, Pair<String,Object>[] right)
    {
        if (left == null || right == null)
        {
            return left == right;
        }

        if (left.length == right.length)
        {
            for (Pair<String, Object> pair : left)
            {
                Pair<String, Object> temp = find(pair.first(), right);
                if (temp == null || !temp.second().equals(pair.second()))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static Pair<String, Object> find(String key, Pair<String, Object>[] pairs)
    {
        for(Pair<String, Object> pair : pairs)
        {
            if (pair.first().equalsIgnoreCase(key))
            {
                return pair;
            }
        }
        return null;
    }

}
