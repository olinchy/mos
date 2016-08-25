package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ReferenceClass;

/**
 * Created by olinchy on 15-12-11.
 */
public interface RefType
{
    void doRef(String refDN, ReferenceClass referenceClass) throws MOSException;
}
