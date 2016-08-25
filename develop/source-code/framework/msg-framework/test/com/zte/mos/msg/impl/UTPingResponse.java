package com.zte.mos.msg.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.operation.PingResponse;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-11-27.
 */
public class UTPingResponse extends PingResponse {

    public UTPingResponse(Future<JsonNode> future, TargetAddress address) {
        super(future, address);
    }
}
