package com.zte.container.ext.switchmodel.tm.local;

import com.zte.container.ext.switchmodel.tm.ModelSwitchTransaction;
import com.zte.container.ext.switchmodel.EmsRoutingRegister;
import com.zte.container.ext.switchmodel.ModelSwitchContext;
import com.zte.container.ext.switchmodel.tm.TransactionType;
import com.zte.mos.container.BundleObject;
import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.imaged.ImagedMos;
import com.zte.mos.msg.framework.CommServiceFactory;

import java.rmi.RemoteException;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/9/16.
 */
public class LocalModelSwitchTransaction extends ModelSwitchTransaction
{
    public LocalModelSwitchTransaction(ModelSwitchContext ctx)
    {
        super();
        this.ctx = ctx;
        this.image = (ImagedSystem)ctx.getMos();
        this.reg = new EmsRoutingRegister();
    }

    private final ModelSwitchContext ctx;
    private final EmsRoutingRegister reg;
    private final ImagedSystem image;

    @Override
    public void start() throws MOSException, RemoteException
    {
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " start local model switch session");
        removePendingTask();
        suspendLocalMos();
        unbindSouth();
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " end start model switch session ");

    }

    private void removePendingTask()
    {
        CommServiceFactory.getService().localRollback(ctx.getID());
    }

    @Override
    public void commit() throws MOSException
    {
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " start commit local model switch session ");
        clearLocalMos();
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " end commit local model switch session ");
    }


    @Override
    protected NeDomain getNeDomain()
    {
        return ctx.getLocalNe();
    }

    @Override
    protected BundleObject getBundleObject()
    {
        return (BundleObject)ctx.getLocalBundle();
    }

    @Override
    protected String getNeDn()
    {
        return ctx.getID();
    }

    @Override
    protected ImagedSystem getImageSystem()
    {
        return image;
    }

    @Override
    public String getTargetId()
    {
        return ctx.getID();
    }

    @Override
    public TransactionType transType()
    {
        return TransactionType.local;
    }

    @Override
    public String getIpAddress()
    {
        return ctx.getIpAddress();
    }

    @Override
    public void rollback() throws MOSException, RemoteException
    {
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " start rollback local model switch session ");
        resumeLocalMos();
        logger(LocalModelSwitchTransaction.class).debug(ctx.getID() + " end rollback local model switch session ");

    }


    private void suspendLocalMos() throws LockedByTransException, RemoteException
    {
        deRegBundleRoute();
        lock();
        this.image.hangUp();
    }

    private void resumeLocalMos() throws RemoteException, MOSException
    {
        regBundleRoute();
        regBundleNeDomain();
        try
        {
            this.image.resume();
        }
        catch (Throwable e)
        {
            logger(LocalModelSwitchTransaction.class).error("fail to resume local mos ", e);
            throw new MOSException(e);
        }
        finally
        {
            unlock();
            rebindSouth();
        }

    }

    private void clearLocalMos()
    {
        unlock();
        try
        {
            ((ImagedMos)this.image).halt();
        }
        catch (Throwable e)
        {
            logger(LocalModelSwitchTransaction.class).error("fail to clear local mos", e);
        }
        deRegBundleNeDomain();
    }

    private void rebindSouth() throws MOSException
    {
        bindSouth(getCurrentConnectState());
    }

}
