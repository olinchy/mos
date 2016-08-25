package com.zte.mos.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Iterator;
import java.util.LinkedList;

import static com.zte.mos.util.tools.JsonUtil.newArrayNode;

/**
 * Created by olinchy on 7/25/14 for MO_JAVA.
 */
public class Sorter
{
    public static <T> void asc(T[] toSort, GreaterThan greaterThan)
    {
        for (int i = 0; i < toSort.length - 1; i++)
        {
            for (int j = 0; j < toSort.length - i - 1; j++)
            {
                if (greaterThan.isGreater(toSort[j], toSort[j + 1]))
                {
                    swap(toSort, j, j + 1);
                }
            }
        }
    }

    private static <T> void swap(T[] toSort, int j, int i)
    {
        T temp = toSort[j];
        toSort[j] = toSort[i];
        toSort[i] = temp;
    }

    public static ArrayNode asc(ArrayNode toSort, GreaterThan greaterThan)
    {
        String[] sort = toArray(toSort);
        asc(sort, greaterThan);
        return toNode(sort);
    }

    private static ArrayNode toNode(String[] sort)
    {
        ArrayNode arrayNode = newArrayNode();
        for (String s : sort)
        {
            arrayNode.add(s);
        }
        return arrayNode;
    }

    private static String[] toArray(ArrayNode toSort)
    {
        LinkedList<String> lst = new LinkedList<String>();
        Iterator<JsonNode> it = toSort.elements();
        while (it.hasNext())
        {
            lst.add(it.next().asText());
        }
        return lst.toArray(new String[lst.size()]);
    }
}
