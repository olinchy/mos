package com.zte.mos.storage.triggers.groupcreator;

import com.odb.database.Odb;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.storage.triggers.ChildrenCreator;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-3-22.
 */
public class SetGroupCreator implements ChildrenCreator
{
    public SetGroupCreator(ManagementObject mo)
    {
        this.mo = mo;
    }

    private final ManagementObject mo;

    @Override
    public void addTo(Odb<ManagementObject> odb) throws MOSException
    {
        MoMetaClass metaOfChild = mo.meta();
        int count = mo.getClass().getAnnotation(MoSet.class).count() + 1;
        for (int i = 1; i < count; i++)
        {
            try
            {
                ManagementObject child = (ManagementObject) metaOfChild.type.newInstance();
                child.setDn(mo.dn().append(String.valueOf(i)).toString());
                child.setMos(((BaseManagementObject) mo).getMos());
                odb.add(child);
            }
            catch (Exception e)
            {
                logger(this.getClass()).warn(
                        "instance child " + metaOfChild.type.getName() + " failed!", e);
            }
        }
    }
}
