package com.zte.mos.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zte.mos.domain.DN;

import java.io.IOException;

/**
 * Created by olinchy on 5/6/15.
 */
public class DNJsonSerializer extends JsonSerializer<DN>
{
    @Override
    public void serialize(
            DN dn, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeString(dn.toString());
    }
}
