package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.*;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoGetMetaMsg;

import static com.zte.mos.util.tools.JsonUtil.toNode;

/**
 * Created by olinchy on 7/9/14 for MO_JAVA.
 */
public class GetMetaHandler
{
    private final Invoker invoker;

    public GetMetaHandler(Invoker invoker)
    {
        this.invoker = invoker;
    }

    public JsonNode handle(
            String dn, String name, boolean isDnValid, JsonNode transId) throws MOSException
    {
        Result<Result> result = invoker.send(mkMsg(dn, name, isDnValid, transId));

        return toNode(result.getMo().get(0));
    }

    private MoMsg mkMsg(String dn, String name, boolean isDnValid, JsonNode transId)
    {
        Maybe<Integer> transactionId = getTransId(transId);
        MoGetMetaMsg msg = new MoGetMetaMsg(dn, name, isDnValid);
        msg.setTransId(transactionId);
        return msg;
    }

    private Maybe<Integer> getTransId(JsonNode transId)
    {
        boolean present = transId.get("present").asBoolean();
        if (present)
        {
            return new Maybe<Integer>(transId.get("value").asInt());
        }
        else
        {
            return new Maybe<Integer>(null);
        }
    }

}
