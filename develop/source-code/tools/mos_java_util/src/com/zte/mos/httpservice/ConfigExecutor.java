package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by olinchy on 3/17/15 for mosjava.
 */
public abstract class ConfigExecutor implements Executor
{
    @Override
    public Result post(JsonNode result, MosService service) throws MOSException
    {
        ConfResultSet res = toObject(result.toString(), ConfResultSet.class);
        Result res1 = service.autoCommit(res);

        return res;
    }

    @Override
    public Result postException(Throwable throwable, MosService service)
    {
        ConfResultSet result = new ConfResultSet();
        result.add(new ConfResult(throwable));

        try
        {
            service.autoCommit(new Failure());
        }
        catch (Throwable throwable1)
        {
            logger(this.getClass()).error("", throwable);
        }
        return result;
    }
}
