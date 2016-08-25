package com.zte.mos.storage.triggers.groupcreator;

import com.zte.mos.annotation.MoAutoCreate;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.storage.triggers.ChildrenCreator;
import com.zte.mos.storage.triggers.groupcreator.MapGroupCreator;
import com.zte.mos.storage.triggers.groupcreator.NoAutoCreator;
import com.zte.mos.storage.triggers.groupcreator.SetGroupCreator;

/**
 * Created by olinchy on 16-3-22.
 */
public class GroupCreatorFactory
{
    public static ChildrenCreator create(ManagementObject mo)
    {
        if (mo.getClass().isAnnotationPresent(MoAutoCreate.class))
        {
            MoAutoCreate autoCreate = mo.getClass().getAnnotation(MoAutoCreate.class);
            if (autoCreate.value().length > 0)
            {
                return new MapGroupCreator(mo, autoCreate);
            }
            return new SetGroupCreator(mo);
        }
        return new NoAutoCreator();
    }
}
