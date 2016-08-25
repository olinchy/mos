package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.RefMsg;

/**
 * Created by olinchy on 15-6-1.
 */
public class REF_MO implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoRef;
    }

    @Override
    public Result<?> execute(MoMsg msg, MOS mos) throws MOSException
    {
        mos.ref(msg.dn(), ((RefMsg) msg).getFrom(), msg.getTransactionID());
        return new Successful();
    }
}
