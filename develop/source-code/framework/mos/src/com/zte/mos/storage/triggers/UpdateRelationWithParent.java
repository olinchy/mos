package com.zte.mos.storage.triggers;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.storage.NoParentExistException;

/**
 * Created by olinchy on 7/1/14 for MO_JAVA.
 */
public abstract class UpdateRelationWithParent implements Trigger<ManagementObject>
{
    public UpdateRelationWithParent()
    {
    }

    @Override
    public void activate(ManagementObject mo, Odb<ManagementObject> odb) throws MOSException
    {
        if (isRoot(mo))
        {
            return;
        }

        DN parentDN = mo.dn().parent();
        ManagementObject parent = odb.get(new Key(parentDN));
        if (parent != null)
        {
            deal(parent, mo, odb);
        }
        else
        {
            throw new NoParentExistException("parent do not exist! parent dn is " + parentDN.toString());
        }
    }

    protected abstract void deal(ManagementObject lst, ManagementObject child, Odb<ManagementObject> odb) throws
            MOSException;

    private boolean isRoot(ManagementObject managementObject)
    {
        return managementObject.dn().toString().equals("/");
    }
}
