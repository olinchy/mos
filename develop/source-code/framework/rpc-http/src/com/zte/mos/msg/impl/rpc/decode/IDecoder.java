package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;

/**
 * Created by luoqingkai on 15-5-19.
 */
public interface IDecoder {
    IResponse decode(JsonNode node);
    Class<? extends IOperation> getOperationClazz();
}
