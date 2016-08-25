package com.mos.container;

import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.BaseManagementObject;

import static com.zte.mos.msg.MsgMode.Multi;
import static com.zte.mos.msg.SupportedProtocol.RPC;


@Imaged(protocol = RPC, msgMode = Multi)
public class Mock_NR8250_V242 extends BaseManagementObject {
}
