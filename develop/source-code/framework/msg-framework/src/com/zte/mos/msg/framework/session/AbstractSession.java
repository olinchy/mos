package com.zte.mos.msg.framework.session;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.domain.TargetAddressImpl;
import com.zte.mos.msg.framework.ISyncSender;

import java.net.MalformedURLException;

/**
 * Created by luoqingkai on 15-5-20.
 */
public abstract class AbstractSession implements ISession, ISyncSender {

    protected TargetAddress myAddress;

    @Override
    public abstract void setConnectSwitch(boolean open);

    public AbstractSession(TargetAddress myAddress) {
        this.myAddress = myAddress;
    }

    public void setIp(String ipAddress) throws MalformedURLException {
        myAddress = new TargetAddressImpl(
                myAddress.getTargetID(),
                ipAddress, myAddress.getSystem());
    }

    @Override
    public ISyncSender getSyncSender() {
        return this;
    }

    public TargetAddress getMyAddress() {
        return myAddress;
    }


    protected abstract boolean isSecure();

}
