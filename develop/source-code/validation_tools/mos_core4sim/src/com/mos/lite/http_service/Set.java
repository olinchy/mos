package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mos.lite.Mos;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.util.scaner.ConverterTempStorage;
import com.zte.mos.util.tools.JsonUtil;

import java.lang.reflect.Field;
import java.util.Map;

import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by olinchy on 15-11-26.
 */
public class Set extends Add
{
    public Set(Mos mos)
    {
        super(mos);
    }

    @Override
    public String config(
            JsonNode jsonNode, Maybe<Integer> transId) throws MOSException, NoSuchFieldException, IllegalAccessException
    {

        ManagementObject mo = mos.get(new DN(jsonNode.get("dn").asText()), transId);
        Mo toSet = JsonUtil.toObject(jsonNode.toString(), Mo.class);

        for (Map.Entry<String, Object> fieldToSet : toSet.getMo().entrySet())
        {
            Field field = mo.getClass().getField(fieldToSet.getKey());
            field.set(mo, getInstance(ConverterTempStorage.class).convert(fieldToSet.getValue(), field.getType()));
        }

        mos.set(mo, transId);

        return mo.dn().toString();
    }
}
