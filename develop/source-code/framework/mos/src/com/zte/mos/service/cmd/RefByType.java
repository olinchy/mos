package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-7-1.
 */
public interface RefByType
{
    void ref(DistinguishedList<String> affectedDN, MOS mos, Log<ManagementObject> log, Maybe<Integer> transId) throws MOSException;
}
