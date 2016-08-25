package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.message.Mo;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.Get;
import com.zte.mos.msg.framework.operation.GetResponse;
import com.zte.mos.msg.framework.operation.IOperation;

import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by luoqingkai on 15-9-29.
 */
@Decoder
public class GetDecoder implements IDecoder {

    @Override
    public GetResponse decode(JsonNode node) {
        int result = node.get("result").asInt();
        int transId = 0; //response.get("transId").asInt();
        JsonNode moNode = node.get("mo");
        Mo mo = toObject(moNode.toString(), Mo.class);
        return new GetResponse(result, mo);
    }

    @Override
    public Class<? extends IOperation> getOperationClazz() {
        return Get.class;
    }
}
