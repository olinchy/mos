package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-11-12.
 */
public class RpcConnectResponse {
    private final Future<JsonNode> future;

    private long bornTime = System.currentTimeMillis();

    private final RpcSession session;

    public RpcConnectResponse(Future<JsonNode> future, RpcSession session) {
        this.future = future;
        this.session = session;
    }

    public RpcSession getSession() {
        return session;
    }

    public Future<JsonNode> getFuture() {
        return future;
    }

    public long getLiveTime(){
        return System.currentTimeMillis() - this.bornTime;
    }

}
