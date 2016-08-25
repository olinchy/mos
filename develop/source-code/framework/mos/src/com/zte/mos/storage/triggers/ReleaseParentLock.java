package com.zte.mos.storage.triggers;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

import static com.odb.database.trigger.TriggerTiming.afterCommit_add;
import static com.odb.database.trigger.TriggerTiming.afterCommit_remove;

/**
 * Created by olinchy on 7/1/14 for MO_JAVA.
 */
public class ReleaseParentLock implements Trigger<ManagementObject>
{
    @Override
    public void activate(ManagementObject mo, Odb<ManagementObject> odb)
            throws MOSException
    {
        DN parentDN = mo.dn().parent();
        ManagementObject parent = odb.get(new Key(parentDN));
        if (parent != null)
        {
            odb.release(parent, odb);
        }
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(afterCommit_remove, new ReleaseParentLock());
        odb.regGlobalTrigger(afterCommit_add, new ReleaseParentLock());
    }
}
