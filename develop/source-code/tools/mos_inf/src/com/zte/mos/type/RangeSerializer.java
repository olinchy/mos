package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by olinchy on 15-10-14.
 */
public class RangeSerializer extends JsonSerializer<Range>
{
    @Override
    public void serialize(
            Range range, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeString(range.toString());
    }
}
