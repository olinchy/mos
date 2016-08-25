package com.zte.container.ext.switchmodel.tm;

import com.zte.concept.DomainState;
import com.zte.container.ext.switchmodel.EmsRoutingRegister;
import com.zte.mos.container.BundleObject;
import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.*;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.except.MsgFrameException;

import java.rmi.RemoteException;
import java.util.UUID;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/10/16.
 */
public abstract class ModelSwitchTransaction implements ITransaction
{
    private static final long TIMEOUT = 60*10;

    protected final EmsRoutingRegister reg;
    private UUID key = null;
    private final long startTime;

    protected ModelSwitchTransaction()
    {
        this.startTime = System.currentTimeMillis();
        this.reg = new EmsRoutingRegister();
    }

    public boolean isTimeOut()
    {
        return (System.currentTimeMillis() - this.startTime) / 1000 > TIMEOUT;
    }

    public void onTimeOut()
    {
        logger(this.getClass()).debug("model switch transaction timeout " + this.getTargetId());

        try
        {
            rollback();
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error("fail to rollback ", e);
        }
    }

    protected ConnectionState getCurrentConnectState()
    {
        try
        {
            MosService service = new MosServiceHttp();
            Result<Mo> result = service.get(new DN(getTargetId()).to("Ne"));

            if (result.isSuccess())
            {
                return ConnectionState.contentOf(String.valueOf(result.getMo().get(0).get("connectionState")));
            }
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error(getTargetId() + " fail to get current connection state", e);
        }
        return ConnectionState.UNKNOWN;
    }



    protected void deRegBundleRoute() throws RemoteException
    {
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " start dereg bundle route");
        reg.register("dereg", getNeDn(), getBundleObject().identity());
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " end dereg bundle route");

    }

    protected void regBundleRoute() throws RemoteException
    {
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " start reg bundle route");
        reg.register("reg", getNeDn(), getBundleObject().identity());
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " end reg bundle route");

    }

    protected void regBundleNeDomain() throws MOSException
    {
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " start reg bundle nedomain");
        getBundleObject().save(getNeDomain(), DomainState.Committed);
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " end reg bundle nedomain");

    }

    protected void deRegBundleNeDomain()
    {
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " start dereg bundle nedomain");
        NeDomain domain = getNeDomain();
        if (domain != null)
        {
            this.getBundleObject().remove(domain);
        }
        logger(ModelSwitchTransaction.class).debug(getNeDn() + " end dereg bundle nedomain");

    }

    protected void lock() throws LockedByTransException
    {
        this.key = getImageSystem().lock();
    }
    protected void unlock()
    {
        getImageSystem().unlock(this.key);
    }

    protected void unbindSouth()
    {
        try
        {
            CommServiceFactory.getService().unRegister(getNeDomain().getID());
        }
        catch (Throwable e)
        {
            logger(ModelSwitchTransaction.class).error("fail to unbindSouth mos ", e);
        }
    }

    protected void bindSouth(ConnectionState state) throws MOSException
    {
        TargetAddress target = new TargetAddressImpl(getTargetId(), getIpAddress(), getImageSystem());
        try
        {
            CommServiceFactory.getService().register(target);
            CommServiceFactory.getService().setConnectSwitch(getTargetId(), state);
        }
        catch (MsgFrameException e)
        {
            throw new MOSException("fail to do south bind " + getTargetId(), e);
        }
    }



    protected abstract NeDomain getNeDomain();
    protected abstract BundleObject getBundleObject();
    protected abstract String getNeDn();
    protected abstract ImagedSystem getImageSystem();

    public abstract String getTargetId();
    public abstract TransactionType transType();
    public abstract String getIpAddress();


}
