package com.zte.mos.storage.stub;

import com.zte.mos.annotation.Mo;
import com.zte.mos.annotation.MoAutoCreate;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;

@Mo(tid = 126)
public class Rtua extends Board
{
    @MoChild()
    @MoAutoCreate
    @MoSet(count = 16)
    public GroupOf16E1PortsInRtua ports = new GroupOf16E1PortsInRtua(this, "E1Port");
}
