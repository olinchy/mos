package com.zte.mos.msg.impl;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.ISyncSender;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.net.MalformedURLException;

/**
 * Created by luoqingkai on 15-5-8.
 */
public class UTRpcSession implements ISession {
    private TargetAddress address;

    public UTRpcSession(TargetAddress address) {
        this.address = address;
    }

    @Override
    public String protocol() {
        return "RPC";
    }

    @Override
    public void setConnectSwitch(boolean open) {

    }

    @Override
    public void setIp(String ipAddress) throws MalformedURLException {

    }

    @Override
    public boolean mosSupport() {
        return true;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public ISyncSender getSyncSender() {
        return new UTRpcSyncSender();
    }

    @Override
    public void localRollback() {

    }

    @Override
    public void setConfiguration(ISessionConfiguration cfg) throws InvalidUrlException {

    }

}
