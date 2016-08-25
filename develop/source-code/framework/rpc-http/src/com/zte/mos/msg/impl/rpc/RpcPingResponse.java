package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.operation.PingResponse;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 2/19/16.
 */
public class RpcPingResponse extends PingResponse {
    public final RpcSession session;

    public RpcPingResponse(Future<JsonNode> future, TargetAddress address, RpcSession session) {
        super(future, address);
        this.session = session;
    }
}
