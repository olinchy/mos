package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.type.Pair;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public class DeleteHandler extends AbstractCfgHandler
{

    public DeleteHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override
    public Pair<String, JsonNode>[] parseMoList(
            JsonNode moList)
    {
        ArrayNode array = (ArrayNode) moList;
        Pair<String, JsonNode>[] pairArray = new Pair[array.size()];

        for (int i = 0; i < array.size(); i++)
        {
            JsonNode oneNode = array.get(i);
            String dnStr = oneNode.asText();
            pairArray[i] = new Pair<String, JsonNode>(dnStr, oneNode);
        }

        return pairArray;
    }

    @Override
    protected MoCmds getCmd()
    {
        return MoCmds.MoDeleteRequest;
    }
}
