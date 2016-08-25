package com.zte.mos.msg.framework;

import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.BaseManagementObject;

import static com.zte.mos.msg.MsgMode.Single;
import static com.zte.mos.msg.SupportedProtocol.SNMP;

/**
 * Created by luoqingkai on 15-12-10.
 */
@Imaged(protocol = SNMP, msgMode = Single)
public class MockMo_V233 extends BaseManagementObject {
}
