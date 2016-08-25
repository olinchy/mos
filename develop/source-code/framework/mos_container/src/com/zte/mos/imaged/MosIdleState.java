package com.zte.mos.imaged;

import com.zte.mos.debugenv.DebuggingEnv;
import com.zte.mos.domain.AdminState;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.RemoteOperException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.basic.Logger;

import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

public class MosIdleState extends MosAdminState
{
    private static final Logger log = logger(MosIdleState.class);

    protected MosIdleState(ImagedMos mos)
    {
        super(mos);
    }

    @Override
    public void stop() throws MOSException
    {
        stateMos.mos.stop();
    }

    @Override
    public String getRootExternalDN()
    {
        return stateMos.mos.getRootExternalDN();
    }

    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        return stateMos.mos.getMO(dn, transactionID);
    }

    @Override
    public List<ManagementObject> find(
            String filterExpression, ManagementObject mo, Maybe<Integer> transactionID) throws
            MOSException
    {
        return stateMos.mos.find(filterExpression, mo, transactionID);
    }

    @Override
    public int startTransaction() throws MOSException
    {
        return stateMos.mos.startTransaction();
    }

    boolean isOnlineOperation(Maybe<Integer> transactionID)
    {
        return transactionID.isPresent() && transactionID.getValue() < -1;
    }

    private Maybe<Integer> getSendToRemoteTrans(Maybe<Integer> transactionID) throws MOSException
    {
        Maybe<Integer> remoteTrans;
        if (transactionID.nothing())
        {
            remoteTrans = new Maybe<Integer>(null);
        }
        else
        {
            Integer bindId = getBindTransaction(transactionID.getValue().intValue());
            if (Integer.MIN_VALUE == bindId)
            {
                remoteTrans = new Maybe<Integer>(null);
            }
            else
            {
                remoteTrans = new Maybe<Integer>(bindId);
            }
        }
        return remoteTrans;
    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        long operId = stateMos.mos.createMoInTransaction(mo, transactionID);
        if (!(mo.dn().toString().equals(MOS.ROOT_INTERNAL_DN) || isOnlineOperation(transactionID)))
        {
            CreateResponse response;
            Maybe<Integer> remoteTrans = getSendToRemoteTrans(transactionID);
            Create cmd = null;
            try
            {
                if (!DebuggingEnv.isDebuggingCreate)
                {
                    cmd = new Create(50, remoteTrans, mo);
                    ISouthService sv = CommServiceFactory.getService();
                    response = sv.create(cmd, this.getRootExternalDN());
                }
                else
                {
                    response = new CreateResponse(0, 0);
                }
            }
            catch (MsgFrameException e)
            {
                stateMos.mos.rollback(transactionID, operId);
                throw new RemoteOperException(e.getErrorCode(), e.getMessage());
            }
            catch (Throwable e)
            {
                stateMos.mos.rollback(transactionID, operId);
                throw new RemoteOperException(1, e.getMessage());
            }
            if (Integer.MIN_VALUE == getBindTransaction(transactionID.getValue().intValue()))
            {
                this.bindTransaction(transactionID.getValue().intValue(), response.getTransactionId());
            }

            if (response.isSuccess())
            {
                log.info("***create mo success," + "transId is" + transactionID);
                stateMos.mos.commit(transactionID, operId);
            }
            else
            {

                log.info("***create failed, return is***" + response.getResult() + ", transId is" + transactionID);
                stateMos.mos.rollback(transactionID, operId);
                throw new RemoteOperException(response.getResult(), "");
            }
        }
        else
        {
            stateMos.mos.commit(transactionID, operId);
        }
    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        RemovedMoInTrans removed = stateMos.mos.deleteMoInTransaction(dn, transactionID);
        if (!(isOnlineOperation(transactionID) || dn.equals(MOS.ROOT_INTERNAL_DN)))
        {
            Maybe<Integer> remoteTrans = getSendToRemoteTrans(transactionID);

            IResponse response;
            Delete cmd = null;
            try
            {
                if (!DebuggingEnv.isDebuggingDel)
                {
                    cmd = new Delete(50, remoteTrans, removed.mo);
                    ISouthService sv = CommServiceFactory.getService();
                    response = sv.delete(cmd, this.getRootExternalDN());
                }
                else
                {
                    response = new DeleteResponse(0, 0);
                }
            }
            catch (MsgFrameException e)
            {
                stateMos.mos.rollback(transactionID, removed.operId);
                throw new RemoteOperException(e.getErrorCode(), e.getMessage());
            }
            catch (Throwable e)
            {
                stateMos.mos.rollback(transactionID, removed.operId);
                throw new RemoteOperException(1, e.getMessage());
            }

            if (Integer.MIN_VALUE == getBindTransaction(transactionID.getValue()))
            {
                this.bindTransaction(transactionID.getValue(), ((DeleteResponse) response).transactionId);
            }
            if (response.isSuccess())
            {
                stateMos.mos.commit(transactionID, removed.operId);
            }
            else
            {
                stateMos.mos.rollback(transactionID, removed.operId);
                throw new RemoteOperException(response.getResult(), "");
            }
        }
        else
        {
            stateMos.mos.commit(transactionID, removed.operId);
        }
        return removed.mo;
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException
    {
        long operId = stateMos.mos.updateMoInTransaction(mo, transactionID);
        ManagementObject old = stateMos.mos.getMO(mo.dn().toString(), new Maybe<Integer>(null));
        if (!(isOnlineOperation(transactionID) || mo.dn().toString().equals(MOS.ROOT_INTERNAL_DN)))
        {
            Maybe<Integer> remoteTrans = getSendToRemoteTrans(transactionID);
            IResponse response;
            Update cmd;
            try
            {
                if (!DebuggingEnv.isDebugUpdate)
                {
                    refreshDelta(mo, delta);
                    cmd = new Update(50, remoteTrans, mo, delta, old);
                    ISouthService sv = CommServiceFactory.getService();
                    response = sv.update(cmd, this.getRootExternalDN());
                    // FIXME: 16-1-26  response = new UpdateResponse(0, transactionID.getValue());
                }
                else
                {
                    response = new UpdateResponse(0, 0);
                }
            }
            catch (MsgFrameException e)
            {
                stateMos.mos.rollback(transactionID, operId);
                throw new MOSException(new ErrorCode(e.getErrorCode()), e.getMessage());
            }
            catch (Throwable e)
            {
                stateMos.mos.rollback(transactionID, operId);
                throw new MOSException(e);
            }
            if (Integer.MIN_VALUE == getBindTransaction(transactionID.getValue()))
            {
                this.bindTransaction(
                        transactionID.getValue().intValue(), ((UpdateResponse) response).transactionId);
            }

            if (response.isSuccess())
            {
                stateMos.mos.commit(transactionID, operId);
            }
            else
            {
                stateMos.mos.rollback(transactionID, operId);
                throw new RemoteOperException(response.getResult(), "");
            }
        }
        else
        {
            stateMos.mos.commit(transactionID, operId);
        }
    }

    /**
     * make sure every change is under the proper type
     *
     * @param mo
     * @param delta
     * @throws MOSException
     */
    private void refreshDelta(ManagementObject mo, Mo delta) throws MOSException
    {
        Mo full = mo.toMoClass();
        for (String key : delta.getMo().keySet())
        {
            delta.setAttr(key, full.get(key));
        }
    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        IResponse response = null;
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
                Commit commit = new Commit(this.getRootExternalDN(), transactionID, new Maybe<Integer>(remoteTrans));

                try
                {
                    ISouthService sv = CommServiceFactory.getService();
                    response = sv.commit(commit, this.getRootExternalDN());
                    clearBindTransaction(transactionID.getValue());
                }
                catch (MsgFrameException e)
                {
                    clearBindTransaction(transactionID.getValue());
                    throw new MOSException(new ErrorCode(e.getErrorCode()), e.getMessage());
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
            if (response != null && !response.isSuccess())
            {
                log.info(
                        "*** commit failed, " + getRootExternalDN() + transactionID + ", return " + response.getResult() + "***");
                stateMos.mos.rollback(transactionID);
                throw new RemoteOperException(response.getResult(), "Commit error");
            }

            log.info("*** commit success" + transactionID + "***");
            return stateMos.mos.commit(transactionID);
        }
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {
        try
        {
            if (!(DebuggingEnv.isDebuggingRollback || isOnlineOperation(transactionID)))
            {
                int remoteTrans = 0;
                try
                {
                    remoteTrans = this.getBindTransaction(transactionID.getValue());
                }
                catch (Exception e)
                {
                    log.info(e.getMessage() + transactionID);
                    return;
                }

                Rollback rollback = new Rollback
                        (getRootExternalDN(), 50, new Maybe<Integer>(remoteTrans));
                try
                {
                    ISouthService sv = CommServiceFactory.getService();
                    sv.rollback(rollback, this.getRootExternalDN());
                    clearBindTransaction(transactionID.getValue());
                }
                catch (MsgFrameException e)
                {
                    clearBindTransaction(transactionID.getValue());
                    throw new MOSException(new ErrorCode(e.getErrorCode()), e.getMessage());
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
            stateMos.mos.rollback(transactionID);
        }
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        return this.deleteMO(mo.dn().toString(), transId);
    }

    @Override
    public MoMetaClass getMeta(String name) throws MOSException
    {
        return stateMos.mos.getMeta(name);
    }

    @Override
    public void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        stateMos.mos.deref(to, from, transactionID);
    }

    @Override
    public void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        stateMos.mos.ref(to, from, transactionID);
    }

    @Override
    public AdminState getAdminState()
    {
        return AdminState.idle;
    }
}
