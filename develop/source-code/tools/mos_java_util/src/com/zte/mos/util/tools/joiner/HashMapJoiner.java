package com.zte.mos.util.tools.joiner;

import com.zte.mos.type.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by olinchy on 16-2-24.
 */
public class HashMapJoiner
{
    public static <T> List<HashMap<String, T>> join(final Select<T> select1, final Select<T> select2, final At at)
    {
        return relateIn2Select(new Relation()
        {
            @Override
            public List<Pair<SelectIn<T>, SelectIn<T>>> deal()
            {
                return select1.match(select2, at);
            }
        });
    }

    public static <T> List<HashMap<String, T>> leftOuterJoin(
            final Select<T> select1, final Select<T> select2, final At at)
    {
        return relateIn2Select(new Relation()
        {
            @Override
            public List<Pair<SelectIn<T>, SelectIn<T>>> deal()
            {
                return select1.join(select2, at);
            }
        });
    }

    private static <T> List<HashMap<String, T>> relateIn2Select(Relation relation)
    {
        List<HashMap<String, T>> join = new ArrayList<HashMap<String, T>>();
        List<Pair<SelectIn<T>, SelectIn<T>>> matches = relation.deal();
        for (Pair<SelectIn<T>, SelectIn<T>> match : matches)
        {
            join.add(match.first().merge(match.second()));
        }
        return join;
    }
}
