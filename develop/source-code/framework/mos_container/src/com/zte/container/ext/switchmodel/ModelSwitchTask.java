package com.zte.container.ext.switchmodel;

import com.zte.mos.exception.ExistedModelSwitchTransaction;
import com.zte.mos.exception.SwitchModelException;
import com.zte.container.ext.switchmodel.tm.ModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.local.LocalModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.remote.RemoteModelSwitchTransactionProxy;
import com.zte.container.ext.switchmodel.tm.TransactionType;
import com.zte.mos.exception.MOSException;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import java.rmi.RemoteException;

import static com.zte.container.ext.switchmodel.tm.TransactionManager.createLocalModelTransaction;
import static com.zte.container.ext.switchmodel.tm.TransactionManager.removeModelSwitchTransaction;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/5/16.
 */


public class ModelSwitchTask extends SchedulingTask
{
    private static final String MODEL_SWITCH_START = "/Ems/1/ModelSwitchStart/1";
    private static final String MODEL_SWITCH_END = "/Ems/1/ModelSwitchEnd/1";
    private final RemoteModelSwitchTransactionProxy remoteTransProxy;

    enum SwitchResult
    {
        success, failed;
    }

    public ModelSwitchTask(ModelSwitchContext context)
    {
        this.context = context;
        this.result = SwitchResult.failed;
        this.remoteTransProxy = new RemoteModelSwitchTransactionProxy(context);
        this.ex = null;
    }

    private final ModelSwitchContext context;
    private SwitchResult result;
    private Throwable ex;


    @Override
    protected String identity()
    {
        return context.getID();
    }

    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();
        beforeSwitch();
        try
        {
            doSwitch();
        }
        catch(ExistedModelSwitchTransaction e)
        {
            logger(ModelSwitchTask.class).debug(" discard current model switch req");
            return;
        }
        catch (Throwable e)
        {
            this.ex = e;
            logger(ModelSwitchTask.class).error(this.context.getID() + " fail to do model switch task", e);
        }
        afterSwitch();
        long endTime = System.currentTimeMillis();
        logger(ModelSwitchTask.class).debug("model switch task takes " + (endTime - startTime)/1000 + " s");

    }


    private void beforeSwitch()
    {
        MoEvent event = new MoEvent(MODEL_SWITCH_START, "");
        event.put("dn", context.getID());
        notifyEvent(event);

    }

    private void afterSwitch()
    {
        MoEvent event = new MoEvent(MODEL_SWITCH_END, "");
        event.put("dn", context.getID());
        event.put("result", result.name());
        event.put("ex", ex);
        notifyEvent(event);
    }

    private void doSwitch() throws MOSException, RemoteException
    {
        try
        {
            switchReq();
            this.result = SwitchResult.success;
        }
        catch (ExistedModelSwitchTransaction e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            logger(ModelSwitchTask.class).error(this.context.getID() + " fail to do switch", e);
            rollback();
            throw new SwitchModelException(e);
        }
        commit();
    }


    private void commit() throws RemoteException, MOSException
    {
        try
        {
            commitRemote();
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error(context.getID() + " fail to commit remote model switch transaction", e);
            rollbackLocal();
            return;
        }
        commitLocal();
    }

    private void rollback() throws RemoteException, MOSException
    {
        rollbackRemote();
        rollbackLocal();
    }

    private void switchReq() throws RemoteException, MOSException
    {
        localSwitchReq();
        remoteSwitchReq();
    }

    private void localSwitchReq() throws MOSException, RemoteException
    {
        LocalModelSwitchTransaction localTrans = createLocalModelTransaction(this.context);
        localTrans.start();
    }

    private void remoteSwitchReq() throws RemoteException, MOSException
    {
        this.remoteTransProxy.start();
    }



    private void commitLocal() throws MOSException
    {
        try
        {
            ModelSwitchTransaction localTrans = removeModelSwitchTransaction(this.context.getID(), TransactionType.local);
            localTrans.commit();
        }
        catch (Throwable e)
        {
            logger(ModelSwitchTask.class).error(context.getID() + " fail to commit local session", e);
        }
    }

    private void commitRemote() throws RemoteException, MOSException
    {
        remoteTransProxy.commit();
    }

    private void rollbackLocal() throws MOSException, RemoteException
    {
        try
        {
            ModelSwitchTransaction localTrans = removeModelSwitchTransaction(this.context.getID(), TransactionType.local);
            localTrans.rollback();
        }
        catch (Throwable e)
        {
            logger(ModelSwitchTask.class).error(context.getID() + " fail to rollback local session", e);
        }
    }

    private void rollbackRemote() throws RemoteException, MOSException
    {
        try
        {
            remoteTransProxy.rollback();
        }
        catch (Throwable e)
        {
            logger(ModelSwitchTask.class).error(context.getID() + " fail to rollback remote session", e);
        }
    }


    void notifyEvent(MoEvent event)
    {
        try
        {
            logger(ModelSwitchTask.class).info(" send event " + event);
            SmartLinkMsgService.send("BUNDLE", event);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
