package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.msg.MoGetMsg;
import com.zte.mos.util.msg.Template;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public class GetHandler extends AbstractQueryHandler
{
    public GetHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override
    protected MoMsg mkQueryMsg(String strDN, JsonNode transId)
    {
        Integer id = transId.get("present").asText().equals("true") ? transId.get("value").asInt() : null;
        return createMsg(id, strDN);
    }

    protected MoMsg createMsg(Integer transId, String strDN)
    {
        return new MoGetMsg(new Template(), new Maybe<Integer>(transId), strDN);
    }
}
