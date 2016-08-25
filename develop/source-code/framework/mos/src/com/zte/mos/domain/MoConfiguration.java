package com.zte.mos.domain;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;

/**
 * Created by olinchy on 15-12-2.
 */
public class MoConfiguration
{
    public static void config(MOS mos, Maybe<Integer> transId, MoBehavior behavior) throws MOSException
    {
        try
        {
            behavior.act();
            mos.commit(transId);
        }
        catch (MOSException e)
        {
            mos.rollback(transId);
            throw e;
        }
        catch (Throwable e)
        {
            mos.rollback(transId);
            throw new MOSException(e);
        }
    }

    public static void updateMo(
            final MOS mos, final ManagementObject mo, final Maybe<Integer> transId) throws MOSException
    {
        MoConfiguration.config(mos, transId, new MoBehavior()
        {
            @Override
            public void act() throws MOSException
            {
                mos.updateMO(mo, null, transId);
            }
        });
    }
}
