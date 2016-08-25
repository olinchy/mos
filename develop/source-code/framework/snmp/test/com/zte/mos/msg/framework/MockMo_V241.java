package com.zte.mos.msg.framework;

import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.BaseManagementObject;

import static com.zte.mos.msg.MsgMode.Multi;
import static com.zte.mos.msg.SupportedProtocol.RPC;

/**
 * Created by luoqingkai on 15-12-10.
 */
@Imaged(protocol = RPC, msgMode = Multi)
public class MockMo_V241 extends BaseManagementObject {
}
