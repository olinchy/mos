package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoLsMsg;
import com.zte.mos.inf.MoMsg;

import java.io.IOException;

import static com.zte.mos.util.tools.JsonUtil.toNode;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public class lsHandler extends AbstractQueryHandler
{
    //    {
    //        "method":"ls",
    //            "params":{
    //        "dn":"/dn/dn/dn" ,
    //                "transId": {
    //            "present": true,
    //                    "value": 0
    //        }
    //    },
    //        "returns":{
    //        "children": ["1", "team"],
    //        "result" : 0,
    //        "transId" : 0
    //    }
    //    },
    public lsHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override protected MoMsg mkQueryMsg(String dn, JsonNode transId)
    {
        if (transId.get("present").asText().equals("true"))
        {
            return new MoLsMsg(dn, new Maybe<Integer>(transId.get("value").asInt()));
        }
        else
        {
            return new MoLsMsg(dn);
        }
    }
}
