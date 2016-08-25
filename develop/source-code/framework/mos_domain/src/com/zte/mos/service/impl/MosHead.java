package com.zte.mos.service.impl;


import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.IMosHead;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;

public class MosHead implements IMosHead{
    private final Imaged annotation;

    public MosHead(Imaged annotation) {
        this.annotation = annotation;
    }

    @Override
    public float version() {
        return annotation.mosVersion();
    }

    @Override
    public SupportedProtocol defaultProtocol() {
        return annotation.protocol();
    }

    @Override
    public MsgMode msgMode() {
        return annotation.msgMode();
    }
}
