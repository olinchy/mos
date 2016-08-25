package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-12-10.
 */
public abstract class MoOperation extends AbstractOperation{
    private final ManagementObject mo;

    public MoOperation(String dn){
        super(dn);
        mo = new NaiveMO(dn);
    }


    public MoOperation(ManagementObject mo) {
        super(mo.dn().toString());
        this.mo = mo;
    }

    public MoOperation(int timeout, Maybe<Integer> transactionId, ManagementObject mo) {
        super(mo.dn().toString(), timeout, transactionId);
        this.mo = mo;
    }

    public ManagementObject getMo()
    {
        return mo;
    }

    @Override
    public String getMoType()
    {
        return mo.getClass().getSimpleName();
    }
}
