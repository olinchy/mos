package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * Created by olinchy on 15-11-12.
 */
public class AppendageDeserializer extends JsonDeserializer<Appendage>
{
    @Override
    public Appendage deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException
    {
        JsonDeserializer jsonDeserializer = JsonNodeDeserializer.getDeserializer(ObjectNode.class);
        ObjectNode node = (ObjectNode) jsonDeserializer.deserialize(jsonParser, deserializationContext);
        if (node.get("isAppend").asBoolean())
        {
            return new Append(node.get("content").asText());
        }
        else
        {
            return new Detach(node.get("content").asText());
        }
    }
}
