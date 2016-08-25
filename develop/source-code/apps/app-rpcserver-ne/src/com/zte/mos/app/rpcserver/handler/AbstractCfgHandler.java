package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public abstract class AbstractCfgHandler
{
    public AbstractCfgHandler(Invoker invoker)
    {
        this.invoker = invoker;
    }

    //    {
    //        "method":"add",
    //            "params":{
    //        "dn":"/dn/dn/dn",
    //                "mo":{
    //
    //        },
    //        "transId":{
    //            "present": true,
    //                    "value": 0
    //        }
    //    },
    //        "returns": {
    //        "result" : 0,
    //                "transId" : 0
    //    }
    //    },
    private final Invoker invoker;

    public Result handleTransaction(String dn, JsonNode transId, JsonNode... moes)
            throws MOSException
    {
        return invoker.sendCfg(getCmd(), dn, getMo(moes), transId);
    }

    protected abstract MoCmds getCmd();

    private Mo getMo(JsonNode[] moes)
    {
        Mo mo;
        if (moes == null || moes.length == 0)
        {
            mo = null;
        }
        else
        {
            mo = JsonUtil.toObject(moes[0].toString(), Mo.class);
        }
        return mo;
    }

    public void indicate(String dn, JsonNode... moes) throws MOSException
    {
        invoker.sendIndicate(getCmd(), dn, getMo(moes));
    }

    public Pair<String, JsonNode>[] parseMoList(JsonNode moList)
    {
        ArrayNode array = (ArrayNode) moList;
        Pair<String, JsonNode>[] pairArray = new Pair[array.size()];

        for (int i = 0; i < array.size(); i++)
        {
            JsonNode oneNode = array.get(i);
            String dnStr = oneNode.get("dn").asText();
            pairArray[i] = new Pair<String, JsonNode>(dnStr, oneNode);
        }

        return pairArray;
    }
}
