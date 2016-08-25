package com.zte.mos.msg.framework;

import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.net.MalformedURLException;

/**
 * Created by luoqingkai on 15-9-24.
 */
public interface ITarget {
    String id();

    void setIp(String ipAddress) throws MalformedURLException;

    void addSession(ISession session);

    ReverseSynResponse reverseSyn(ReverseSyn operation) throws MsgFrameException;

    ISyncSender getSender(MoOperation operation) throws MsgFrameException;

    AckResponse commit(Commit commit) throws MsgFrameException;

    AckResponse rollback(Rollback rollback) throws MsgFrameException;

    void setConnectSwitch(boolean open);

    PingResponse ping() throws UnsupportedProtocolException, NotConnectException;

    void unRegister();

    void localRollback();

    void setSessionCfg(ISessionConfiguration cfg, String protocol) throws MsgFrameException;
}
