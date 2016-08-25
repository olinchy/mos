package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mos.lite.Mos;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

/**
 * Created by olinchy on 15-11-26.
 */
public class Del extends Add
{
    public Del(Mos mos)
    {
        super(mos);
    }

    @Override
    public String config(JsonNode jsonNode, Maybe<Integer> transId) throws MOSException
    {
        DN dn;
        mos.del(dn = new DN(jsonNode.asText()), transId);
        return dn.toString();
    }
}
