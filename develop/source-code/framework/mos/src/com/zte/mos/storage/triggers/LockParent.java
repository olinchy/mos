package com.zte.mos.storage.triggers;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.storage.NoParentExistException;

import static com.odb.database.extended_odb.OperatingSet.OperationSection.RELATION;
import static com.odb.database.trigger.TriggerTiming.beforeAdd;
import static com.odb.database.trigger.TriggerTiming.beforeDelete;

/**
 * Created by olinchy on 8/14/14 for MO_JAVA.
 */
public class LockParent implements Trigger<ManagementObject>
{
    @Override
    public void activate(ManagementObject child, Odb<ManagementObject> odb)
            throws MOSException
    {
        if (child.dn().toString().equals("/"))
        {
            return;
        }
        ManagementObject parent = odb.get(new Key(child.dn().parent()));
        if (parent == null)
        {
            throw new NoParentExistException("parent dn is " + child.dn().parent().toString() + " child mo dn " + child.dn().toString() + "child class type " + child.getClass().getSimpleName());
        }
        odb.acquire(parent, odb, RELATION);
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(beforeDelete, new LockParent());
        odb.regGlobalTrigger(beforeAdd, new LockParent());
    }
}
