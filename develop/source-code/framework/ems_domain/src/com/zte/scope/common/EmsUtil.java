package com.zte.scope.common;

import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.msg.MoFindMsg;

import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by luoqingkai on 15-8-21.
 */
public class EmsUtil
{
    public static boolean isLocal(MoFindMsg findMsg) throws MOSException
    {
        String who = findMsg.getDnRegex();
        return getInstance(MetaStringStore.class).isIn(who) || who.equals("");
    }
}
