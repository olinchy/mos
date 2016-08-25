package com.zte.mos.imaged;

import com.zte.mos.domain.AdminState;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.SyncStateException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;

import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-11.
 */
public class MosSyncState extends MosAdminState
{
    protected MosSyncState(ImagedMos stateMos)
    {
        super(stateMos);
        logger(this.getClass()).info("activate sync state!" + stateMos.mos.getRootExternalDN());
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
            String filterExpression, ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException
    {
        return stateMos.mos.find(filterExpression, mo, transactionID);
    }

    @Override
    public int startTransaction() throws MOSException
    {
        return stateMos.mos.startTransaction();
    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        throw new SyncStateException();
    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        throw new SyncStateException();
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException
    {
        throw new SyncStateException();
    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        throw new SyncStateException();
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {
        stateMos.mos.rollback(transactionID);
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        throw new SyncStateException();
    }

    @Override
    public MoMetaClass getMeta(String name) throws MOSException
    {
        return stateMos.mos.getMeta(name);
    }

    @Override
    public void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        if (isDataRecover(transactionID))
        {
            stateMos.mos.deref(to, from, transactionID);
        }
        else
        {
            throw new SyncStateException();
        }
    }

    private boolean isDataRecover(Maybe<Integer> transactionID)
    {
        return transactionID.equals(MOS.SYNC_TRANS_ID) || transactionID.equals(MOS.SWITCH_MODEL_TRANS_ID);
    }

    @Override
    public void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        if (isDataRecover(transactionID))
        {
            stateMos.mos.ref(to, from, transactionID);
        }
        else
        {
            throw new SyncStateException();
        }
    }

    @Override
    public AdminState getAdminState()
    {
        return AdminState.synchronizing;
    }
}
