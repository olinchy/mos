package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

/**
 * Created by olinchy on 15-11-26.
 */
public interface Config
{
    String config(
            JsonNode jsonNode,
            Maybe<Integer> transId) throws MOSException, NoSuchFieldException, IllegalAccessException;
}
