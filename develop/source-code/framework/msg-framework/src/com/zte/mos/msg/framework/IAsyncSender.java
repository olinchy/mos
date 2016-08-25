package com.zte.mos.msg.framework;

import com.zte.mos.msg.framework.operation.*;

import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-5-7.
 */
public interface IAsyncSender {
    Future<ConnectResponse> async_connect(Connect connectOperation);
    Future<PingResponse> async_ping(Ping pingOperation);
    Future<CreateResponse> async_create(Create createOperation);
    Future<UpdateResponse> async_update(Update updateOperation);
    Future<DeleteResponse> async_delete(Delete deleteOperation);
    Future<AckResponse> async_commit(Commit commit);
    Future<AckResponse> async_rollback(Rollback rollback);
    Future<GetResponse> async_get(Get get);
}
