package com.zte.mos.msg.framework.svr;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;


public interface RpcListener {

    void process(TargetAddress address, JsonNode moOperList);
}
