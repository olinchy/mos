package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.*;

import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by olinchy on 15-5-8.
 */
public abstract class FindExecutor extends GetExecutor
{
    @Override
    public Result<Mo> post(JsonNode result, MosService service) throws MOSException
    {
        return toObject(result.toString(), FindResult.class);
    }
}

