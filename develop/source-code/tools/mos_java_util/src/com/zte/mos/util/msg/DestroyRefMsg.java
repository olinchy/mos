package com.zte.mos.util.msg;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

/**
 * Created by olinchy on 15-6-3.
 */
public class DestroyRefMsg extends RefMsg
{
    public DestroyRefMsg(DN to, DN from, Maybe<Integer> transId)
    {
        super(to, from, transId);
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoDestroyRef;
    }


}
