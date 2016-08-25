package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Result;

/**
 * Created by olinchy on 3/18/15 for mosjava.
 */
public interface Executor<T>
{
    JsonNode exec() throws MOSException;

    Result<T> post(JsonNode result, MosService service) throws MOSException;

    <T> Result<T> postException(Throwable throwable, MosService service);
}
