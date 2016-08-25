package com.zte.mos.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by ccy on 12/16/15.
 */
public class MacAddressSerializer extends JsonSerializer<MacAddress>
{
    @Override
    public void serialize(
            MacAddress macAddress, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeString(macAddress.toString());
    }
}
