package com.zte.mos.util.msg.action;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoAction;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoActionMsg;

/**
 * Created by luoqingkai on 15-2-9.
 */
public class ClearRevision extends MoActionMsg
{
    public ClearRevision(String dn, Maybe<Integer> transId)
    {
        super(dn, transId);
    }

    @Override
    public String actionName()
    {
        return MoAction.ClearRevision.name();
    }

    @Override
    public Pair<String, Object>[] paras()
    {
        return new Pair[]{};
    }
}
