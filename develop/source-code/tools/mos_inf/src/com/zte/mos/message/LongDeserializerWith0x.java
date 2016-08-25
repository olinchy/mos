package com.zte.mos.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by olinchy on 15-5-14.
 */
public class LongDeserializerWith0x extends JsonDeserializer<Long>
{
    @Override
    public Long deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException
    {
        String value = jsonParser.getText();
        if (value.startsWith("0x"))
            return Long.parseLong(value.replace("0x", ""), 16);
        return new BigDecimal(value).longValue();
    }
}
