package com.zte.mos.storage.triggers;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

import static com.odb.database.trigger.TriggerTiming.afterDelete;

/**
 * Created by olinchy on 8/6/14 for MO_JAVA.
 */
public class RemoveFromParent extends UpdateRelationWithParent
{
//    public RemoveFromParent(Odb<ManagementObject> odb) {
//        super(odb);
//    }

    @Override
    protected void deal(ManagementObject parent, ManagementObject child
            , Odb<ManagementObject> odb) throws MOSException
    {
        odb.removeChild(parent, child);
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(afterDelete, new RemoveFromParent());
    }
}
