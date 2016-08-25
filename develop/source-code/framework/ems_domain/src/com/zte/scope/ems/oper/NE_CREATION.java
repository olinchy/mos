package com.zte.scope.ems.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.ems.EmsDomain;

/**
 * Created by luoqingkai on 15-8-18.
 * It's responsibility is pick the right bundle for NE
 */
public class NE_CREATION implements IMoOperation {
    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoCreateRequest);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        EmsDomain ems = (EmsDomain)domain;
        BundleDomain bundleDomain = ems.pickBundle();
        return bundleDomain.onMsg(msg);
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
