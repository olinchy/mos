package com.zte.mos.storage.triggers.nongroupcreator;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.storage.triggers.ChildrenCreator;
import com.zte.mos.storage.triggers.nongroupcreator.MoChildrenCreator;

/**
 * Created by olinchy on 16-3-22.
 */
public class NonGroupCreatorFactory
{
    public static ChildrenCreator create(ManagementObject mo)
    {
        return new MoChildrenCreator(mo);
    }
}
