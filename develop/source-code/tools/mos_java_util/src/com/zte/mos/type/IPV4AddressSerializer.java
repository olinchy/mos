package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by olinchy on 4/30/15.
 */
public class IPV4AddressSerializer extends JsonSerializer<IPV4Address>
{
    @Override
    public void serialize(
            IPV4Address ipv4Address, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeString(ipv4Address.toString());
    }
}
