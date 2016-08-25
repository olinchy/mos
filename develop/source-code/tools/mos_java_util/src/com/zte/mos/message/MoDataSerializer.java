package com.zte.mos.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by olinchy on 5/6/15.
 */
public class MoDataSerializer extends JsonSerializer<Set<Map.Entry<String, Object>>>
{
    @Override
    public void serialize(
            Set<Map.Entry<String, Object>> entries, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        for (Map.Entry<String, Object> entry : entries)
        {
            jsonGenerator.writeObjectField(entry.getKey(), entry.getValue());
        }
        jsonGenerator.writeEndObject();
    }
}
