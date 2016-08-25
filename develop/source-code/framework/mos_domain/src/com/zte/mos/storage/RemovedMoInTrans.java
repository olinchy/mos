package com.zte.mos.storage;

import com.zte.mos.domain.ManagementObject;

/**
 * Created by luoqingkai on 15-4-6.
 */
public class RemovedMoInTrans {
    public final ManagementObject mo;

    public final long operId;

    public RemovedMoInTrans(ManagementObject mo, long operId) {
        this.mo = mo;
        this.operId = operId;
    }
}
