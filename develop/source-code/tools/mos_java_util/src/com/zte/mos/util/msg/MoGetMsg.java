package com.zte.mos.util.msg;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

/**
 * Created by olinchy on 9/28/14 for MO_JAVA.
 */
@SuppressWarnings("ALL")
public class MoGetMsg extends MoFindMsg
{
    private Maybe<Integer> transId;

    public MoGetMsg(Template temp, Maybe<Integer> transId, String dn)
    {
        super("", temp, transId, dn);
    }

    @Override
    public String toString()
    {
        return "MoGetMsg{" +
                "transId=" + transId +
                ", dn='" + dn + '\'' +
                '}';
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoGetRequest;
    }
}
