package com.zte.mos.imaged;

import com.zte.container.ext.diff.DiffImpl;
import com.zte.mos.debugenv.DebuggingEnv;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TransactionLog;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.RemoteOperException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.operation.AckResponse;
import com.zte.mos.msg.framework.operation.Commit;
import com.zte.mos.persistent.Log;
import com.zte.mos.util.basic.Logger;

import java.util.LinkedList;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 2/23/16.
 */
public class V12MosIdleState extends MosIdleState {
    private static final Logger log = logger(V12MosIdleState.class);

    protected V12MosIdleState(ImagedMos mos) {
        super(mos);
    }


    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        try
        {
            if (!(DebuggingEnv.isDebuggingCommit || isOnlineOperation(transactionID)))
            {
                int remoteTrans = 0;
                try
                {
                    remoteTrans = this.getBindTransaction(transactionID.getValue());
                }
                catch (Exception e)
                {
                    log.info(e.getMessage() + transactionID);
                    return null;
                }
                Commit commit = new Commit(this.getRootExternalDN(), new Maybe<Integer>(remoteTrans));
                AckResponse response;
                try
                {
                    ISouthService sv = CommServiceFactory.getService();
                    response = sv.commit(commit, this.getRootExternalDN());
                    clearBindTransaction(transactionID.getValue());

                    stateMos.mos.rollback(transactionID);

                    if (response.isSuccess())
                    {
                        replay(response.getTransactionLog(), response.getRevision(), transactionID);
                    }

                }
                catch (Throwable e)
                {
                    clearBindTransaction(transactionID.getValue());
                    throw new RemoteOperException(1, e.getMessage());
                }

            }
        }
        catch (Exception e)
        {
            log.info(e.getMessage());
        }
        finally
        {
            return stateMos.mos.commit(transactionID);
        }
    }

    private void replay(TransactionLog transLog, int revision, Maybe<Integer> transId) throws MOSException
    {
        this.stateMos.syncDiff(new DiffImpl(revision, transLog.getQueue(), transId));
    }

}
