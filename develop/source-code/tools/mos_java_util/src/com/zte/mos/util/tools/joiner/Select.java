package com.zte.mos.util.tools.joiner;

import com.zte.mos.type.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zte.mos.type.Pair.pair;

/**
 * Created by olinchy on 16-2-24.
 */
public class Select<T>
{
    public Select(List<HashMap<String, T>> table, String... toSelect)
    {
        this.table = table;
        this.toSelect = toSelect;
    }

    private final String[] toSelect;
    private final List<HashMap<String, T>> table;

    public List<Pair<SelectIn<T>, SelectIn<T>>> match(Select<T> toMatch, At at)
    {
        List<Pair<SelectIn<T>, SelectIn<T>>> returns = new ArrayList<Pair<SelectIn<T>, SelectIn<T>>>();

        for (HashMap<String, T> local : table)
        {
            for (HashMap<String, T> toMatchRecord : toMatch.table)
            {
                if (at.match(local, toMatchRecord))
                {
                    returns.add(pair(
                            SelectIn.selectIn(local, this.toSelect),
                            SelectIn.selectIn(toMatchRecord, toMatch.toSelect)));
                }
            }
        }
        return returns;
    }

    public List<Pair<SelectIn<T>, SelectIn<T>>> join(Select<T> toMatch, At at)
    {
        List<Pair<SelectIn<T>, SelectIn<T>>> returns = new ArrayList<Pair<SelectIn<T>, SelectIn<T>>>();

        for (HashMap<String, T> local : table)
        {
            SelectIn<T> selectInLeft = SelectIn.selectIn(local, this.toSelect);

            boolean isMatched = false;

            for (HashMap<String, T> toMatchRecord : toMatch.table)
            {
                if (at.match(local, toMatchRecord))
                {
                    returns.add(pair(selectInLeft, SelectIn.selectIn(toMatchRecord, toMatch.toSelect)));
                    isMatched = true;
                    break;
                }
            }
            if (!isMatched)
            {
                returns.add(pair(selectInLeft, SelectIn.createEmptyOne(local, toMatch.toSelect)));
            }
        }
        return returns;
    }

    public static <T> Select<T> select(List<HashMap<String, T>> table, String... toSelect)
    {
        return new Select<T>(table, toSelect);
    }
}
