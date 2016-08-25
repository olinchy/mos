package com.mos.lite;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 15-11-19.
 */
public interface MoExecutor
{
    void execute(Odb<ManagementObject> odb) throws MOSException;
}
