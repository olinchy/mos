package com.zte.mos.util.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{

    public static final String Tab = "    ";

    public static String add(int length, String string)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            buffer.append(string);
        }

        return buffer.toString();
    }

    public static List<String> absPattern(String target, String regex, int... pos)
    {
        List<String> result = null;

        try
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(target);
            while (matcher.find())
            {
                if (result == null)
                    result = new ArrayList<String>();
                if (pos != null && pos.length>0)
                {
                    result.add(matcher.group(pos[0]));
                }
                else
                {
                    result.add(matcher.group());
                }
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static String getFirstMatch(String target, String regex) throws Exception
    {
        List<String> lst = absPattern(target, regex);
        if (lst != null && lst.size() > 0)
        {
            return lst.get(0);
        }
        else
        {
            throw new Exception("message is " + target + " " + regex);
        }
    }

    public static String[] cutBySymbol(String cutThis, String by)
    {
        String[] cuts = new String[2];
        int index = cutThis.indexOf(by);
        if (index == -1)
        {
            throw new IllegalArgumentException(
                    cutThis + " not contains " + by + "! cannot not cut");
        }

        cuts[0] = cutThis.substring(0, index);
        cuts[1] = cutThis.substring(index + by.length());

        return cuts;
    }

    public static boolean fnmatch(String exp, String toMatch)
    {
        String temp = exp.replace("**", ".{0,}").replace("*", "[^/]+").replace("?", ".");
        return toMatch.matches(temp);
    }

}
