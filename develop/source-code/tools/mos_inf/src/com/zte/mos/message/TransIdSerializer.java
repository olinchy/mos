package com.zte.mos.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zte.mos.inf.Maybe;

import java.io.IOException;

/**
 * Created by olinchy on 5/5/15.
 */
public class TransIdSerializer extends JsonSerializer<Maybe<Integer>>
{
    @Override
    public void serialize(
            Maybe<Integer> maybe, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException
    {
        if (maybe.nothing())
        {
            jsonGenerator.writeString("{\"present\": false}");
        }
        else
        {
            jsonGenerator.writeNumber(maybe.getValue());
        }

    }
}
