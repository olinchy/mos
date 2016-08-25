package com.zte.mos.storage.triggers.groupcreator;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.storage.triggers.ChildrenCreator;

/**
 * Created by olinchy on 16-3-22.
 */
public class NoAutoCreator implements ChildrenCreator
{
    @Override
    public void addTo(Odb<ManagementObject> odb)
    {

    }
}
