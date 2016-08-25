package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

/**
 * Created by olinchy on 15-10-14.
 */
public class RangeDeserializer extends JsonDeserializer<Range>
{
    @Override
    public Range deserialize(
            JsonParser jp,
            DeserializationContext ctxt) throws IOException
    {
        String value;
        if (jp.isExpectedStartArrayToken())
        {
            JsonDeserializer jsonDeserializer = JsonNodeDeserializer.getDeserializer(ArrayNode.class);
            value = jsonDeserializer.deserialize(jp, ctxt).toString();
        }
        else
        {
            value = jp.getText();
        }
        return new Range(new RangeExp(value));
    }
}
