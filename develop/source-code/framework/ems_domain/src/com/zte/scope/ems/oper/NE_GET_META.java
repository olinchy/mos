package com.zte.scope.ems.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;

/**
 * Created by luoqingkai on 15-8-20.
 */
public class NE_GET_META implements IMoOperation {
    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoGetMetaRequest);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        MOS mos = domain.getMos();
        return MoCommandExecutor.execute(msg, mos);
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
