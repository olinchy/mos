package com.zte.mos.storage.triggers;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;

import static com.odb.database.trigger.TriggerTiming.beforeDelete;

/**
 * Created by olinchy on 6/19/14 for MO_JAVA.
 */
public class DeleteChildrenTrigger implements Trigger<ManagementObject>
{
    @Override
    public void activate(final ManagementObject mo, final Odb<ManagementObject> odb)
            throws MOSException
    {
        To.map(mo.lsDN(), new LambdaConverter<Object, String>()
        {
            @Override
            public Object to(String content)
            {
                try
                {
                    odb.remove(new Key(new DN(content)));
                }
                catch (MOSException e)
                {
                }
                return null;
            }
        });
    }

    @Override
    public void register(Odb<ManagementObject> odb)
    {
        odb.regGlobalTrigger(beforeDelete, new DeleteChildrenTrigger());
    }
}
