package com.odb.database.extended_odb;

import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 16-3-1.
 */
public interface PostProcess
{
    void post() throws MOSException;
}
