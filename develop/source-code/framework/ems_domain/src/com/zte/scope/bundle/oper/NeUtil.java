package com.zte.scope.bundle.oper;

import com.zte.mos.message.Mo;
import com.zte.mos.util.msg.MoConfigMsg;

/**
 * Created by luoqingkai on 15-7-31.
 */
public class NeUtil
{
    public static Mo getMo(MoConfigMsg msg)
    {
        return msg.getMo();
    }
}
