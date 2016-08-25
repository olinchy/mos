package com.zte.mos.storage.triggers;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

import static com.odb.database.trigger.TriggerTiming.afterAdd;

/**
 * Created by olinchy on 8/6/14 for MO_JAVA.
 */
public class AddToParent extends UpdateRelationWithParent
{

    @Override
    protected void deal(ManagementObject parent, ManagementObject child
            , Odb<ManagementObject> odb) throws MOSException
    {
        odb.addChild(parent, child);
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(afterAdd, new AddToParent());
    }
}
