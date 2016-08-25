package com.zte.mos.service.cmd.reftypes;

import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.Ref;
import com.zte.mos.util.DistinguishedList;

import java.lang.reflect.Field;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-7-1.
 */
public class Update extends AbstractType
{
    @Override
    protected void doRef(
            DistinguishedList<String> affectedDN, MOS mos, Log<ManagementObject> log,
            Maybe<Integer> transId) throws MOSException
    {
        ManagementObject origin = log.oldValue();
        ManagementObject mo = log.newValue();
        origin.setMos(mos);
        mo.setMos(mos);

        boolean needToDo = false;
        try
        {
            for (Field field : origin.getClass().getFields())
            {
                if (field.isAnnotationPresent(MoReference.class) && (!field.get(origin).equals(field.get(mo))))
                {
                    needToDo = true;
                    break;
                }
            }
        }
        catch (IllegalAccessException e)
        {
            logger(this.getClass()).warn("", e);
        }

        if (needToDo)
            affectedDN.addAll(Ref.set(origin, mo, transId, mos));
    }

    @Override
    protected ManagementObject getValue(Log<ManagementObject> log)
    {
        return log.newValue();
    }
}
