package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.Connect;
import com.zte.mos.msg.framework.operation.ConnectResponse;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;

/**
 * Created by luoqingkai on 15-5-19.
 */

@Decoder
public class ConnectDecoder implements IDecoder {
    @Override
    public IResponse decode(JsonNode node) {
        int result = node.get("result").asInt();
        JsonNode sNode = node.get("sessionId");
        String sessionId = null;
        if (sNode != null){
            sessionId = sNode.asText();
        }
        ConnectResponse response = new ConnectResponse(result, sessionId);
        return response;
    }

    @Override
    public Class<? extends IOperation> getOperationClazz() {
        return Connect.class;
    }
}
