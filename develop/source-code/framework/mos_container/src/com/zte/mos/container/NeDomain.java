package com.zte.mos.container;

import com.services.IPTarget;
import com.zte.concept.AbstractDomain;
import com.zte.concept.DomainOperation;
import com.zte.concept.DomainState;
import com.zte.concept.NeDecTable;
import com.zte.container.ext.ExtServiceProvider;
import com.zte.container.ext.switchmodel.ModelSwitchContext;
import com.zte.container.ext.switchmodel.ModelSwitchTask;
import com.zte.mos.domain.*;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.ModelNotSpecException;
import com.zte.mos.imaged.MosFactory;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.task.TaskScheduler;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoFindMsg;

import java.net.MalformedURLException;

import static com.zte.concept.DomainOperation.*;
import static com.zte.concept.DomainState.*;
import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_DOMAIN;
import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_MODEL;
import static com.zte.mos.util.basic.Logger.logger;

public class NeDomain extends AbstractDomain implements IPTarget {
    private static Logger log = logger(NeDomain.class);

    public void setState(DomainState state)
    {
        this.state = state;
    }

    private DomainState state = Created;
    private int transId = Integer.MIN_VALUE;

    private String ipAddress;
    private String backedupIpAddress;

    private final String neType;

    private ModelState modelState = ModelState.unchecked;


    private ConnectionState connectionState = ConnectionState.UNKNOWN;

    private NeDomain(NeIdentity identity)
    {
        super(identity.dn, null);
        ipAddress = identity.ip;
        neType    = identity.netype;
    }


    public static NeDomain newInstance(NeIdentity identity)
    {
        return new NeDomain(identity);
    }

    static NeDomain recover(NeIdentity identity) throws Exception
    {
        log.info("start to recover ne domain " + identity.toString());
        NeDomain ne = new NeDomain(identity);
        DataUnit unit = MapDbService.getDB(NE_MODEL).get(identity.dn);
        if (unit != null)
        {
            String modelName = unit.getData().get("modelName");
            String modelVersion = unit.getData().get("modelVersion");
            ne.recoverMos(modelName, modelVersion);
        }
        ne.state = DomainState.Committed;
        return ne;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    @Override
    public String getNeType()
    {
        return this.neType;
    }

    public ModelState getModelState()
    {
        return modelState;
    }

    public ConnectionState getConnectionState()
    {
        return connectionState;
    }

    public void setConnectionState(ConnectionState state)
    {
        this.connectionState = state;
        updateSouthServiceConnectState();
    }

    private void updateSouthServiceConnectState() {
        try {
            CommServiceFactory.getService().setConnectSwitch(this.ID, connectionState);
        } catch (UnregisteredException e) {
            log.info("the southservice has not been unregistered yet!");
            log.info(e.getMessage(), e);
        }
    }

    public void updateDateState(DataState state)
    {
        if (mos != null)
        {
            getSystem().setDataState(state);
        }
    }


    public ImagedSystem getSystem()
    {
        return (ImagedSystem) mos;
    }


    @Override
    public Result onMsg(MoMsg msg)
    {
        if (mos == null)
        {
            return new Failure(1, new ModelNotSpecException(msg.dn()), msg.getTransactionID());
        }
        Tools.cutMsgHead(msg, mos.getRootExternalDN());
        return super.onMsg(msg);
    }

    public void updateIP(String ip, int transId) throws MOSException
    {
        logger(RealBundleDomain.class).debug("start start lock in updateIP, dn " + this.getID() + " transId " + transId);
        lock(transId);
        this.ipAddress = ip;
        onOperation(update);
        if (mos != null)
        {
            TargetAddress target = new TargetAddressImpl(ID, ipAddress, getSystem());
            try
            {
                CommServiceFactory.getService().modifyIp(target);
            }
            catch (MalformedURLException e)
            {
                logger(RealBundleDomain.class).error("fail to unlock in updateIP, dn " + this.getID() + " transId " + transId, e);
                throw new MOSException(e);
            }
        }
        logger(RealBundleDomain.class).debug("end start lock in updateIP, dn " + this.getID() + " transId " + transId);

    }

    private void createMosWithSouthbound(String name, String version) throws MOSException, MsgFrameException
    {
        mos = MosFactory.createMOS(this.ID, name, version);
        saveModel(name, version);
        bindSouth();
    }

    private void bindSouth() throws MsgFrameException
    {
        ImagedSystem sys = (ImagedSystem)this.mos;
        TargetAddress target = new TargetAddressImpl(ID, ipAddress, sys);
        CommServiceFactory.getService().register(target);
        updateSouthServiceConnectState();
    }



    private void replaceMosWithSouthbound(String name, String version) throws MOSException, MsgFrameException
    {
        this.mos.stop();
        this.mos = MosFactory.createMOS(this.ID, name, version);
        saveModel(name, version);
        bindSouth();
    }

    public void saveModel(String name, String version) throws MOSException
    {
        DataUnit unit = new DataUnit(ID);
        unit.put("modelName", name);
        unit.put("modelVersion", version);
        MapDbService.getDB(NE_MODEL).put(unit);
    }


    public void setModelState(ModelState state){
        this.modelState = state;
    }

    public void delete(){
        onOperation(delete);
    }

    protected Result dealInLocal(MoMsg msg, DN dn) throws MOSException {
        return MoCommandExecutor.execute(msg, mos);
    }

    public DomainState getState() {
        return state;
    }

    private void onOperation(DomainOperation oper){
        state = NeDecTable.getNextState(state, oper);
    }

    public void commit(int transId) throws MOSException
    {
        if (isLockedBy(transId))
        {
            unlock(transId);
            if (state == Created || state == Updated || state == Replaced)
            {
                saveNeDomain();
            }
            else if (state == Deleted)
            {
                destroyNeDomain();
            }
            onOperation(commit);
            return;
        }
        log.info("ne " + this.getIpAddress() + "fail to get lock by transId " + transId);
    }

    protected void saveNeDomain() throws MOSException
    {
        DataUnit unit = new DataUnit(this.ID);
        unit.put("ip", ipAddress);
        unit.put("netype", neType);
        MapDbService.getDB(NE_DOMAIN).put(unit);
        backedupIpAddress = ipAddress;
    }

    protected void destroyNeDomain()
    {
        try
        {
            MapDbService.getDB(NE_DOMAIN).delete(this.ID);
            MapDbService.getDB(NE_MODEL).delete(this.ID);
            if (mos != null)
            {
                CommServiceFactory.getService().unRegister(this.ID);
                mos.stop();
            }
        }
        catch (Throwable e)
        {
            log.error(this.ID + " fail to destroy ne domain " , e);
        }
    }

    public void rollback(int transId) throws MOSException
    {
        if (isLockedBy(transId))
        {
            unlock(transId);
            onOperation(rollback);
            if (state == Empty)
            {
                destroyNeDomain();
            }
            else if (state == Committed)
            {
                if (backedupIpAddress != ipAddress)
                {
                    rollbackIP();
                }
            }
        }
    }

    private void rollbackIP() throws MOSException {
        if (mos != null){
            this.ipAddress = backedupIpAddress;
            TargetAddress target = new TargetAddressImpl(ID, backedupIpAddress, getSystem());
            try {
                CommServiceFactory.getService().modifyIp(target);
            } catch (MalformedURLException e) {
                throw new MOSException(e);
            }
        }
    }


    @Override
    protected Result dealAckInLocal(MoAckMsg ack) throws MOSException {
        Tools.cutMsgHead(ack, mos.getRootExternalDN());
        return MoCommandExecutor.execute(ack, mos);
    }

    @Override
    public Result forward(MoMsg msg)
    {
        return null;
    }


    public Result ack(MoAckMsg ack) throws MOSException
    {
        return MoCommandExecutor.execute(ack, mos);
    }

    public void lock(int transId) throws LockedByTransException {
        if (!isLocked()){
            this.transId = transId;
        }else if(!isLockedBy(transId)){
            throw new LockedByTransException("already locked by " + this.transId);
        }
    }

    private void unlock(int transId) throws MOSException {
        if (isLockedBy(transId)){
            this.transId = Integer.MIN_VALUE;
        }else{
            throw new MOSException("locked by " + this.transId);
        }
    }

    public boolean isLockedBy(int transId){
        return this.transId == transId;
    }


    private boolean isLocked(){
        return this.transId != Integer.MIN_VALUE;
    }

    @Override
    public boolean localSupported(DN dn) {
        return true;
    }

    @Override
    public boolean localOperable(DN dn, MoCmds cmd) {
        return false;
    }

    @Override
    public FindResult broadcast(MoFindMsg msg) {
        try {
            Tools.cutMsgHead(msg, mos.getRootExternalDN());
            return (FindResult)MoCommandExecutor.execute(msg, mos);
        } catch (Exception e) {
            return new FindResult(msg.getTransactionID());
        }
    }

    private void startMos(String name, String version) throws MOSException, MsgFrameException
    {
        log.debug("start startMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]");
        createMosWithSouthbound(name, version);
        log.debug("end startMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]" + " NeDomainInst " + this + " MosInst " + this.mos);

    }

    private void replaceMos(String name, String version) throws MOSException, MsgFrameException
    {
        log.debug("start replaceMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]" + " NeDomainInst " + this + " MosInst " + this.mos);
        replaceMosWithSouthbound(name, version);
        log.debug("end replaceMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]"+ " NeDomainInst " + this + " MosInst " + this.mos);

    }

    private void switchMos(String name, String version, BundleService local)
    {
        log.debug("start switchMos " + this.getID() + " [modelName: " + name + " modelVersion: " +  getSystem().getModelMETA().modelHead.modelVersion() + " -> " + version + " ]");
        TaskScheduler.addTask(new ModelSwitchTask(new ModelSwitchContext(name, version, local, this)));
        log.debug("end switchMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]" + " NeDomainInst " + this + " MosInst " + this.mos);

    }

    private void recoverMos(String name, String version) throws UnsupportedProtocolException, MOSException
    {
        log.debug("start recoverMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]");
        this.mos = MosFactory.createMOS(this.ID, name, version);
        TargetAddress target = new TargetAddressImpl(this.ID, this.ipAddress, (ImagedSystem)mos);
        CommServiceFactory.getService().recover(target);
        log.debug("end recoverMos " + this.getID() + " [modelName: " + name + " modelVersion: " + version + " ]" + " NeDomainInst " + this + " MosInst " + this.mos);

    }


    public void updateModel(String modelName,  String modelVersion, BundleService local) throws MOSException, MsgFrameException
    {
        ImagedSystem sys = getSystem();
        ModelMETA remoteMeta = ExtServiceProvider.getInstance().getModelService().get(modelName, modelVersion);
        ModelHead head = remoteMeta.modelHead;

        if (sys == null)
        {
            startMos(head.modelName(), head.modelVersion());
            setDataState(DataState.init);
            setModelState(ModelState.checked);
        }
        else
        {
            if (!remoteMeta.equals(sys.getModelMETA()))
            {
                if (!head.modelName().equals(sys.getModelMETA().modelHead.modelName())) // modelName diffs
                {
                    replaceMos(head.modelName(), head.modelVersion());
                    setDataState(DataState.init);
                    setModelState(ModelState.checked);
                }
                else
                {
                    switchMos(head.modelName(), head.modelVersion(), local);
                }
            }
            else
            {
                setModelState(ModelState.checked);
            }
        }

    }

    private void setDataState(DataState state)
    {
        try
        {
            getSystem().setDataState(state);
        }
        catch (Throwable e)
        {
            log.error(this.getID() + " fail to set data state ", e);
        }
    }


    public void reset()
    {
        setState(DomainState.Empty);
        if (mos != null)
        {
            try
            {
                CommServiceFactory.getService().unRegister(this.ID);
                mos.stop();
            }
            catch (Throwable e)
            {
                log.error("fail to reset mos ", e);
            }
            log.info("reset mos suceed " + this.ID);
        }
    }





}
