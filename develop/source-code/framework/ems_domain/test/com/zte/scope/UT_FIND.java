package com.zte.scope;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCmdHandler;

/**
 * Created by luoqingkai on 15-8-28.
 */
public class UT_FIND implements MoCmdHandler {
    @Override
    public MoCmds getCmd() {
        return MoCmds.MoFindRequest;
    }

    @Override
    public Result<?> execute(MoMsg msg, MOS mos) throws MOSException {
        return null;
    }
}
