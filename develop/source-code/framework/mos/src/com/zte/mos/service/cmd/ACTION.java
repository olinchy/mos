package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoActionMsg;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zte on 15-1-6.
 */
public class ACTION implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoAction;
    }

    @Override
    public Result execute(MoMsg msg, MOS mos) throws MOSException
    {
        MoActionMsg actionMsg = (MoActionMsg) msg;
        logger(ACTION.class).debug(mos.getRootExternalDN() + " execute action " + actionMsg);

        String dn = msg.dn();
        ManagementObject mo = mos.getMO(dn, msg.getTransactionID());

        ActionRsp rsp = mo.act(mos, actionMsg);

        return new Successful<ActionRsp>(rsp);
    }
}
 /*
    *   MoActionMsg msg = new MoActionMsg(dn, actionName, new Pair<String, Object>[]{pair("loopbackinterval", 10)});
    *   Result<ActionRsp> res = new DeliverService(APP_NAME).send(msg);
    *   if (res.isSuccess())
    *   {
    *       ActionRsp rsp = res,getMo().get(0);
    *       rsp.confAttr("aaa");
    *       ...
    *   }
    *
    *   Result<ActionRsp> res = service.act(new DN("/Ems/1"), "allocateNeID")
    *   if (res.isSuccess())
    *   {
    *       ActionRsp rsp = res.getRetObj().get(0);
    *       AllocateNeIDRsp rspModel = rsp.to(AllocateNeIDRsp.class);
    *
    *       String neID = res.getRetObj().get(0).confAttr("neID");
    *       service.startTrans();
    *       service.add(new Mo("Ne").setDN(new DN("/Ems/1/Ne")).append("mw." + neType + "=" + String.valueOf(neID))));
    *       ...
    *       service.commit();
    *   }
    *
    * */

