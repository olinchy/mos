package com.zte.mos.storage.triggers;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 16-3-22.
 */
public interface ChildrenCreator
{
    void addTo(Odb<ManagementObject> odb) throws MOSException;
}
