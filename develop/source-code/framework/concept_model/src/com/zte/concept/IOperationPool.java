package com.zte.concept;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.MoCmds;

/**
 * Created by luoqingkai on 15-7-17.
 */
public interface IOperationPool {
    IMoOperation getOperation(DN dn, MoCmds cmd);
}
