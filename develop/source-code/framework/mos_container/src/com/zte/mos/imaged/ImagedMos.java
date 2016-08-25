package com.zte.mos.imaged;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.container.ext.total.FastImagedDB;
import com.zte.container.ext.total.TotalSynchor;
import com.zte.mos.domain.*;
import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.service.RawMos;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.msg.MoEvent;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import java.util.UUID;

import static com.zte.mos.domain.DataState.init;
import static com.zte.mos.domain.DataState.synced;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 14-12-8.
 * modify by luoqingkai on 15-09-21
 */
public abstract class ImagedMos extends StatefulMos implements ImagedSystem
{
    private static final String MOSSTATECHANGEEVENT = "MosStateChangeEvent";
    protected static Logger logger = logger(ImagedMos.class);
    private ModelMETA modelMETA;
    protected TotalSynchor totalSynchor;
    private int revision = 0;
    protected volatile UUID key = null;
    protected DataState dataState = init;
    protected boolean init_reverse_ok = false;
    public Object locker = new Object();

    public ImagedMos(ModelMETA meta, String dn, String smartlinkName)
            throws MOSException
    {
        super(dn, meta.modelHead, smartlinkName);
        modelMETA = meta;
        this.totalSynchor = new TotalSynchor(this.mos, meta.modelHead);
    }

    protected ImagedMos(ModelMETA meta, RawMos mos)
    {
        super(mos);
        this.modelMETA = meta;
        this.totalSynchor = new TotalSynchor(mos, meta.modelHead);
    }

    public void reverseFailed()
    {
        setDataState(DataState.unsynced);
    }

    @Override
    public int revision()
    {
        return this.revision;
    }

    @Override
    public DataState getDataState()
    {
        return dataState;
    }

    @Override
    public void setDataState(DataState state)
    {
        if (state == init)
        {
            init_reverse_ok = false;
        }
        else if (state == synced && init_reverse_ok == false)
        {
            init_reverse_ok = true;
        }
        this.dataState = state;

        // send mos state change event
        notifyMosStateChanged();
    }

    protected void notifyMosStateChanged()
    {
        try
        {
            MoEvent event = new MoEvent(MOSSTATECHANGEEVENT);
            event.put("targetId", mos.getRootExternalDN());
            event.put("mosState", this.dataState.name());
            event.put("init_reverse_ok", init_reverse_ok);

            logger(this.getClass()).debug("start to notify mos state changed " + event.toString());

            SmartLinkMsgService.send("BUNDLE", event);
        }
        catch (MOSException e)
        {
            logger(this.getClass()).error("fail to notify mos state changed , targetId " + mos.getRootExternalDN(), e);
        }
    }

    @Override
    public ModelMETA getModelMETA()
    {
        return this.modelMETA;
    }

    @Override
    public ImagedSystem switchTo(ModelMETA meta)
    {
//        // load all MO to cache
//        FastImagedDB oldDb = load();
//        // clear the db data
//        clearDb();
//        // create a new mos
//        RawMos newRawMos = new RawMos(this.getRootExternalDN(),meta.modelHead, "BUNDLE");
//        // traverse the cache, and transform the mo, and save to db
//        for (ManagementObject mo : oldDb.moes())
//        {
//            ManagementObject newMo = transform(mo, getNewMoClass(mo.getClass().getSimpleName(), meta));
//            if (newRawMos.getMO(mo.dn().toString(), MOS.SYNC_TRANS_ID) != null)
//            {
//                newRawMos.createMO(newMo);
//            }
//            else
//            {
//                newRawMos.updateMO(newMo, null, MOS.SYNC_TRANS_ID);
//            }
//        }
//        //should del failed moes
//
//        newRawMos.commit(MOS.SYNC_TRANS_ID);
//
//
//
//        ImagedMos clonedMos = ImagedMosFactory.build(meta, newRawMos);
//        return clonedMos;
        return null;
    }

    private Class<? extends ManagementObject> getNewMoClass(String name, ModelMETA meta)
    {
        return null;
    }

    @Override
    public UUID lock() throws LockedByTransException
    {
        synchronized (locker)
        {
            if (key != null)
            {
                logger.debug(this.getRootExternalDN() + " fail to lock , current key= " + key.toString());
                throw new LockedByTransException(this.getRootExternalDN() + "has locked by " + this.key);
            }
            setDataState(DataState.syncing);
            this.adminState = new MosSyncState(this);
            this.key = UUID.randomUUID();
            logger.debug(this.getRootExternalDN() + " start lock key= " + key.toString());
            return key;
        }
    }

    public boolean isLocked()
    {
        return key != null;
    }

    @Override
    public boolean sync(SysSnapSpot sysData, UUID key) throws MOSException
    {
        try
        {
            if (!this.key.equals(key))
            {
                logger.info("reverse syncing ...... ");
                return false;
            }
            else
            {
                this.mos.rollbackAllUserTransactions();
                CommServiceFactory.getService().localRollback(this.getRootExternalDN());
                this.totalSynchor.save(sysData.getMoArray());
                this.revision = sysData.revision();
                setDataState(DataState.synced);
                return true;
            }
        }
        catch (Throwable e)
        {
            setDataState(DataState.unsynced);
            throw new MOSException(e);
        }
    }

    @Override
    public void syncDiff(Diff diff) throws MOSException
    {
        diff.sync(this.mos, modelMETA.modelHead);
        this.revision = diff.revision();
    }

    @Override
    public JsonNode act(MoActionMsg msg) throws MOSException
    {
        ActionResponse response;
        Action cmd;
        try
        {
            cmd = new Action(this.getMO(msg.dn(), new Maybe<Integer>(null)), msg.actionName(), msg.getParameters());
            ISouthService sv = CommServiceFactory.getService();
            response = sv.action(cmd, this.getRootExternalDN());
        }
        catch (MsgFrameException e)
        {
            logger(this.getClass()).error(
                    "msg frame exception !!! fail to excute action , dn: " + msg.dn() + " actionName:" + msg.actionName(),
                    e);
            throw new MOSException(new ErrorCode(e.getErrorCode()));
        }

        catch (MOSException e)
        {
            logger(this.getClass()).error(
                    "mos internal exception !!! fail to excute action , dn: " + msg.dn() + " actionName:" + msg.actionName(),
                    e);
            throw e;
        }

        return JsonUtil.toNode(response);
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        return mos.delete(mo, transId);
    }

    private FastImagedDB load()
    {
        FastImagedDB db = new FastImagedDB();
        return db;
    }

    @Override
    public MoMetaClass getMeta(String name) throws MOSException
    {
        return mos.getMeta(name);
    }

    public void hangUp()
    {
        RawMos mos = getRawMos();
        mos.rollbackAllUserTransactions();
        mos.hangUp();
    }

    public void resume()
    {
        RawMos mos = getRawMos();
        mos.restore();
    }

    public void halt()
    {
        RawMos mos = getRawMos();
        mos.halt();
    }

    @Override
    public void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        adminState.deref(to, from, transactionID);
    }

    @Override
    public void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        adminState.ref(to, from, transactionID);
    }
}
