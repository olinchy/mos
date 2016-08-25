package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;
import com.zte.mos.msg.framework.operation.Ping;
import com.zte.mos.msg.framework.operation.PingResponse;

/**
 * Created by luoqingkai on 15-5-21.
 */
@Decoder
public class PingDecoder implements IDecoder {
    @Override
    public IResponse decode(JsonNode node) {
        int result = -1;
        JsonNode resultNode = node.get("result");
        if (resultNode != null){
            result = resultNode.asInt();
        }
        return new PingResponse(null, null);
    }

    @Override
    public Class<? extends IOperation> getOperationClazz() {
        return Ping.class;
    }
}
