package com.zte.mos.storage.triggers;

import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

import static com.odb.database.trigger.TriggerTiming.afterAdd;

/**
 * Created by luoqingkai on 14-12-5.
 * modified by liuwei at 11-23-2015
 * refactored by liuwei at 3-22-2016
 */
public class AutoAddChildrenTrigger implements Trigger<ManagementObject>
{
    public AutoAddChildrenTrigger()
    {
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(afterAdd, new AutoAddChildrenTrigger());
    }

    @Override
    public void activate(ManagementObject mo, Odb<ManagementObject> odb) throws MOSException
    {
        ChildrenCreator creator = CreatorFactory.create(mo);
        creator.addTo(odb);
    }
}
