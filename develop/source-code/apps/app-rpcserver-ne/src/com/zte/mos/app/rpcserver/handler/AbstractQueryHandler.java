package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;

import static com.zte.mos.util.tools.JsonUtil.toNode;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public abstract class AbstractQueryHandler
{
    private Invoker invoker;

    public AbstractQueryHandler(Invoker invoker)
    {
        this.invoker = invoker;
    }

    public JsonNode handleQuery(String dn, JsonNode transId) throws MOSException
    {
        Result<Result> result = invoker.send(mkQueryMsg(dn, transId));

        return mkReturn(result.getMo().get(0));
    }

    protected JsonNode mkReturn(Result node) throws MOSException
    {
        return toNode(node);
    }

    protected abstract MoMsg mkQueryMsg(String dn, JsonNode transId);
}
