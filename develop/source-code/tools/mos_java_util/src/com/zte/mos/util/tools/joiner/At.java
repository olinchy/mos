package com.zte.mos.util.tools.joiner;

import com.zte.mos.type.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by olinchy on 16-2-24.
 */
public class At
{
    public At(Pair<String, String> fieldNames)
    {
        this.fieldNames = fieldNames;
    }

    private final Pair<String, String> fieldNames;
    private List<BoolChecker> checkers = new ArrayList<BoolChecker>();

    public <T> boolean match(HashMap<String, T> source, HashMap<String, T> target)
    {
        boolean test = source.get(fieldNames.first()).equals(target.get(fieldNames.second()));
        for (BoolChecker checker : checkers)
        {
            test = checker.mark(test, source, target);
        }
        return test;
    }

    public static At at(Pair<String, String> fieldNames)
    {
        return new At(fieldNames);
    }

    public At and(Pair<String, String> and)
    {
        this.checkers.add(new And(new At(and)));
        return this;
    }

    public void or(Pair<String, String> or)
    {
        this.checkers.add(new Or(new At(or)));
    }
}
