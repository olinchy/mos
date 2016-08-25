package com.zte.mos.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olinchy on 15-10-14.
 */
public class RangeExp
{
    // [[1,3],[4],[5,6]]
    public RangeExp(String rangeExp)
    {
        if (rangeExp == null || rangeExp.equals(""))
            return;

        rangeExp = rangeExp.replace(" ", "");
        Pattern pattern = Pattern.compile("\\[((-[\\d]+)|([\\d]+))(,)((-[\\d]+)|([\\d]+))\\]");
        Matcher matcher = pattern.matcher(rangeExp);

        String value = matcher.replaceAll("$1-$5");

        pattern = Pattern.compile("\\[((-[\\d]+)|([\\d]+))\\]");
        matcher = pattern.matcher(value);
        value = matcher.replaceAll("$1");

        StringBuilder builder = new StringBuilder(value);

        builder.deleteCharAt(0);
        builder.deleteCharAt(builder.length() - 1);

        this.array = builder.toString().split(",");
    }

    private String[] array = new String[0];

    public String[] toArray()
    {
        return array;
    }
}
