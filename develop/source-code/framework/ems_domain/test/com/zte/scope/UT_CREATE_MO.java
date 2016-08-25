package com.zte.scope;

import com.mos.UT_MO;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCmdHandler;

/**
 * Created by luoqingkai on 15-8-17.
 */
public class UT_CREATE_MO implements MoCmdHandler {
    @Override
    public MoCmds getCmd() {
        return MoCmds.MoCreateRequest;
    }

    @Override
    public Result<?> execute(MoMsg msg, MOS mos) throws MOSException {
        ManagementObject mo = new UT_MO();
        mo.setDn(msg.dn());
        try{
            mos.createMO(mo, msg.getTransactionID());
            return new Successful<Object>();
        }catch (Exception e){
            return new Failure<Object>(e);
        }

    }
}
