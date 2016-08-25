package com.zte.mos.domain;

import com.zte.mos.exception.MOSException;
import com.zte.mos.service.MOS;

/**
 * Created by olinchy on 15-10-29.
 */
public interface AfterGetter<T, R>
{
    void register(AfterGetters afterGetters);

    R get(T obj, MOS mos) throws MOSException;
}
