package com.zte.mos.util.msg.action;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoAction;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoActionMsg;

/**
 * Created by luoqingkai on 15-2-9.
 */
public class ForceReverseSync extends MoActionMsg {

    public ForceReverseSync(String dn, Maybe<Integer> transId) {
        super(dn, transId);
    }

    @Override
    public String actionName() {
        return MoAction.ForceReverseSync.name();
    }

    @Override
    public Pair<String, Object>[] paras() {
        return new Pair[] {};
    }
}
