package com.zte.mos.domain;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.service.MOS;
import com.zte.mos.type.Pair;

public interface Action<T extends ActionRsp, C>
{
    T on(C mo, MOS mos, Pair<String, Object>... paras) throws MOSException;
}
