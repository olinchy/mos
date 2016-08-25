package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.message.Mo;
import com.zte.mos.util.tools.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by olinchy on 16-4-25.
 */
public class ArrayNodeToMoArray extends JsonDeserializer<Mo[]>
{
    @Override
    public Mo[] deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException
    {
        JsonDeserializer jsonDeserializer = JsonNodeDeserializer.getDeserializer(ArrayNode.class);
        String value = jsonDeserializer.deserialize(jsonParser, deserializationContext).toString();

        JsonNode arrayNode = JsonUtil.toNode(value);

        Iterator<JsonNode> it = arrayNode.elements();

        ArrayList<Mo> list = new ArrayList<Mo>();
        while (it.hasNext())
        {
            JsonNode node = it.next();
            Mo mo = toMo(node);

            list.add(mo);
        }

        return list.toArray(new Mo[list.size()]);
    }

    private Mo toMo(JsonNode node)
    {
        Mo mo = new Mo();
        Iterator<String> it = node.fieldNames();
        while (it.hasNext())
        {
            String fieldName;
            JsonNode child = node.get(fieldName = it.next());
            if (child.isObject())
            {
                mo.setAttr(fieldName, toMo(child));
            }
            else
            {
                mo.setAttr(fieldName, JsonUtil.toObject(child.toString(), Object.class));
            }
        }
        return mo;
    }
}
