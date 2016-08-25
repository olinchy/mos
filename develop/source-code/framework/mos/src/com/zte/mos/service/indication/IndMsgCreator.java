package com.zte.mos.service.indication;

import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.IndicateMsg;

/**
 * Created by olinchy on 15-5-18.
 */
public interface IndMsgCreator<T>
{
    IndicateMsg createMsg(Log<T> tLog, String rootExternalDN) throws MOSException;
}
