package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;

import static com.zte.mos.util.tools.JsonUtil.*;

/**
 * Created by olinchy on 3/17/15 for mosjava.
 */
public class Serializer
{
    public static JsonNode serialize(Mo... moes) throws MOSException
    {
        ArrayNode array = newArrayNode();
        for (Mo mo : moes)
        {
            array.add(toNode(mo));
        }
        return array;
    }
}
