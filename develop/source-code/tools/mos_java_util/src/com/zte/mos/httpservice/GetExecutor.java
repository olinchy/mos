package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.*;

import java.util.ArrayList;
import java.util.Iterator;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by olinchy on 3/18/15 for mosjava.
 */
public abstract class GetExecutor implements Executor<Mo>
{
    @Override
    public Result<Mo> post(JsonNode result, MosService service) throws MOSException
    {
        if (result.elements().hasNext())
        {
            return toResult(result);
        }
        return new Failure<Mo>();
    }

    private Result<Mo> toResult(JsonNode result)
    {
        ArrayList<Mo> lst = new ArrayList<Mo>();
        Iterator<JsonNode> it = result.elements();
        Maybe<Integer> transId = null;
        while (it.hasNext())
        {
            FindResult res = toObject(it.next().toString(), FindResult.class);
            if (transId == null)
                transId = res.getTransId();
            if (res.getMo().size() > 0)
                lst.add(res.getMo().get(0));
        }
        return new Successful<Mo>(lst, transId);
    }

    @Override
    public Result postException(Throwable throwable, MosService service)
    {
        logger(this.getClass()).error("failed to execute", throwable);
        return new Failure(throwable);
    }
}
