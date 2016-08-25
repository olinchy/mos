package com.zte.scope.ems.oper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.ems.EmsDomain;
import com.zte.scope.ems.EmsRoutingTable;

import java.util.Map;

import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by ccy on 5/9/16.
 */
public class EMSBundlePicker implements IMoOperation
{
    @Override
    public String mib()
    {
        return ModelTool.buildKey("/Ems/Bundles", MoCmds.MoAction);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException
    {
        EmsDomain emsDomain = (EmsDomain)domain;
        BundleDomain bundleDomain = emsDomain.pickBundle();
        ObjectNode res = newObjNode();
        res.put("address", bundleDomain.getAddress());
        return new Successful<ActionRsp>(new ActionRsp(null, res));
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException
    {

    }
}
