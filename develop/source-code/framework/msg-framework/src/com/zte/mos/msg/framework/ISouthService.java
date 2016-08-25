package com.zte.mos.msg.framework;

import com.zte.mos.domain.ConnectionState;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.net.MalformedURLException;

/**
 * Created by luoqingkai on 15-5-5.
 *
 */
public interface ISouthService {

    void register(TargetAddress address)
            throws MsgFrameException;

    void unRegister(String targetId);

    void modifyIp(TargetAddress address) throws MalformedURLException;

    void recover(TargetAddress address) throws UnsupportedProtocolException;

    void setConnectSwitch(String targetId, ConnectionState state) throws UnregisteredException;

    CreateResponse create(Create operation, String targetId) throws MsgFrameException;

    PingResponse ping(String targetId) throws MsgFrameException;

    DeleteResponse delete(Delete operation, String targetId) throws MsgFrameException;

    GetResponse get(Get operation, String targetId) throws MsgFrameException;
    ManagementObject getMo(Get operation, String targetId) throws MsgFrameException;

    ActionResponse action(Action operation, String targetId) throws MsgFrameException;

    UpdateResponse update(Update operation, String targetId) throws MsgFrameException;

    AckResponse commit(Commit operation, String targetId) throws MsgFrameException;

    void rollback(Rollback operation, String targetId) throws MsgFrameException;

    void localRollback(String targetId);

    ReverseSynResponse reverseSync(ReverseSyn operation, String targetId) throws MsgFrameException;

    void setSessionCfg(ISessionConfiguration cfg, String targetId)throws MsgFrameException;

}
