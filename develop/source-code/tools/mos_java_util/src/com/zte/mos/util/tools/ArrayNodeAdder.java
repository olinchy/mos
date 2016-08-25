package com.zte.mos.util.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by olinchy on 6/13/14 for MO_JAVA.
 */
public class ArrayNodeAdder
{
    public static void add(ArrayNode array, JsonNode addThis)
    {
        if (addThis instanceof ArrayNode)
        {
            array.addAll((ArrayNode) addThis);
        }
        else
        {
            array.add(addThis);
        }

    }
}
