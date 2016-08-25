package com.zte.container.ext.total;

import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.ODBWalker;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.zte.container.MoOperateType;
import com.zte.container.ext.exception.InvalidReferenceException;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.SLRouterPool;
import com.zte.mos.service.impl.ReservedMoService;
import com.zte.mos.storage.temp.TempOdb;
import com.zte.mos.util.basic.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static com.zte.container.ext.total.ReverseSyncLogger.logReverseSync;
import static com.zte.container.ext.total.config.ReverseConfig.isFault_tolerant;
import static com.zte.mos.util.basic.Logger.logger;

public class TotalSynchor
{
    private static Logger log = logger(TotalSynchor.class);
    private RawMos mos;
    private ModelHead modelHead;

    public TotalSynchor(RawMos mos, ModelHead modelHead)
    {
        this.mos = mos;
        this.modelHead = modelHead;//
    }

    public void save(ManagementObject[] moes) throws Throwable
    {
        if (check(moes))
        {
            long start = System.currentTimeMillis();
            FastImagedDB fastImagedDB = buildFastImagedDB(moes);
            doSync(fastImagedDB, new DbDiffImpl(fastImagedDB, mos));
            long elapse = (System.currentTimeMillis() - start) / 1000;
            log.debug("total reverse synchor suceeds, it takes " + elapse + " s");
        }
    }

    private void doSync(FastImagedDB imagedDB, DbDiffImpl diff) throws Throwable
    {
        long start = System.currentTimeMillis();
        try
        {
            startSyncTrans();
            mergeSharedMoes(imagedDB);
            delRemoteUnExistedMoes(diff.diffs());
            log.debug("total reverse doSync suceeds, it takes " + (System.currentTimeMillis() - start) / 1000 + " s");
            LinkedList<Log<ManagementObject>> logs = this.mos.commit(MOS.SYNC_TRANS_ID);
            SLRouterPool.getRouter(mos.getRootExternalDN()).noti(logs);
            ReferenceDBInBerkeley.get(mos).commit(MOS.SYNC_TRANS_ID);
            ReferenceDBInBerkeley.get(mos).refresh(logs);

        }
        catch (Throwable e)
        {
            log.error("total reverse doSync fails, it takes " + (System.currentTimeMillis() - start) / 1000 + " s", e);
            rollback();
            throw e;
        }
    }

    private void rollback()
    {
        try
        {
            mos.rollback(MOS.SYNC_TRANS_ID);
        }
        catch (Throwable e)
        {
            logger(this.getClass()).debug(mos.getRootExternalDN() + " fail to roll back sync transaction", e);
        }
        finally
        {
            ReferenceDBInBerkeley.get(mos).rollback(MOS.SYNC_TRANS_ID);
        }

    }

    private boolean check(ManagementObject[] moes)
    {
        return moes != null && moes.length > 0;
    }

    public void startSyncTrans() throws MOSException
    {
        mos.startSyncTransaction(MOS.SYNC_TRANS_ID);
    }

    public boolean containsRoot(ManagementObject[] moes)
    {
        return moes[0].dn().toString().equalsIgnoreCase(MOS.ROOT_INTERNAL_DN);
    }

    private void mergeSharedMoes(FastImagedDB fastImagedDB) throws Exception
    {
        long start = System.currentTimeMillis();
        Queue<ManagementObject> moes = fastImagedDB.moes();
        log.debug(mos.getRootExternalDN() + " start merge shared moes, size = " + moes.size());
        for (ManagementObject mo : moes)
        {
            try
            {
                if (isOperatable(mo))
                {
                    merge(mo);
                }
            }
            catch (Throwable e)
            {
                if (isFault_tolerant())
                {
                    log.error(mos.getRootExternalDN() + " skip reverse sync error in mergeSharedMoes , mo dn " + mo.dn().toString(), e);
                    continue;
                }
                throw new Exception(e);
            }
        }
        long elapse = (System.currentTimeMillis() -  start) /1000;
        log.debug(mos.getRootExternalDN() + " merge shared moes takes " + elapse + " s");
    }

    private void merge(ManagementObject mo) throws Exception
    {
        long start = 0;
        ManagementObject local = mos.getMO(mo.dn().toString(), MOS.SYNC_TRANS_ID);

        if (local != null && local.dn().toString().equalsIgnoreCase("/SysPara/1/DevicePara/1"))
        {
            new DeviceParaDumper(mos.getRootExternalDN(), "from local before merge ", local);
        }


        MoOperateType operateType = SyncTool.getMoOperateType((BaseManagementObject) local, (BaseManagementObject) mo);

        switch (operateType)
        {
            case add:
                start = System.currentTimeMillis();
                mos.createMO(initMo(mo, mos), MOS.SYNC_TRANS_ID);
                logReverseSync(log, mos.getRootExternalDN(), " mergeMoes create mo " + mo.dn(), System.currentTimeMillis() - start);
                break;
            case set:
                start = System.currentTimeMillis();
                mergeAttributesAndRef((BaseManagementObject) local, (BaseManagementObject) mo);
                mos.updateMO(initMo(local, mos), null, MOS.SYNC_TRANS_ID);
                if (mo.dn().toString().equalsIgnoreCase("/SysPara/1/DevicePara/1"))
                {
                    new DeviceParaDumper(mos.getRootExternalDN(), "from local after merge ", local);
                }
                logReverseSync(log, mos.getRootExternalDN(), " mergeMoes update mo " + mo.dn(), System.currentTimeMillis() - start);
                break;
            case del:
                start = System.currentTimeMillis();
                deleteMo(initMo(local, mos));
                logReverseSync(log, mos.getRootExternalDN(), " mergeMoes delete mo " + mo.dn(), System.currentTimeMillis() - start);
                break;
            case replace:
                start = System.currentTimeMillis();
                deleteMo(initMo(local, mos));
                mos.createMO(initMo(mo, mos), MOS.SYNC_TRANS_ID);
                logReverseSync(log, mos.getRootExternalDN(), " mergeMoes replace mo " + mo.dn() + " oldType" + local.getClass().getName() + " newType " + mo.getClass().getName(), System.currentTimeMillis() - start);
                break;
            default:
                break;
        }

        if (mo.dn().toString().equalsIgnoreCase("/SysPara/1/DevicePara/1"))
        {
            new DeviceParaDumper(mos.getRootExternalDN(), "from transaction after merge ", mos.getMO("/SysPara/1/DevicePara/1", MOS.SYNC_TRANS_ID));
        }
    }

    private ManagementObject initMo(ManagementObject mo, RawMos mos)
    {
        mo.setMos(mos);
        return mo;
    }

    private void deleteMo(ManagementObject mo) throws Exception
    {
        try
        {
            mos.deleteMO(mo.dn().toString(), MOS.SYNC_TRANS_ID);
        }
        catch (NullMoException e)
        {
            log.error(mos.getRootExternalDN() + " fail to delete unexisted mo " + mo.dn().toString(), e);
        }
    }

    private void mergeAttributesAndRef(BaseManagementObject local, BaseManagementObject remote) throws Exception
    {
        Field[] fields = local.getClass().getFields();
        for (Field f : fields)
        {
            if (SyncTool.isValidAttribute(f))
            {
                f.set(local, f.get(remote));
            }
        }
    }

    private void update(ManagementObject local, ManagementObject remote) throws MOSException
    {
        try
        {
            setAttributesAndRef((BaseManagementObject) local, (BaseManagementObject) remote);
            mos.updateMO(local, null, MOS.SYNC_TRANS_ID);
        }
        catch (IllegalAccessException e)
        {
            log.error("fail to update mo, dn " + local.dn().toString(), e);
            throw new MOSException("IllegalAccessException", e);
        }
    }

    private void setAttributesAndRef(BaseManagementObject to, BaseManagementObject from) throws IllegalAccessException
    {
        Field[] fields = to.getClass().getFields();
        for (Field f : fields)
        {
            if (SyncTool.isValidAttribute(f))
            {
                if (!SyncTool.isFieldEquals(f, to, from))
                {
                    f.set(to, f.get(from));
                }
            }
        }
    }

    private void delRemoteUnExistedMoes(Queue<ManagementObject> diffs) throws MOSException
    {
        long start = System.currentTimeMillis();
        for (ManagementObject mo : diffs)
        {
            String dn = mo.dn().toString();
            if (isOperatable(mo))
            {
                log.debug("delete mo " + dn);
                try
                {
                    mos.deleteMO(dn, MOS.SYNC_TRANS_ID);
                }
                catch (Throwable e)
                {
                    if (isFault_tolerant())
                    {
                        log.error(mos.getRootExternalDN() + " skip reverse sync error in delRemoteUnExistedMoes, mo dn " + mo.dn().toString(), e);
                        continue;
                    }
                    throw new MOSException(e);
                }
            }
        }
        long elapse = (System.currentTimeMillis() - start)/1000;
        log.debug(mos.getRootExternalDN() + " del remote unexisted moes takes" + elapse + " s");
    }

    private boolean isOperatable(ManagementObject mo)
    {
        return !ReservedMoService.isReserved(this.modelHead.modelName(), this.modelHead.modelVersion(), mo);
    }


    private TempOdb buildTempOdb(ManagementObject[] moes) throws Exception
    {
        long start = System.currentTimeMillis();
        long currentTime = 0;
        ManagementObject root = modelHead.rootClass().newInstance();
        root.setDn(MOS.ROOT_INTERNAL_DN);
        TempOdb odb = new TempOdb();
        odb.create(initMo(root, mos));
        log.debug(mos.getRootExternalDN() + " build temp odb create root takes " + (System.currentTimeMillis() - start) + "ms");
        mergeOdbRoot(odb, moes);

        start = System.currentTimeMillis();
        for (ManagementObject mo : moes)
        {
            ManagementObject temp = odb.get(mo.dn());
            try
            {
                if (temp == null)
                {
                    currentTime = System.currentTimeMillis();
                    odb.create(initMo(mo, mos));
                    logReverseSync(log, mos.getRootExternalDN(), " buildTempOdb create mo " + mo.dn(), System.currentTimeMillis() - currentTime);
                }
                else
                {
                    if (temp.getClass() == mo.getClass())
                    {
                        currentTime = System.currentTimeMillis();
                        setAttributesAndRef((BaseManagementObject) temp, (BaseManagementObject) mo);
                        odb.update(initMo(temp, mos));
                        logReverseSync(log, mos.getRootExternalDN(), " buildTempOdb update mo " + mo.dn(), System.currentTimeMillis() - currentTime);

                    }
                    else
                    {
                        currentTime = System.currentTimeMillis();
                        odb.delete(initMo(temp, mos));
                        odb.create(initMo(mo, mos));
                        logReverseSync(log, mos.getRootExternalDN(), " buildTempOdb replace mo " + mo.dn(), System.currentTimeMillis() - currentTime);
                    }
                }
            }
            catch (Throwable e)
            {
                if (isFault_tolerant())
                {
                    log.error(mos.getRootExternalDN() + " skip reverse sync error in buildTempOdb, mo dn " + mo.dn().toString(), e);
                    continue;
                }
                throw new Exception(e);
            }
        }
        odb.commit();
        log.debug(mos.getRootExternalDN() + " build temp odb from or takes " + (System.currentTimeMillis() - start) + " ms");
        return odb;
    }

    private void mergeOdbRoot(TempOdb imageDb, ManagementObject[] moes) throws Exception
    {
        if (moes.length == 0 || moes[0].dn().toString().equalsIgnoreCase(MOS.ROOT_INTERNAL_DN))
        {
            return;
        }
        Queue<ManagementObject> queue = new LinkedList<ManagementObject>();
        queue.add(imageDb.get(new DN(MOS.ROOT_INTERNAL_DN)));

        while (!queue.isEmpty())
        {
            ManagementObject mo = queue.poll();
            ManagementObject odbMo = mos.getMO(mo.dn().toString(), new Maybe<Integer>());
            if (odbMo != null)
            {
                if (odbMo.getClass() == mo.getClass())
                {
                    setAttributesAndRef((BaseManagementObject) mo, (BaseManagementObject) odbMo);
                    addChildren(queue, mo, imageDb);
                }
                else
                {
                    log.debug(
                            "auto created root is mismatched with current odb , mo dn " + mo.dn().toString()
                                    + " mo type " + mo.getClass().getName() + " odb mo type " + odbMo.getClass().getName());
                }
            }
        }
    }

    private FastImagedDB buildFastImagedDBWithRoot(ManagementObject[] moes) throws Exception
    {
        long start = System.currentTimeMillis();
        FastImagedDB db = new FastImagedDB(moes[0]);
        for (ManagementObject mo : moes)
        {
            db.put(mo);
        }
        long elapse = (System.currentTimeMillis() - start) / 1000;
        log.debug(mos.getRootExternalDN() + " build fast image db takes " + elapse + " s");
        return db;
    }


    private FastImagedDB buildFastImagedDBWithOutRoot(ManagementObject[] moes) throws Exception
    {
        long start = System.currentTimeMillis();
        TempOdb odb = buildTempOdb(moes);
        FastImagedDB db = new FastImagedDB();
        Queue<ManagementObject> queue = new LinkedBlockingQueue<ManagementObject>();
        queue.add(odb.get(new DN(MOS.ROOT_INTERNAL_DN)));
        db.setRoot(odb.get(new DN(MOS.ROOT_INTERNAL_DN)));

        while (!queue.isEmpty())
        {
            ManagementObject mo = queue.poll();
            if (mo.dn().toString().equalsIgnoreCase("/SysPara/1/DevicePara/1"))
            {
                new DeviceParaDumper(mos.getRootExternalDN(), "from or ", mo);
            }
            try
            {
                List<Field> refFields = getRefFields(mo.getClass());
                if (refFields.size() > 0)
                {
                    if (checkRefFields(refFields, mo, db.dns()))
                    {
                        db.put(mo);
                        addChildren(queue, mo, odb);
                    }
                    else
                    {
                        checkQueueRefs(queue, mo, db.dns());
                        queue.add(mo);
                    }
                }
                else
                {
                    db.put(mo);
                    addChildren(queue, mo, odb);
                }
            }
            catch (Throwable e)
            {
                if (isFault_tolerant())
                {
                    log.error(mos.getRootExternalDN() + " skip reverse sync error in buildFastImagedDB, mo dn " + mo.dn().toString(), e);
                    continue;
                }
                throw new Exception(e);
            }
        }
        long elapse = (System.currentTimeMillis() - start) / 1000;
        log.debug(mos.getRootExternalDN() + " build fast image db takes " + elapse + " s");
        return db;
    }

    private FastImagedDB buildFastImagedDB(ManagementObject[] moes) throws Throwable
    {
        long start = System.currentTimeMillis();
        FastImagedDB db = (containsRoot(moes) ? buildFastImagedDBWithRoot(moes) : buildFastImagedDBWithOutRoot(moes));
        long elapse = (System.currentTimeMillis() - start)/1000;
        log.debug("total reverse build fast image db, it takes " + elapse + " s");
        return db;
    }

    private void checkQueueRefs(
            Queue<ManagementObject> queue, ManagementObject current, Set<String> dns) throws Exception
    {
        ManagementObject[] moes = queue.toArray(new ManagementObject[queue.size()]);
        StringBuffer buffer = new StringBuffer();
        buffer.append("current mo : " + current.dn() + " ref to");

        for (Field field : getRefFields(current.getClass()))
        {
            buffer.append(" ").append(field.get(current));
        }
        buffer.append("\r\n");

        for (ManagementObject mo : moes)
        {
            List<Field> refFields = getRefFields(mo.getClass());

            if (refFields.size() == 0)
            {
                return;
            }
            if (checkRefFields(refFields, mo, dns))
            {
                return;
            }
            buffer.append(" queue mo " + mo.dn() + " ref to ");

            for (Field field : refFields)
            {
                buffer.append(" ").append(field.get(mo));
            }
        }
        throw new InvalidReferenceException(buffer.toString());
    }

    private boolean checkRefFields(
            List<Field> refFields, ManagementObject mo, Set<String> dns) throws MOSException, IllegalAccessException
    {

        for (Field f : refFields)
        {
            List<String> refs = getRefs(f, mo);
            for (String ref : refs)
            {
                if (!dns.contains(ref))
                {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Field> getRefFields(Class<? extends ManagementObject> clazz)
    {
        List<Field> list = new ArrayList<Field>();

        Field[] fields = clazz.getFields();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(MoReference.class))
            {
                list.add(f);
            }
        }
        return list;
    }

//    FastImagedDB resembleDb(FastImagedDB original)
//    {
//        Queue<ManagementObject> queue = new LinkedList<ManagementObject>();
//        FastImagedDB sorted = new FastImagedDB(original.getRoot());
//        queue.addAll(original.moes());
//
//        while(!queue.isEmpty())
//        {
//            ManagementObject mo = queue.poll();
//
//            if (refValidation(mo, sorted))
//            {
//                sorted.put(mo);
//            }
//            else
//            {
//                queue.add(mo);
//            }
//        }
//        return sorted;
//    }

//    private boolean refValidation(ManagementObject mo,  FastImagedDB sorted)
//    {
//        if (!hasRefAttribute(mo))
//        {
//            return true;
//        }
//
//        return _checkEachRefMo(mo, sorted.dns());
//    }
//
//    private boolean hasRefAttribute(ManagementObject mo)
//    {
//        Field[] fields = mo.getClass().getFields();
//        for (Field f : fields)
//        {
//            if (f.isAnnotationPresent(MoReference.class))
//            {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void checkRefMo(Queue<ManagementObject> queue, HashSet<String> dns) throws Exception
//    {
//        for(ManagementObject mo : queue)
//        {
//            checkEachRefMo(mo, dns);
//        }
//    }
//
//    private boolean _checkEachRefMo(ManagementObject mo, HashSet<String> dns)
//    {
//        Field[] fields = mo.getClass().getFields();
//
//        for (Field f : fields)
//        {
//            if (f.isAnnotationPresent(MoReference.class))
//            {
//                try
//                {
//                    List<String> refs = getRefs(f, mo);
//                    for(String ref : refs)
//                    {
//                        if (!dns.contains(ref))
//                        {
//                            return false;
//                        }
//                    }
//
//                }
//                catch (Throwable e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }
//
//    private void checkEachRefMo(ManagementObject mo, HashSet<String> dns) throws Exception
//    {
//        if (!_checkEachRefMo(mo, dns))
//        {
//            throw new MOSException("fail to check reference,  mo " + mo.dn().toString());
//        }
//    }

    private List<String> getRefs(Field f, ManagementObject mo) throws IllegalAccessException, MOSException
    {
        List<String> results = new ArrayList<String>();
        MoReference reference = f.getAnnotation(MoReference.class);

        if (reference.isMulti())
        {
            List<String> list = (List) f.get(mo);
            if (list != null)
            {
                for (String each : list)
                {
                    if (isValidRef(each))
                    {
                        results.add(each);
                    }
                }

                if (results.size() != 0 && results.size() != list.size())
                {
                    throw new MOSException("fail to parse ref " + mo.dn().toString());
                }
            }
        }
        else
        {
            String ref = f.get(mo).toString();
            if (isValidRef(ref))
            {
                results.add(ref);
            }
        }
        return results;
    }

    private boolean isValidRef(String ref)
    {
        return (ref != null && !ref.isEmpty());
    }

    private void addChildren(Queue<ManagementObject> queue, ManagementObject parent, TempOdb odb) throws MOSException
    {
        List<String> children = parent.lsDN();
        for (String child : children)
        {
            queue.add(odb.get(new DN(child)));
        }
    }

    class DbDiffImpl
    {
        private final Queue<ManagementObject> diff = new LinkedList<ManagementObject>();

        DbDiffImpl(FastImagedDB imagedDB, RawMos mos) throws Exception
        {
            compare(imagedDB, mos);
        }

        private void compare(FastImagedDB imagedDB, RawMos mos) throws Exception
        {
            long start = System.currentTimeMillis();
            Set<String> current = findAllMoes(mos);
            if (current.removeAll(imagedDB.dns()))
            {
                for(String dn : current)
                {
                    try
                    {
                        diff.add(mos.getMO(dn, new Maybe<Integer>()));
                    }
                    catch (Throwable e)
                    {
                        log.error(mos.getRootExternalDN() + " fail to get mo, dn" + dn);
                    }
                }
            }
            else
            {
                log.debug(mos.getRootExternalDN() + " fail to do db compare");
            }
            long elapse = System.currentTimeMillis() - start;
            log.debug(mos.getRootExternalDN() + " do db diff takes " + elapse + " ms");
        }

        public Queue<ManagementObject> diffs()
        {
            return diff;
        }

        public Set<String> findAllMoes(RawMos mos)
        {
            final Set<String> set = new ConcurrentHashSet<String>();
            try
            {
                mos.all(
                        new ODBWalker()
                        {
                            @Override
                            public boolean contains(Object data)
                            {
                                return false;
                            }

                            @Override
                            public void remove(Object data)
                            {

                            }

                            @Override
                            public void add(Object data) throws MOSException
                            {

                            }

                            @Override
                            public void walk(BerkeleyDBIndexer indexer) throws MOSException
                            {
                                CursorConfig config = new CursorConfig();
                                config.setReadUncommitted(true);
                                Cursor cursor = null;
                                try
                                {
                                    DatabaseEntry keyEntry = new DatabaseEntry();
                                    DatabaseEntry dataEntry = new DatabaseEntry();
                                    cursor = indexer.getDatabase().openCursor(null, config);

                                    while (OperationStatus.SUCCESS.equals(
                                            cursor.getNext(
                                                    keyEntry, dataEntry, LockMode.DEFAULT)))
                                    {
                                        String dn = StringBinding.entryToString(keyEntry);
                                        if (dn != null && !dn.isEmpty())
                                        {
                                            set.add(dn);
                                        }
                                    }
                                }
                                finally
                                {
                                    if (cursor != null)
                                    {
                                        cursor.close();
                                    }
                                }

                            }
                        }, new Maybe<Integer>());
            }
            catch(Throwable e)
            {
                log.error(mos.getRootExternalDN() + " fail to findAllMoes", e);
            }
            return set;
        }
    }
}

class DeviceParaDumper
{
    DeviceParaDumper(String root, String desc, ManagementObject mo)
    {
        this.root = root;
        this.desc = desc;
        this.mo = mo;
        dump();
    }

    private final String root;
    private final String desc;
    private final ManagementObject mo;


    private void dump()
    {
        logger(this.getClass()).debug(root + " start dump, type " + mo.getClass().getSimpleName() + " ip " + get("ip") + " neName " + get("neName") + " " + desc);
    }

    private String get(String fieldName)
    {
        Field[] fields = mo.getClass().getFields();
        for(Field field : fields)
        {
            if (field.getName().equalsIgnoreCase(fieldName))
            {
                try
                {
                    return String.valueOf(field.get(mo));
                }
                catch (IllegalAccessException e)
                {
                    logger(this.getClass()).error(root + " fail to get field " + fieldName, e);
                }
            }
        }
        return "null";
    }
}


class ReverseSyncLogger
{
    private static final long TIME_THRESHOLD = 50;

    public static void logReverseSync(Logger logger, String mos, String desc, long costTime)
    {
        if (reachThreshOld(costTime))
            logger.debug(new StringBuffer().append(mos).append(desc).append(" takes ").append(costTime).append(" ").append("ms").toString());
    }

    private static boolean reachThreshOld(long time)
    {
        return time >= TIME_THRESHOLD;
    }
}


