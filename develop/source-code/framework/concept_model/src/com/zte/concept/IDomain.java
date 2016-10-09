package com.zte.concept;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoFindMsg;

/**
 * Created by luoqingkai on 15-7-14.
 */
public interface IDomain {
    Result onMsg(MoMsg msg);
    boolean localSupported(DN dn);
    boolean localOperable(DN dn, MoCmds cmd);
    String getID();
    MOS getMos();
    FindResult broadcast(MoFindMsg msg);
}
