package com.zte.mos.msg.framework.operation;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class PingResponse extends AbstractResponse {

    private final Future<JsonNode> future;
    private long bornTime = System.currentTimeMillis();
    private final TargetAddress address;

    public PingResponse(Future<JsonNode> future, TargetAddress address) {
        super(0);
        this.future = future;
        this.address = address;
    }

    public long getLiveTime(){
        return System.currentTimeMillis() - this.bornTime;
    }

    public Future<JsonNode> getFuture() {
        return future;
    }

    public TargetAddress getAddress() {
        return address;
    }
}
