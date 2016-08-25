package com.zte.container.ext.switchmodel.log;

import com.zte.container.MoOperateType;
import com.zte.mos.message.Mo;

/**
 * Created by ccy on 5/20/16.
 */
public class MoOperateLog
{
    public MoOperateLog(Mo mo, MoOperateType type)
    {
        this.mo = mo;
        this.type = type;
    }

    private final Mo mo;

    public MoOperateType getType()
    {
        return type;
    }

    private final MoOperateType type;

    public Mo getMo()
    {
        return mo;
    }
}
