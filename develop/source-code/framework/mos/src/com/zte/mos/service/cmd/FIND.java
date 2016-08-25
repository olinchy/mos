package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;
import com.zte.mos.tools.Expression;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.msg.MoFindMsg;

import java.util.ArrayList;

import static com.zte.mos.util.To.map;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 14-12-4.
 * FIND cmd0
 */
class FIND implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoFindRequest;
    }

    @Override
    public Result execute(MoMsg msg, MOS mos) throws MOSException
    {
        ArrayList<Mo> array = new ArrayList<Mo>();
        MoFindMsg findMsg = (MoFindMsg) msg;

        int size = 1;
        if (findMsg.getExp() == null)
        {
            ManagementObject toStart = mos.getMO(msg.dn(), msg.getTransactionID());
            array.add(toStart.toMoClass());
        }
        else
        {
            String exp = findMsg.getExp();
            if (!mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN))
            {
                exp = exp.replace(mos.getRootExternalDN(), "");
            }
            Finder finder =
                    new Finder(new Expression(exp), mos, findMsg);
            size = finder.find(array);
        }

        final MOS mosFin = mos;
        for(Mo mo: array)
        {
            String refKey = !mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN)?
                            mo.getDn().toString().replace(mos.getRootExternalDN(),""): mo.getDn().toString();
            ReferenceObject ro = ReferenceDBInBerkeley.get(mos).get(refKey);
            if(ro != null)
            {
                mo.setAttr("refBys", map(ro.refBy, new LambdaConverter<String, String>()
                {
                    @Override
                    public String to(String refBy)
                    {
                        if (!mosFin.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN) && !refBy.startsWith(
                                mosFin.getRootExternalDN()) && !refBy.startsWith("/Ems"))
                            refBy = new DN(mosFin.getRootExternalDN()).append(refBy).toString();
                        return refBy;
                    }
                }));
            }

        }

        return new FindResult(array, size, findMsg.getTransactionID());
    }
}
