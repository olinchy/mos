package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.GetMetaResult;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoGetMetaMsg;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-4.
 */
class GET_META implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoGetMetaRequest;
    }

    @Override
    public Result execute(MoMsg msg, MOS mos)
    {

        MoGetMetaMsg metaMsg = (MoGetMetaMsg) msg;
        final String name = metaMsg.getName();
        Maybe<Integer> transId = metaMsg.getTransactionID();
        MoMetaClass metaString = null;
        try
        {
            if (metaMsg.isDnValid)
            {
                ManagementObject mo = mos.getMO(metaMsg.dn(), transId);
                if (mo == null)
                {
                    mo = mos.getMO(new DN(metaMsg.dn()).parent().toString(), transId);
                    metaString = mo.childMeta(new DN(metaMsg.dn()).value(-1));
                    if (metaString == null)
                        // meta of child with name metaMsg.dn()).value(-1) might return null, return mo.meta() instead
                        metaString = mo.meta();
                }
                else
                {
                    metaString = mo.meta();
                }
            }
            else
            {
                metaString = mos.getMeta(name);
            }
        }
        catch (Exception e)
        {
            logger(this.getClass()).warn("get meta caught exception", e);
        }

        return new GetMetaResult(metaString);
    }
}
