package com.zte.scope.ems.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.ems.EmsDomain;
import com.zte.scope.ems.EmsRoutingTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccy on 5/9/16.
 */
public class EMSRouterRegister implements IMoOperation
{
    @Override
    public String mib()
    {
        return ModelTool.buildKey("/Ems/Routing", MoCmds.MoAction);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException
    {
        MoActionMsg actionMsg = (MoActionMsg)msg;

        Map map = toMap((MoActionMsg)msg);
        String neDn = new DN(map.get("dn").toString()).to("Ne").toString();

        if (actionMsg.actionName().equalsIgnoreCase("reg"))
        {
            EmsRoutingTable.service.addRoute(neDn, getBundle(domain, map.get("bundleId").toString()));
        }
        else
        {
            EmsRoutingTable.service.delRoute(neDn);
        }
        return new Successful<ActionRsp>(new ActionRsp());
    }

    private BundleDomain getBundle(IDomain domain, String bundleId)
    {
        EmsDomain emsDomain = (EmsDomain)domain;
        return emsDomain.getBundle(bundleId);
    }

    private Map toMap(MoActionMsg msg)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        for(Pair pair : msg.paras())
        {
            map.put(pair.first().toString(), pair.second());
        }
        return map;
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException
    {

    }
}
