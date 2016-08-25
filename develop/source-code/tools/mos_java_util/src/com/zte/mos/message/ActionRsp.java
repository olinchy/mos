package com.zte.mos.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.JsonUtil;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by olinchy on 4/22/15 for ems-mos.
 */
public class ActionRsp implements Serializable
{
    public ActionClass meta;
    public LinkedHashMap data = new LinkedHashMap();

    public ActionRsp()
    {
        data.put("result", 0);
    }

    public ActionRsp(ActionClass meta)
    {
        this(meta, null);
    }

    public ActionRsp(ActionClass actionRspMeta, JsonNode mo)
    {
        this.meta = actionRspMeta;
        if (mo != null)
            this.data = JsonUtil.toObject(mo.toString(), LinkedHashMap.class);
    }

    public Object get(String fieldName)
    {
        return data.get(fieldName);
    }

    public ActionClass meta()
    {
        return meta;
    }

    public <T> T to(Class<T> clazz) throws MOSException
    {
        return data == null ? null : JsonUtil.toObject(JsonUtil.toString(data), clazz);
    }
}
