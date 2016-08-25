package com.zte.mos.msg.framework.protocol;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.msg.framework.Protocol;

/**
 * Created by luoqingkai on 15-9-24.
 */
public interface IProtocolService {
    Protocol[] getProtocols(IMosHead mosHeader, String dn);
}
