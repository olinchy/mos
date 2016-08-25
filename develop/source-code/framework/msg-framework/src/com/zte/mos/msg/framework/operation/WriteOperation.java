package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-11-27.
 */
public abstract class WriteOperation extends MoOperation
{

    private int revision;
    public WriteOperation(int timeout, Maybe<Integer> transactionId, ManagementObject mo) {
        super(timeout, transactionId, mo);
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
}
