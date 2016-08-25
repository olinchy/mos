package com.zte.mos.msg.framework;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.SessionFactory;

import java.net.MalformedURLException;
import java.util.HashMap;

import static com.zte.mos.msg.SupportedProtocol.RPC;
import static com.zte.mos.msg.SupportedProtocol.SNMP;
import static com.zte.mos.util.basic.Logger.logger;

class SouthTarget implements ITarget {

    private final TargetAddress address;

    private HashMap<String, ISession> sessionMap = new HashMap<String, ISession>(2, 1);

    public SouthTarget(TargetAddress address) {
        this.address = address;
    }

    private boolean connectState = false;

    @Override
    public String id() {
        return address.getTargetID();
    }

    @Override
    public void setIp(String ipAddress) throws MalformedURLException {
        for (ISession session : sessionMap.values()){
            session.setIp(ipAddress);
        }
    }

    @Override
    public void addSession(ISession session) {
        sessionMap.put(session.protocol(), session);
    }

    private ISyncSender getSender() throws UnsupportedProtocolException {
        String defaultProtocol = address.getMosHead().defaultProtocol().name();
        ISession session = sessionMap.get(defaultProtocol);
        if (session == null) {
            throw new UnsupportedProtocolException(address.getIpAddress());
        }else{
            return session.getSyncSender();
        }
    }

    private ISession getSession(AbstractOperation operation, String protocol) throws UnsupportedProtocolException {
        ISession session = sessionMap.get(protocol);
        if (session == null) {
            throw new UnsupportedProtocolException(operation.getDN());
        }
        return session;
    }

    private String getProtocol() {
        IMosHead mosHeader = address.getMosHead();
        if (mosHeader.msgMode() == MsgMode.Single){
            return mosHeader.defaultProtocol().name();
        }else{
            return "RPC";
        }
    }

    private ISyncSender getOperSender(AbstractOperation operation) throws MsgFrameException {
        checkConnected(operation);
        ISyncSender sender;
        MsgProcess process = MsgProcessPool.getMsgProcess(address, operation);

        if (process != null) {
            ISession session = getSession(operation, process.protocol());
            process.setSession(session);
            sender = process;
        }else{
            ISession session = getSession(operation, getProtocol());
            sender = session.getSyncSender();
        }
        return sender;
    }

    void checkConnected(IOperation op) throws NotConnectException{
        if (!connectState)
        {
            boolean isExist = op == null;
            String operation = isExist ? "" : op.getOperation();
            String dn = isExist ? "" : op.getOperation();
            logger(this.getClass()).error(address.getTargetID() + " fail to check state when " + operation + " " + dn);
            throw new NotConnectException(isExist ? null:op.getDN());
        }
    }

    @Override
    public ISyncSender getSender(MoOperation operation) throws MsgFrameException {

        return getOperSender(operation);
    }

    public ReverseSynResponse reverseSyn(ReverseSyn operation)
            throws MsgFrameException
    {
        ISyncSender sender = this.getOperSender(operation);
        return sender.reverseSyn(operation);
    }

    @Override
    public PingResponse ping()
            throws UnsupportedProtocolException, NotConnectException {
        checkConnected(null);
        ISyncSender sender = this.getSender();
        return sender.ping();
    }

    @Override
    public AckResponse commit(Commit commit) throws MsgFrameException {
        checkConnected(null);
        executeRpcSessionCommit(commit);
        return executeSnmpSessionCommit(commit);
    }

    private AckResponse executeRpcSessionCommit(Commit commit) throws NotConnectException {
        ISession rpcSession = sessionMap.get(RPC.name());
        if (rpcSession == null) return new AckResponse(1);
        return rpcSession.getSyncSender().commit(commit);
    }

    private AckResponse executeSnmpSessionCommit(Commit commit) throws NotConnectException {
        ISession snmpSession = sessionMap.get(SNMP.name());
        if (snmpSession != null){
            return snmpSession.getSyncSender().commit(commit);
        }
        return new AckResponse(1);
    }

    @Override
    public AckResponse rollback(Rollback rollback)  throws MsgFrameException{
        boolean mosRollbacked = false;
        for (ISession s : sessionMap.values()) {
            if (s.mosSupport()) {
                if (!mosRollbacked) {
                    s.getSyncSender().rollback(rollback);
                    mosRollbacked = true;
                }
            } else {
                s.getSyncSender().rollback(rollback);
            }
        }
        return null;
    }

    @Override
    public void setConnectSwitch(boolean open) {
        connectState = open;
        for (ISession s : sessionMap.values()) {
            s.setConnectSwitch(open);
        }
    }

    @Override
    public void unRegister() {
        for (ISession session : this.sessionMap.values()) {
            SessionFactory.unRegister(session);
        }
    }

    @Override
    public void localRollback() {
        for (ISession session : this.sessionMap.values()) {
            session.localRollback();
        }
    }

    @Override
    public void setSessionCfg(ISessionConfiguration cfg, String protocol) throws MsgFrameException {
        ISession session = this.sessionMap.get(protocol);
        if (session != null){
            session.setConfiguration(cfg);
        }
    }

}
