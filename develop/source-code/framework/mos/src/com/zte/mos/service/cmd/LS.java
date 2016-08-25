package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.LsResult;
import com.zte.mos.service.MOS;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;
import com.zte.mos.util.msg.MoLsMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 14-12-1.
 */
class LS implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoLsRequest;
    }

    @Override
    public LsResult execute(MoMsg msg, MOS mos) throws MOSException
    {
        MoLsMsg lsMsg = (MoLsMsg) msg;
        String dn = lsMsg.dn();
        ArrayList<String> lst = new ArrayList<String>();

        LsResult res = new LsResult(msg.getTransactionID(), lst);
        try
        {
            ManagementObject father = mos.getMO(dn, msg.getTransactionID());
            List<String> children = father.lsDN();

            Collections.sort(children, new Comparator<String>()
            {
                @Override
                public int compare(String o1, String o2)
                {
                    return new DN(o1).compareTo(new DN(o2));
                }
            });

            lst.addAll(To.map(children, new LambdaConverter<String, String>()
            {
                @Override
                public String to(String content)
                {
                    return new DN(content).value(-1);
                }
            }));

            res.setResult(0);
        }
        catch (Exception e)
        {
            res.setResult(1);
            res.setEx(e);
        }

        return res;
    }
}
