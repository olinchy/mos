package com.zte.mos.container;

import com.zte.concept.DomainState;
import com.zte.container.ext.load.CheckModelTask;
import com.zte.container.ext.total.ReverseSyncResult;
import com.zte.container.ext.total.ReverseTaskFactory;
import com.zte.mos.domain.ConnectionState;
import com.zte.mos.domain.ModelState;
import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.MOS;
import com.zte.mos.task.TaskScheduler;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.msg.MoEvent;
import com.zte.mos.util.msg.MoFindMsg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.zte.container.ext.switchmodel.remote.handler.ModelSwitchEventDef.handleSwitchEvent;
import static com.zte.mos.util.basic.Logger.logger;


public class BundleObject extends UnicastRemoteObject implements BundleService, MsgService
{
    private static final Logger log = logger(BundleObject.class);

    private RealBundleDomain bundleDomain;


    public BundleObject(RealBundleDomain bundleDomain) throws RemoteException
    {
        this.bundleDomain = bundleDomain;
    }

    @Override
    public Result onMessage(MoMsg msg) throws RemoteException, MOSException
    {
        log.debug("rec:" + msg.toString());
        return bundleDomain.onMsg(msg);
    }

    @Override
    public void createNeDomain(NeIdentity identity, int transId) throws RemoteException, MOSException
    {
        log.debug("create ne start:" + identity + ", transId=" + transId);
        this.bundleDomain.createNeDomain(identity, transId);
        log.debug("create ne end:" + transId);
    }

    @Override
    public void deleteNeDomain(String dn, int transId) throws RemoteException, MOSException
    {
        log.debug("del ne start:" + dn + ", transId=" + transId);
        this.bundleDomain.deleteNeDomain(dn, transId);
        log.debug("del ne end:" + dn + ", transId=" + transId);
    }

    @Override
    public void updateNeIP(String dn, String ip, int transId) throws RemoteException, MOSException
    {
        log.debug("update ne start:" + dn + ", transId=" + transId);
        this.bundleDomain.updateNeIP(dn, ip, transId);
        log.debug("update ne end:" + dn + ", transId=" + transId);
    }

    @Override
    public void updateModel(String dn, String type, String version) throws RemoteException {
        new CheckModelTask(type, version, dn, this).run();
    }

    @Override
    public FindResult broadcast(MoFindMsg msg) throws RemoteException
    {
        return this.bundleDomain.broadcast(msg);
    }

    @Override
    public void syncConfig(MoActionMsg action) throws RemoteException, MOSException
    {
        Runnable task = ReverseTaskFactory.createUserTask(action.dn(), action.paras());
        if (task != null)
        {
            TaskScheduler.addTask(task);
        }
        else
        {
            log.debug(action.dn() + " is not ready for sync");
            throw new MOSException(new ErrorCode(ReverseSyncResult.failed.getErrorCode()), action.dn() + " is not ready for sync");
        }
    }

    @Override
    public void switchModelReq(MoActionMsg msg) throws RemoteException, MOSException
    {
        handleSwitchEvent(msg, this);
    }

    @Override
    public void switchModelAck(MoActionMsg msg) throws RemoteException, MOSException
    {
        handleSwitchEvent(msg, this);
    }

    @Override
    public String identity() throws RemoteException
    {
        return this.bundleDomain.getID();
    }

    @Override
    public Result<?> onMsg(MoMsg msg) throws RemoteException, MOSException
    {

        if (msg.getCmd() == MoCmds.Event)
        {
            MoEvent event = (MoEvent) msg;
            String eventName = msg.dn();
            HashMap content = event.getContent();
            String neId = content.get("neid").toString();
            neId = "/Ems/1/Ne/" + neId + "/Product/1";
            if (eventName.equals("NeModelChangeEvent"))
            {
                log.debug("recv NeModelChangeEvent: " + msg.toString());
                Map model = (Map) content.get("newValue");
                String modelName = model.get("modelName").toString();
                String modelVersion = model.get("modelVersion").toString();
                TaskScheduler.addTask(new CheckModelTask(modelName,modelVersion, neId, this));
            }
            else if (eventName.equals("NeStateChangeEvent"))
            {
                log.debug("recv NeStateChangeEvent: " + msg.toString());
                String stateType = content.get("stateType").toString();
                if (stateType.equals("connectionState"))
                {
                    Map vMap = (Map)content.get("newValue");
                    String value = vMap.get("connectionState").toString();
                    NeDomain ne = bundleDomain.getChild(neId);

                    ConnectionState connectionState = ConnectionState.contentOf(value);
                    ne.setConnectionState(connectionState);
                }
            }
        }
        return new Successful<Object>();
    }

    @Override
    public Result<?> onMsg(ArrayList<? extends MoMsg> msg) throws RemoteException, MOSException
    {
        return null;
    }

    public NeDomain create(NeIdentity identity, MOS mos)
    {
        NeDomain domain =  NeDomain.newInstance(identity);
        domain.setMos(mos);
        return domain;
    }

    public void save(NeDomain domain, DomainState state) throws MOSException
    {
        domain.setState(state);
        domain.saveNeDomain();
        this.bundleDomain.save(domain);
    }

    public void remove(NeDomain domain)
    {
        this.bundleDomain.remove(domain);
    }



}
