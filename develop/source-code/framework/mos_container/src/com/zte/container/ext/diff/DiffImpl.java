package com.zte.container.ext.diff;

import com.zte.container.ext.operate.Operation;
import com.zte.container.ext.operate.OperationContext;
import com.zte.mos.domain.Diff;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.OperationLog;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;
import com.zte.mos.service.SLRouterPool;
import com.zte.mos.util.MinusIDGenerator;
import com.zte.mos.util.basic.Logger;

import java.util.Queue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by love on 16-1-12.
 */

public class DiffImpl implements Diff
{
    private static Logger log = logger(DiffImpl.class);

    private final int revision;
    private final Queue<OperationLog> logs;

    private Maybe<Integer> transId;

    public DiffImpl(int revision, Queue<OperationLog> logs)
    {
        this(revision, logs, new Maybe<Integer>(MinusIDGenerator.genNextId()));
    }

    public DiffImpl(int revision, Queue<OperationLog> logs, Maybe<Integer> transId)
    {
        this.revision = revision;
        this.logs = logs;
        this.transId = transId;
    }


    @Override
    public int revision() {
        return revision;
    }


    @Override
    public void sync(MOS mos, ModelHead header) throws MOSException
    {
        try
        {
            for (OperationLog log : logs)
            {
                replayOperation(mos, header, log);
            }
        }

        catch (Exception e)
        {
            log.error(mos.getRootExternalDN() + " sync diff error:", e);
            mos.rollback(transId);
        }

        if(!logs.isEmpty())
        {
            SLRouterPool.getRouter(mos.getRootExternalDN()).noti(mos.commit(transId));
        }
    }

    private void replayOperation(MOS mos, ModelHead header, OperationLog log) throws Exception
    {
        Operation.process(new OperationContext(log.type, log, mos, header, transId));
    }

}
