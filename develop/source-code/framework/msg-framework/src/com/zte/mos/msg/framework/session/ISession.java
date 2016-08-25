package com.zte.mos.msg.framework.session;

import com.zte.mos.msg.framework.ISyncSender;
import com.zte.mos.msg.framework.except.InvalidUrlException;

import java.net.MalformedURLException;

/**
 * Created by luoqingkai on 15-5-5.
 */
public interface ISession {
    String protocol();

    void setConnectSwitch(boolean open);
    void setIp(String ipAddress) throws MalformedURLException;
    boolean mosSupport();
    boolean isConnected();
    ISyncSender getSyncSender();

    void localRollback();

    void setConfiguration(ISessionConfiguration cfg) throws InvalidUrlException;
}
