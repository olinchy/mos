package com.zte.mos.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.odb.database.ODBWalker;
import com.zte.mos.domain.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.persistent.Log;
import com.zte.mos.storage.*;
import com.zte.mos.tools.Expression;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.tools.IDAllocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.zte.mos.message.MoMetaClass.Package_Prefix;
import static com.zte.mos.type.Pair.pair;
import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.To.map;
import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;
import static com.zte.mos.util.tools.JsonUtil.toNode;

@SuppressWarnings("ALL")
public class RawMos implements MOS
{
    private static final Logger log = logger(RawMos.class);
    private static final long TIME_OUT = 10 * 60 * 1000;
    private final IDAllocator allocator = new IDAllocator(1, 65535);
    private String rootExternalDN;
    protected MODB db;
    private Map<Integer, OdbOperation> transactionMap = new ConcurrentHashMap<Integer, OdbOperation>();
    private Map<Integer, Timer> timerMap = new ConcurrentHashMap<Integer, Timer>();
    private String smartlinkName;
    private ModelHead modelHeader;

    public RawMos(String rootDN, ModelHead header, String smName) throws MOSException
    {
        init(rootDN, header, smName);
        autoCreateRoot();
    }

    public RawMos(String rootDN, ModelHead header, String smName, Maybe<Integer> transId) throws MOSException
    {
        init(rootDN, header, smName);
        autoCreateRootInTransaction(transId);
    }

    private void autoCreateRootInTransaction(Maybe<Integer> transId) throws MOSException
    {
        TriggerRegister register = new TriggerRegister();
        db = new MODB(register, this);
        db.regObjTriggers();
        try
        {
            ManagementObject mo = (ManagementObject) modelHeader.rootClass().newInstance();
            setMos(mo, this);
            mo.setDn(MOS.ROOT_INTERNAL_DN);
            this.createMO(mo, transId);
        }
        catch (Exception e)
        {
            logger(this.getClass()).debug(rootExternalDN + " create root failed", e);
        }
    }

    private void init(String rootDN, ModelHead header, String smName) throws MOSException
    {
        this.rootExternalDN = rootDN;
        this.modelHeader = header;
        this.smartlinkName = smName;
        registerAfterGetter();
        SmartLinkRouter smartlinkRouter = new SmartLinkRouter(rootExternalDN, smName);
        SLRouterPool.reg(rootDN, smartlinkRouter);
    }

    public void regSmartLinkRouter()
    {
        try
        {
            SmartLinkRouter smartlinkRouter = new SmartLinkRouter(rootExternalDN, this.smartlinkName());
            SLRouterPool.reg(this.getRootExternalDN(), smartlinkRouter);
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error(this.rootExternalDN + " fail to reg smart link router", e);
        }
    }

    @Override
    public void clear(String dn) throws MOSException
    {
        ManagementObject mo = db.get(new DN(dn));
        mo.setMos(this);
        ArrayList<String> list = new ArrayList<String>();

        if (mo != null)
        {
            if (mo.isGroup())
            {
                list.addAll(mo.lsDN());
            }
            else
            {
                list.add(mo.dn().toString());
            }
        }

        for (String dnString : list)
        {
            logger(this.getClass()).info("clearing " + dnString);
            db.remove(new DN(dnString));
        }
    }

    private void registerAfterGetter()
    {
        Set<Class<AfterGetter>> clazzes = Scan.getClasses(Package_Prefix, AfterGetter.class);
        for (Class<AfterGetter> clazz : clazzes)
        {
            if (!Modifier.isAbstract(clazz.getModifiers()))
            {
                try
                {
                    Constructor<AfterGetter> con = clazz.getConstructor();
                    AfterGetter<ManagementObject, Mo> getter = con.newInstance();
                    getter.register(getInstance(AfterGetters.class));
                    logger(RawMos.class).info("afterGetter of " + clazz.getSimpleName() + " registered!");
                }
                catch (Exception e)
                {
                    logger(RawMos.class).info("reg afterGetter of " + clazz.getSimpleName() + " failed!");
                }
            }
        }
    }

    private void autoCreateRoot() throws MOSException
    {
        TriggerRegister register = new TriggerRegister();
        db = new MODB(register, this);
        if (db.get(new DN(MOS.ROOT_INTERNAL_DN)) == null)
        {
            try
            {
                ManagementObject mo = (ManagementObject) modelHeader.rootClass().newInstance();
                setMos(mo, this);
                mo.setDn(MOS.ROOT_INTERNAL_DN);
                MosTransaction transaction = db.newTransaction(0);
                transaction.create(mo);
                register.regObjectTriggers(transaction.odb());

                transaction.initBusinessProcess();
                SLRouterPool.getRouter(this.getRootExternalDN()).noti(transaction.commit());
            }
            catch (Exception e)
            {
                logger(this.getClass()).debug(rootExternalDN + " create root failed", e);
            }
        }
        else
        {
            db.regObjTriggers();
        }
    }

    // TODO
    public int getLatestRevision()
    {
        return db.getRevision();
    }

    // TODO
    public void stop() throws MOSException
    {
        SLRouterPool.unReg(this.rootExternalDN);
        db.stop();
        ReferenceDBInBerkeley.stop(this);
    }

    @Override
    public String smartlinkName()
    {
        return this.smartlinkName;
    }

    @Override
    public String getRootExternalDN()
    {
        return rootExternalDN;
    }

    private OdbOperation getOdbOperation(Maybe<Integer> transactionID) throws MOSException
    {
        if (transactionID.nothing() || transactionID.getValue() == 0)
        {
            return this.db;
        }
        else
        {
            OdbOperation trans = transactionMap.get(transactionID.getValue());
            if (trans == null)
            {
                trans = startTransaction(transactionID.getValue());
            }
            return trans;
        }
    }

    private MosTransaction startTransaction(int transID) throws MOSException
    {
        logger(this.getClass()).debug(rootExternalDN + " allocate transID: " + String.valueOf(transID));
        MosTransaction transaction = db.newTransaction(transID);
        this.transactionMap.put(transID, transaction);
        createTimeoutTask(transID);
        return transaction;
    }

    private void createTimeoutTask(final int transID)
    {
        final Timer timer = new Timer("Timer-" + RawMos.class.getSimpleName());
        timerMap.put(transID, timer);
        timer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        if (transactionMap.containsKey(transID))
                        {
                            try
                            {
                                rollback(new Maybe<Integer>(transID));
                                logger(RawMos.class).debug(rootExternalDN + " rollback timeout transId: " + transID);
                            }
                            catch (MOSException e)
                            {
                                logger(RawMos.class).warn(
                                        rootExternalDN + " rollback timeout transId: " + transID + " failed!", e);
                            }
                        }
                        timer.cancel();
                    }
                }, TIME_OUT, 30);
    }

    private OdbOperation getExistedOdbOperation(Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation trans = transactionMap.get(transactionID.getValue());
        if (trans == null)
        {
            throw new NoSuchTransException();
        }
        return trans;
    }

    private void cancelTimer(Maybe<Integer> transactionID)
    {
        Timer timer = this.timerMap.remove(transactionID.getValue());
        if (timer != null)
        {
            timer.cancel();
            log.debug("cancel timer of trans: " + transactionID.getValue() + " of " + rootExternalDN);
        }
    }

    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = (isSyncTrans(transactionID) ? getExistedOdbOperation(transactionID) : getOdbOperation(
                transactionID));
        ManagementObject mo = operation.get(new DN(dn));
        setMos(mo, this);
        return mo;
    }

    @Override
    public List<ManagementObject> find(
            String filterExpression, ManagementObject local, Maybe<Integer> transactionID) throws
            MOSException
    {
        Expression expression = new Expression(filterExpression);
        OdbOperation operation = getOdbOperation(transactionID);
        return operation.find(expression, local);
    }

    @Override
    public void all(ODBWalker walker, Maybe<Integer> transId) throws MOSException
    {
        this.getOdbOperation(transId).all(walker);
    }

    @Override
    public int startTransaction() throws MOSException
    {
        final int transID = this.allocator.allocate();

        startTransaction(transID);

        return transID;
    }

    @Override
    public int generateTransId()
    {
        return this.allocator.allocate();
    }

    @Override
    public void bindTransaction(int masterTrans, int slaveTrans) throws MOSException
    {
        //log.info("bind trans:" + masterTrans + ",  " + slaveTrans);
        OdbOperation operation = getExistedOdbOperation(new Maybe<Integer>(masterTrans));
        operation.bindTransaction(slaveTrans);
    }

    @Override
    public int getBindTransaction(int masterTrans) throws MOSException
    {
        OdbOperation operation = getExistedOdbOperation(new Maybe<Integer>(masterTrans));
        int res = operation.getBindTransaction(masterTrans);
        //log.info("bind trans:" + res);
        return operation.getBindTransaction(masterTrans);
    }

    @Override
    public void clearBindTransaction(int masterTrans) throws MOSException
    {
        OdbOperation operation = getExistedOdbOperation(new Maybe<Integer>(masterTrans));
        operation.clearBindTransaction(masterTrans);
    }

    /**
     * create mo in transaction(ID==transactionID) without commit.
     * return the operation ID of this operation
     *
     * @param mo
     * @param transactionID
     * @return return the operation ID or inner transaction ID of transaction whose ID is
     * transactionID
     * @throws MOSException
     */
    @Override
    public long createMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = getOdbOperation(transactionID);
        return operation.createMoInTransaction(mo);
    }

    @Override
    public RemovedMoInTrans deleteMoInTransaction(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = getOdbOperation(transactionID);
        return operation.removeMoInTransaction(new DN(dn));
    }

    @Override
    public long updateMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = getOdbOperation(transactionID);
        return operation.updateMoInTransaction(mo);
    }

    /**
     * rollback the operation(ID==operId) in transaction(ID==transactionID)
     *
     * @param transactionID
     * @param operId
     * @throws Exception
     */
    @Override
    public void rollback(Maybe<Integer> transactionID, long operId) throws MOSException
    {
        OdbOperation operation = getExistedOdbOperation(transactionID);
        operation.rollback(operId);
    }

    @Override
    public void commit(Maybe<Integer> transactionID, long operId) throws MOSException
    {
        OdbOperation operation = getExistedOdbOperation(transactionID);
        operation.commit(operId);
    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        setMos(mo, this);
        OdbOperation operation = (isSyncTrans(transactionID) ? getExistedOdbOperation(transactionID) : getOdbOperation(
                transactionID));
        operation.create(mo);
    }

    @Override
    public void recover(ManagementObject mo) throws MOSException
    {
        mo.setMos(this);
        this.db.recover(mo);
    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = (isSyncTrans(transactionID) ? getExistedOdbOperation(transactionID) : getOdbOperation(
                transactionID));
        //log.info("del mo trans:" + transactionID);
        return operation.remove(new DN(dn));
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException
    {
        setMos(mo, this);
        OdbOperation operation = (isSyncTrans(transactionID) ? getExistedOdbOperation(transactionID) : getOdbOperation(
                transactionID));
        operation.update(mo);
    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        logger(this.getClass()).info(
                rootExternalDN + " commit, release transID " + String.valueOf(transactionID.getValue()));

        try
        {
            // make sure the transaction had been removed after this
            LinkedList<Log<ManagementObject>> ret = new LinkedList<Log<ManagementObject>>();
            OdbOperation operation = getExistedOdbOperation(transactionID);
            try
            {
                // protecting the commit action
                List<Log<ManagementObject>> lst = operation.commit();

                ret.addAll(
                        map(
                                lst, new LambdaConverter<Log<ManagementObject>, Log<ManagementObject>>()
                                {
                                    @Override
                                    public Log<ManagementObject> to(Log<ManagementObject> content)
                                    {
                                        setMos(content.newValue(), RawMos.this);
                                        setMos(content.oldValue(), RawMos.this);
                                        return content;
                                    }
                                }));
                return ret;
            }
            catch (Throwable e)
            {
                operation.rollback();
                return ret;
            }
        }
        finally
        {
            this.transactionMap.remove(transactionID.getValue());
            cancelTimer(transactionID);
        }
    }

    private void setMos(ManagementObject managementObject, MOS mos)
    {
        if (managementObject != null)
        {
            managementObject.setMos(mos);
        }
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {
        logger(this.getClass()).info(
                rootExternalDN + " rollback, release transID " + String.valueOf(transactionID.getValue()));
        try
        {
            OdbOperation operation = getExistedOdbOperation(transactionID);
            operation.rollback();
        }
        finally
        {
            this.transactionMap.remove(transactionID.getValue());
            cancelTimer(transactionID);
        }
    }

    @Override
    public JsonNode act(MoActionMsg msg) throws MOSException
    {
        return toNode(SLRouterPool.getRouter(this.rootExternalDN).onMsg(msg));
    }

    @Override
    public DistinguishedList<String> getAffectedDN(Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation odbOperation = this.getOdbOperation(transactionID);

        return odbOperation.getAffectedDN();
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        this.getOdbOperation(transId).delete(mo);
        return mo;
    }

    public RemovedMoInTrans deleteMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        OdbOperation operation = getOdbOperation(transactionID);
        return operation.removeMoInTransaction(mo);
    }

    // TODO
    protected String handleFailure(Maybe<Integer> transId)
    {
        ObjectNode node = newObjNode();
        node.put("result", 1);
        node.put("transId", transId.getValue());
        return node.toString();
    }

    public void rollbackAllUserTransactions()
    {
        rollbackAllTrans();
        cancelAllTimers();
    }

    private void rollbackAllTrans()
    {
        for (OdbOperation odbOperation : this.transactionMap.values())
        {
            try
            {
                odbOperation.rollback();
            }
            catch (Exception e)
            {
                log.info(rootExternalDN + " rollback user transaction failed !");
            }
        }
        transactionMap.clear();
    }

    private void cancelAllTimers()
    {
        for (Timer timer : timerMap.values())
        {
            timer.cancel();
        }
        this.timerMap.clear();
    }

    @Override
    public MoMetaClass getMeta(String name) throws MOSException
    {
        return getInstance(MetaStringStore.class).getMeta(
                modelHeader.modelName(), modelHeader.modelVersion(), name);
    }

    @Override
    public AfterGetter<ManagementObject, Mo> getAfterGetter(
            Class<? extends ManagementObject> clazz) throws MOSException
    {
        return getInstance(AfterGetters.class).get(clazz, pair(modelHeader.modelName(), modelHeader.modelVersion()));
    }

    public void hangUp()
    {
        this.db.renameWithPostfix("bak");
        ReferenceDBInBerkeley.hangUp(this);
    }

    public void restore()
    {
        try
        {
            this.db.delPostfix("bak");
            ReferenceDBInBerkeley.restore(this);
        }
        finally
        {
            regSmartLinkRouter();
        }
    }

    public void halt()
    {
        try
        {
            db.stop();
        }
        finally
        {
            ReferenceDBInBerkeley.halt(this);
        }
    }

    private boolean isSyncTrans(Maybe<Integer> transId)
    {
        return transId.equals(MOS.SYNC_TRANS_ID);
    }

    public void startSyncTransaction(Maybe<Integer> transId) throws MOSException
    {
        OdbOperation trans = transactionMap.get(transId.getValue());
        if (trans == null)
        {
            trans = startTransaction(transId.getValue());
            return;
        }
        throw new MOSException(transId + " already exists");
    }

    @Override
    public void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        ManagementObject mo = this.getMO(to, transactionID);
        if (mo != null)
        {
            ReferenceDBInBerkeley.get(this).deRef(to, from, transactionID);
        }
        else
        {
            logger(this.getClass()).info(to + " is not exist in " + transactionID);
        }
    }

    @Override
    public void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        ManagementObject mo = this.getMO(to, transactionID);
        if (mo != null)
        {
            ReferenceDBInBerkeley.get(this).ref(to, from, transactionID);
        }
        else
        {
            throw new NullMoException(to + " is not exist in " + transactionID);
        }
    }
}
