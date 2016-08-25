package com.zte.mos.storage.stub;

import com.zte.mos.annotation.Mo;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.domain.BaseManagementObject;

@Mo(tid = 101)
public class Board extends BaseManagementObject
{
    @MoAttribute(field = "WBOARDTYPE", enums = BoardTypeEnum.class)
    public BoardTypeEnum boardType = BoardTypeEnum.rtua;
    @MoAttribute(field = "DWSTATUS")
    public long state = 1l;
    @MoAttribute(field = "UCFUNCTION")
    public long function = 0l;
}
