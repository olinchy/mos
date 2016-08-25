package com.zte.mos.util.msg.action;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoAction;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoActionMsg;

import java.util.List;

/**
 * Created by ccy on 5/5/16.
 */
public class ModelSwitchReq extends MoActionMsg
{
    public ModelSwitchReq(
            String dn, Maybe<Integer> transId,
            List<Pair<String, Object>> paras)
    {
        super(dn, transId, MoAction.ModelSwitchReq.name(), paras);
    }
}
