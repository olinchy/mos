package com.zte.mos.msg.framework;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.operation.*;

/**
 * Created by luoqingkai on 15-5-7.
 */
public interface ISyncSender
{
    //ConnectResponse connect() throws MsgFrameException;
    PingResponse ping() throws NotConnectException;

    CreateResponse create(Create createOperation) throws MsgFrameException;

    UpdateResponse update(Update updateOperation) throws MsgFrameException;

    DeleteResponse delete(Delete deleteOperation) throws MsgFrameException;

    AckResponse commit(Commit commit) throws NotConnectException;

    AckResponse rollback(Rollback rollback) throws NotConnectException;

    GetResponse get(Get getOperation) throws NotConnectException;

    ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation) throws NotConnectException;

    ActionResponse act(Action actOperation);

    ManagementObject getMo(Get getOperation) throws MsgFrameException;
}
