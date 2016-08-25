package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.message.AckResult;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.tools.JsonUtil;

/**
 * Created by olinchy on 3/18/15 for mosjava.
 */
public abstract class AckExecutor implements Executor
{
    @Override
    public Result postException(Throwable throwable, MosService service)
    {
        return new Failure(throwable);
    }

    @Override
    public Result post(JsonNode result, MosService service)
    {
        return JsonUtil.toObject(result.toString(), AckResult.class);
    }
}
