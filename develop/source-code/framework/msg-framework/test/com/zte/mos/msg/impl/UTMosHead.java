package com.zte.mos.msg.impl;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;

/**
 * Created by love on 16-1-14.
 */
public class UTMosHead implements IMosHead {

    private float version;
    private SupportedProtocol protocol;
    private MsgMode msgMode;

    public UTMosHead(float version, SupportedProtocol protocol, MsgMode msgMode) {
        this.version = version;
        this.protocol = protocol;
        this.msgMode = msgMode;
    }

    @Override
    public float version() {
        return version;
    }

    @Override
    public SupportedProtocol defaultProtocol() {
        return protocol;
    }

    @Override
    public MsgMode msgMode() {
        return msgMode;
    }
}
