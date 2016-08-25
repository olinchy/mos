package com.zte.container.ext.switchmodel.tm.remote;

import com.zte.container.MoOperateType;
import com.zte.container.ext.ExtServiceProvider;
import com.zte.container.ext.switchmodel.ReferenceObjectsRepository;
import com.zte.container.ext.switchmodel.log.*;
import com.zte.container.ext.switchmodel.tm.ModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.TransactionType;
import com.zte.container.ext.total.SyncTool;
import com.zte.mos.container.BundleObject;
import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.imaged.MosFactory;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.message.ReferenceClass;
import com.zte.mos.message.Result;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;
import com.zte.mos.type.Pair;
import com.zte.mos.util.Visitor;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.action.ModelSwitchReq;
import com.zte.mos.util.structure.tree.MoTree;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.*;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/5/16.
 */
public class RemoteModelSwitchTransaction extends ModelSwitchTransaction
{
    private static Logger log = logger(RemoteModelSwitchTransaction.class);

    private final BundleObject bundleObject;
    private String newModelName;
    private String newModelVersion;
    private String oldModelName;
    private String oldModelVersion;

    private String targetId;
    private String ipAddress;
    private MoTree tree;
    private ReferenceObjectsRepository refRepo;
    private RawMos mos;
    private ImagedSystem sys;
    private LogMerger logMerger;
    private String netype;
    private NeDomain domain;
    private long key;


    public RemoteModelSwitchTransaction(ModelSwitchReq req, BundleObject bundleObject)
    {
        super();
        this.bundleObject = bundleObject;
        parse(req);
    }


    private void parse(ModelSwitchReq req)
    {
        Map map =  toMap(req);
        this.newModelName = String.valueOf(map.get("newModelName"));
        this.newModelVersion = String.valueOf(map.get("newModelVersion"));
        this.oldModelName = String.valueOf(map.get("oldModelName"));
        this.oldModelVersion = String.valueOf(map.get("oldModelVersion"));
        this.targetId = String.valueOf(map.get("targetId"));
        this.ipAddress = String.valueOf(map.get("ip"));
        this.tree = (MoTree)map.get("moTree");
        this.refRepo = (ReferenceObjectsRepository) map.get("refRepository");
        this.netype = String.valueOf(map.get("netype"));

    }



    public String getTargetId()
    {
        return targetId;
    }

    @Override
    public TransactionType transType()
    {
        return TransactionType.remote;
    }

    @Override
    public String getIpAddress()
    {
        return ipAddress;
    }

    private Map<String, Object> toMap(ModelSwitchReq req)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        for(Pair<String, Object> pair : req.paras())
        {
            map.put(pair.first(), pair.second());
        }
        return map;
    }


    @Override
    public void commit() throws MOSException
    {
        List<Log<ManagementObject>> logs = this.mos.commit(MOS.SWITCH_MODEL_TRANS_ID);
        ReferenceDBInBerkeley.get(mos).commit(MOS.SWITCH_MODEL_TRANS_ID);
        ReferenceDBInBerkeley.get(mos).refresh(logs);
        regBundleNeDomain();
        unlock();
        ConnectionState state = getCurrentConnectState();
        bindSouth(state);
        saveModelVersion(state);
        bindSmartLinkRouter();
        dispatchLogs();
    }



    void saveModelVersion(ConnectionState current) throws MOSException
    {
        domain.saveModel(this.newModelName, this.newModelVersion);
        domain.setModelState(current == ConnectionState.OFFLINE ? ModelState.unchecked : ModelState.checked);
    }

    @Override
    public void rollback() throws MOSException, RemoteException
    {
        unlock();
        deRegBundleRoute();
        deRegBundleNeDomain();

        if (this.mos != null)
        {
            this.mos.rollback(MOS.SWITCH_MODEL_TRANS_ID);
            ReferenceDBInBerkeley.get(mos).rollback(MOS.SWITCH_MODEL_TRANS_ID);
            this.mos.stop();
        }

    }

    @Override
    protected NeDomain getNeDomain()
    {
        return domain;
    }

    @Override
    protected BundleObject getBundleObject()
    {
        return bundleObject;
    }

    @Override
    protected String getNeDn()
    {
        return this.getTargetId();
    }

    @Override
    protected ImagedSystem getImageSystem()
    {
        return this.sys;
    }

    private MOS loadModels() throws MOSException
    {
        ModelMETA meta = ExtServiceProvider.getInstance().getModelService().get(newModelName, newModelVersion);
        this.mos = createRawMosInTrans(meta.modelHead, MOS.SWITCH_MODEL_TRANS_ID);
        this.sys = (ImagedSystem)MosFactory.createMOS(meta, this.mos);
        return (MOS)this.sys;
    }

    @Override
    public void start() throws MOSException, RemoteException
    {
        createNeDomain(loadModels());
        lock();
        regBundleRoute();
        regBundleNeDomain();
        initLogMerger();
        mergeAll();
        mergeRefsFromEms();
    }

    private void mergeRefsFromEms()
    {
        refRepo.walk(
                new Visitor<ReferenceObject>()
                {
                    @Override
                    public void visit(ReferenceObject referenceObject) throws MOSException
                    {
                        ReferenceDBInBerkeley db = ReferenceDBInBerkeley.get(mos);

                        for(String dn : referenceObject.refBy)
                        {
                            if (fromEms(dn))
                            {
                                db.ref(referenceObject.dn, new DN(dn), MOS.SWITCH_MODEL_TRANS_ID);
                            }
                        }
                    }
                    private boolean fromEms(String dn)
                    {
                        return dn.startsWith("/Ems/1");
                    }
                });
    }

    private void createNeDomain(MOS mos)
    {
        log.debug(this.targetId + " start create ne domain");
        this.domain = bundleObject.create(new NeIdentity(this.targetId, this.ipAddress, this.netype), mos);
        log.debug(this.targetId + " end create ne domain");

    }

    private void mergeAll() throws MOSException
    {
        log.debug(this.targetId + " start merge all moes");
        Queue<Mo> queue = new LinkedList<Mo>();
        queue.add(tree.getRoot());

        while(!queue.isEmpty())
        {
            Mo mo = queue.poll();
            List<Mo> list = doUserCallBack(mo);
            List<MoOperateLog> moOperateLogs = new ArrayList<MoOperateLog>();

            if (list.size() == 0)
            {
                mergeDeletedLogs(mo);
                continue;
            }
            try
            {
                if (preCheck(list))
                {
                    moOperateLogs = mergeMo(list, mo);
                }
                else
                {
                    if (!checkQueueMoes(queue))
                    {
                        break;
                    }
                    queue.add(mo);
                    continue;
                }
            }
            catch (Throwable e)
            {
                mergeDeletedLogs(mo);
                continue;
            }
            mergeUpdatedLogs(mo, list, moOperateLogs);
            addChildren(mo, queue);
        }

        mergeLeaveByMoLogs(queue);
        log.debug(this.targetId + " end merge all moes");

    }


    private void initLogMerger() throws MOSException
    {
        this.logMerger = new LogMerger(this.mos);
    }

    private void mergeLeaveByMoLogs(Queue<Mo> queue) throws MOSException
    {
        this.logMerger.mergeLog(ModelSwitchLogCreator.valueOf("leaveby").createLogs(new MergeLeaveByContext(queue, tree)));
    }

    private boolean checkQueueMoes(Queue<Mo> queue) throws MOSException
    {
        Mo[] moes = queue.toArray(new Mo[queue.size()]);

        for(Mo mo : moes)
        {
            if (preCheck(Arrays.asList(mo)))
            {
                return true;
            }
        }

        return false;
    }

    private boolean preCheck(List<Mo> list) throws MOSException
    {
        return preCheckParent(list) && preCheckRef(list);
    }

    private boolean isMoInTransaction(String dn, Maybe<Integer> transId) throws MOSException
    {
        return this.mos.getMO(dn , transId) != null;
    }


    private boolean preCheckParent(List<Mo> list) throws MOSException
    {
        for(Mo mo : list)
        {
            if (!isMoInTransaction(mo.getDn().parent().toString(), MOS.SWITCH_MODEL_TRANS_ID))
            {
                return false;
            }
        }
        return true;
    }

    private boolean preCheckRef(List<Mo> list) throws MOSException
    {
        for(Mo mo : list)
        {
            MoMetaClass meta = mo.getMeta();
            if (meta != null)
            {
                if (!preCheckMoRef(mo, meta))
                {
                    return false;
                }
                continue;
            }
        }
        return true;
    }

    private boolean preCheckMoRef(Mo mo, MoMetaClass meta) throws MOSException
    {
        List<String> refList = getAllReference(mo, meta);

        for(String ref : refList)
        {
            if (!isMoInTransaction(ref, MOS.SWITCH_MODEL_TRANS_ID))
            {
                return false;
            }
        }
        return true;
    }

    private List<String> getAllReference(Mo mo, MoMetaClass meta)
    {
        List<String> refs = new ArrayList<String>();

        for(ReferenceClass referenceClass : meta.reference)
        {
            if (referenceClass.isMulti)
            {
                List<String> refList = (List<String>)mo.get(referenceClass.name);

                if (refList != null)
                {
                    refs.addAll(refList);
                }
            }
            else
            {
                String ref = mo.get(referenceClass.name).toString();
                if (ref != null && !ref.isEmpty())
                {
                    refs.add(ref);
                }
            }
        }
        return refs;
    }

    private void dispatchLogs() throws MOSException
    {
        new LogDispatcher(logMerger, mos).dispatch();
    }


    private void mergeDeletedLogs(Mo mo) throws MOSException
    {
        this.logMerger.mergeLog(ModelSwitchLogCreator.valueOf("failed").createLogs(new MergeFailedContext(mo, tree)));
    }

    private void mergeUpdatedLogs(Mo origin, List<Mo> list,  List<MoOperateLog> moOperateLogs) throws MOSException
    {
        this.logMerger.mergeLog(ModelSwitchLogCreator.valueOf("success").createLogs(new MergeSuccessfulContext(origin, moOperateLogs, this.mos)));
    }

    private List<MoOperateLog> mergeMo(List<Mo> list, Mo origin) throws MOSException
    {
        List<MoOperateLog> mergeResult = new ArrayList<MoOperateLog>();
        try
        {
            for (Mo temp : list)
            {
                Class<?> clazz = getMetaClass(temp.getMoClass());

                if (isGroup(temp.getMoClass(), clazz))
                {
                    mergeResult.add(new MoOperateLog(temp, MoOperateType.na));
                    continue;
                }
                ManagementObject mo = (ManagementObject) temp.to(clazz);
                mo.setDn(temp.getDn().toString());
                ManagementObject local = mos.getMO(mo.dn().toString(), MOS.SWITCH_MODEL_TRANS_ID);
                MoOperateType operateType = SyncTool.getSwitchMoOperateType(
                        (BaseManagementObject) local, (BaseManagementObject) mo);

                mergeResult.add(new MoOperateLog(temp, operateType));

                switch (operateType)
                {
                    case add:
                        mos.createMO(initMo(mo, mos), MOS.SWITCH_MODEL_TRANS_ID);
                        break;
                    case set:
                        mergeAttributesAndRef((BaseManagementObject) local, (BaseManagementObject) mo);
                        mos.updateMO(initMo(local, mos), null, MOS.SWITCH_MODEL_TRANS_ID);
                        break;
                    case replace:
                        deleteMo(initMo(local, mos));
                        mos.createMO(initMo(mo, mos), MOS.SWITCH_MODEL_TRANS_ID);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (Throwable e)
        {
            for(Mo temp : list)
            {
                deleteMo(temp.getDn().toString());
            }
            throw new MOSException(this.targetId + " fail to merge mo " + origin.getDn().toString(), e);
        }
        return mergeResult;

    }

    private boolean isGroup(String moClassName, Class<?> clazz)
    {
        return ( moClassName.equalsIgnoreCase("Group") || (clazz != null && GroupOf.class.isAssignableFrom(clazz)));
    }

    private void deleteMo(String dn) throws MOSException
    {
        try
        {
            mos.deleteMO(dn, MOS.SWITCH_MODEL_TRANS_ID);
        }
        catch (NullMoException e)
        {
            log.error("fail to delete unexisted mo " + dn, e);
        }
    }

    private void deleteMo(ManagementObject mo) throws MOSException
    {
        deleteMo(mo.dn().toString());
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

    private ManagementObject initMo(ManagementObject mo, RawMos mos)
    {
        mo.setMos(mos);
        return mo;
    }



    private void addChildren(Mo mo, Queue<Mo> queue)
    {
        queue.addAll(tree.getChildren(mo));
    }

    private RawMos createRawMosInTrans(ModelHead modelHead, Maybe<Integer> transId) throws MOSException
    {
        return new RawMos(this.getTargetId(), modelHead, "BUNDLE", MOS.SWITCH_MODEL_TRANS_ID);
    }

    private List<Mo> doUserCallBack(Mo mo)
    {
        return Arrays.asList(mo);
    }

    private Class<?> getMetaClass(String simpleClassName) throws MOSException
    {
        return getInstance(MetaStringStore.class).getClass(this.newModelName, this.newModelVersion, simpleClassName);
    }

    private void bindSmartLinkRouter() throws MOSException
    {
        this.mos.regSmartLinkRouter();
    }

}
