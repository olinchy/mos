package com.zte.scope.ems.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoFindMsg;
import com.zte.scope.common.EmsUtil;
import com.zte.scope.ems.EmsDomain;

/**
 * Created by luoqingkai on 15-8-28.
 */
public class NE_FIND implements IMoOperation {
    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoFindRequest);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        MoFindMsg find = (MoFindMsg)msg;
        Result result;
        if (new DNWrapper(msg.dn()).isSizeInOdd())
        {
            if (EmsUtil.isLocal(find)){
                result = MoCommandExecutor.execute(find, domain.getMos());
            }
            else{
                result = domain.broadcast(find);
            }
        }else{
            result = ((EmsDomain)domain).forward(find);
        }
        return result;
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
