package com.zte.container.ext.operate;

import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.OperationLog;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;

/**
 * Created by ccy on 2/24/16.
 */
public class OperationContext
{
    public OperationContext(String op, OperationLog log, MOS mos, ModelHead indexer, Maybe<Integer> transId)
    {
        this.op = op;
        this.log = log;
        this.mos = mos;
        this.indexer = indexer;
        this.transId = transId;
    }

    private final String op;
    private final OperationLog log;
    private final MOS mos;
    private final ModelHead indexer;
    private final Maybe<Integer> transId;

    public String getOp()
    {
        return op;
    }

    public OperationLog getLog()
    {
        return log;
    }

    public MOS getMos()
    {
        return mos;
    }

    public ModelHead getIndexer()
    {
        return indexer;
    }

    public Maybe<Integer> getTransId()
    {
        return transId;
    }
}
