package com.zte.mos.msg.impl;

import com.zte.mos.msg.framework.IAsyncSender;
import com.zte.mos.msg.framework.operation.*;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-5-8.
 */
public class UTRpcAsyncSender implements IAsyncSender {

    @Override
    public Future<ConnectResponse> async_connect(Connect connectOperation) {
        return null;
    }

    @Override
    public Future<PingResponse> async_ping(Ping pingOperation) {
        return null;
    }

    @Override
    public Future<CreateResponse> async_create(Create createOperation) {
        return null;
    }

    @Override
    public Future<UpdateResponse> async_update(Update updateOperation) {
        return null;
    }

    @Override
    public Future<DeleteResponse> async_delete(Delete deleteOperation) {
        return null;
    }

    @Override
    public Future<AckResponse> async_commit(Commit commit) {
        return null;
    }

    @Override
    public Future<AckResponse> async_rollback(Rollback rollback) {
        return null;
    }

    @Override
    public Future<GetResponse> async_get(Get get) {
        return null;
    }
}

