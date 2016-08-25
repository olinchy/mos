package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mos.lite.Mos;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.util.tools.JsonUtil;

import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by olinchy on 15-11-26.
 */
public class Add implements Config
{
    public Add(Mos mos)
    {
        this.mos = mos;
    }

    protected Mos mos;

    @Override
    public String config(
            JsonNode jsonNode, Maybe<Integer> transId) throws MOSException, NoSuchFieldException, IllegalAccessException
    {
        ManagementObject mo;
        mos.add(mo = toMo(jsonNode, transId), transId);

        return mo.dn().toString();
    }

    private ManagementObject toMo(JsonNode jsonNode, Maybe<Integer> transId) throws MOSException
    {
        ManagementObject mo;

        Mo moClass = JsonUtil.toObject(jsonNode.toString(), Mo.class);
        Class<? extends ManagementObject> prototype;

        MoMetaClass meta = mos.getMeta(moClass.getMoClass());
        if (meta == null)
            throw new MOSException("cannot find meta of " + moClass.getMoClass());
        prototype = (Class<? extends ManagementObject>) meta.type;

        mo = moClass.to(prototype);
        mo.setDn(moClass.getDn().toString());

        return mo;
    }
}
