package com.zte.mos.msg.impl;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.msg.framework.Protocol;
import com.zte.mos.msg.framework.protocol.IProtocolService;


public class UTProtocolService implements IProtocolService {
    private Protocol[] protocolArray;

    public void setProtocolArray(Protocol[] protocolArray) {
        this.protocolArray = protocolArray;
    }


    @Override
    public Protocol[] getProtocols(IMosHead mosHeader, String dn)
    {
        return new Protocol[0];
    }
}
