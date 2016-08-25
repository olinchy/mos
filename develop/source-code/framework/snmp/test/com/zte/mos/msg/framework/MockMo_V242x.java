package com.zte.mos.msg.framework;

import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.BaseManagementObject;

import static com.zte.mos.msg.MsgMode.Single;
import static com.zte.mos.msg.SupportedProtocol.RPC;

/**
 * Created by luoqingkai on 15-12-1.
 */
@Imaged(protocol = RPC, msgMode = Single)
public class MockMo_V242x extends BaseManagementObject
{
    public Long state = 1000l;
    public Long function = 99999l;
}
