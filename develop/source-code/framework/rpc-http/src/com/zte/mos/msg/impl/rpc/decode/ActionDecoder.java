package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedHashMap;

/**
 * Created by ccy on 7/21/15.
 */
@Decoder
public class ActionDecoder implements IDecoder
{
    @Override
    public IResponse decode(JsonNode node)
    {
        int result = node.get("result").asInt();
        JsonNode sNode = node.get("mo");
        LinkedHashMap data = new LinkedHashMap();
        if (sNode != null)
            data = JsonUtil.toObject(sNode.toString(), LinkedHashMap.class);
        ActionResponse response = new ActionResponse(result, data);
        return response;
    }

    @Override
    public Class<? extends IOperation> getOperationClazz() {
        return Action.class;
    }
}
