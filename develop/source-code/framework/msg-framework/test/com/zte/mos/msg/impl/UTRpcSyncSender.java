package com.zte.mos.msg.impl;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.msg.framework.ISyncSender;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.*;

/**
 * Created by luoqingkai on 15-5-8.
 */
public class UTRpcSyncSender implements ISyncSender {

    @Override
    public PingResponse ping() {
        return new UTPingResponse(null, null);
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException {
        return null;
    }

    @Override
    public UpdateResponse update(Update updateOperation) {
        return null;
    }

    @Override
    public DeleteResponse delete(Delete deleteOperation) {
        return null;
    }

    @Override
    public AckResponse commit(Commit commit) {
        return null;
    }

    @Override
    public AckResponse rollback(Rollback rollback) {
        return null;
    }

    @Override
    public GetResponse get(Get getOperation) {
        return null;
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation)
    {
        return null;
    }

    @Override
    public ActionResponse act(Action actOperation) {
        return null;
    }

    @Override
    public ManagementObject getMo(Get getOperation) throws MsgFrameException {
        return null;
    }

}
