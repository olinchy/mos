package com.zte.mos.util.msg;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

/**
 * Created by olinchy on 9/28/14 for MO_JAVA.
 */
@SuppressWarnings("ALL")
public class MoGetConfigMsg extends MoFindMsg
{
    private Maybe<Integer> transId;

    public MoGetConfigMsg(Template temp, Maybe<Integer> transId, String dn)
    {
        super("", temp, transId, dn);
    }

    @Override
    public String toString()
    {
        return "MoGetConfigMsg{" +
                "transId=" + transId +
                ", dn='" + dn + '\'' +
                '}';
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoGetConfigRequest;
    }
}
