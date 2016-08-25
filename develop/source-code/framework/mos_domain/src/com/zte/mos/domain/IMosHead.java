package com.zte.mos.domain;

import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;


public interface IMosHead {

    float version();

    SupportedProtocol defaultProtocol();

    MsgMode msgMode();
}
