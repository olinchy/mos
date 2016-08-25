package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;


public class Get extends MoOperation
{
    private String moClazz;

    public Get(String dn, String moClazz){
        super(dn);
        this.moClazz = moClazz;
    }

    public Get(ManagementObject mo) {
        super(mo);
        moClazz = mo.getClass().getSimpleName();
    }

    public Get(int timeout, Maybe<Integer> transactionId, ManagementObject mo) {
        super(timeout, transactionId, mo);
        moClazz = mo.getClass().getSimpleName();
    }

    @Override
    public String getOperation()
    {
        return OperEnum.Get.name();
    }

    public String getMoClazz() {
        return moClazz;
    }
}
