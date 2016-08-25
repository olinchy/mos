package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.Delete;
import com.zte.mos.msg.framework.operation.DeleteResponse;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;

/**
 * Created by luoqingkai on 15-5-25.
 */
@Decoder
public class DeleteDecoder implements IDecoder {

    @Override
    public IResponse decode(JsonNode node) {
        int result = node.get("result").asInt();
        int transId = node.get("transId").asInt();
        return new DeleteResponse(result,transId);
    }

    @Override
    public Class<? extends IOperation> getOperationClazz() {
        return Delete.class;
    }
}
