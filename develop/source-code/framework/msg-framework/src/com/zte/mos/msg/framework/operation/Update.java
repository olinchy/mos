package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class Update extends WriteOperation
{
    private final ManagementObject old;
    private Mo delta;

    public Update(int timeout, Maybe<Integer> transactionId,
                  ManagementObject mo, Mo delta, ManagementObject old) {
        super(timeout, transactionId, mo);
        this.delta = delta;
        this.old = old;
    }

    public Mo getDelta()
    {
        return delta;
    }

    @Override
    public String getOperation()
    {
        return OperEnum.Update.name();
    }

    public ManagementObject getOld()
    {
        return old;
    }
}
