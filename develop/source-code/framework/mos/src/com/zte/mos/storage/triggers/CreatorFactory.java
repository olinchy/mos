package com.zte.mos.storage.triggers;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.storage.triggers.groupcreator.GroupCreatorFactory;
import com.zte.mos.storage.triggers.nongroupcreator.NonGroupCreatorFactory;

/**
 * Created by olinchy on 16-3-22.
 */
public class CreatorFactory
{
    public static ChildrenCreator create(ManagementObject mo)
    {
        if (mo.isGroup())
        {
            return GroupCreatorFactory.create(mo);
        }

        return NonGroupCreatorFactory.create(mo);
    }
}
