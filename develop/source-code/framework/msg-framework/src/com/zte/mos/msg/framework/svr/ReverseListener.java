package com.zte.mos.msg.framework.svr;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;

/**
 * Created by luoqingkai on 3/7/16.
 */
public interface ReverseListener {
    void process(TargetAddress target, JsonNode moList, int sn, boolean isContinue);
}
