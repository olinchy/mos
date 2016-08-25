package com.zte.mos.msg.framework;

import com.zte.mos.domain.ConnectionState;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.protocol.IProtocolService;
import com.zte.mos.msg.framework.protocol.ProtocolService;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.SessionFactory;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static com.zte.mos.domain.ConnectionState.*;

/**
 * Created by luoqingkai on 15-9-23.
 *
 */
public class SouthService implements ISouthService {

    private Map<String, ITarget> targetMap = new HashMap<String, ITarget>();


    @Override
    public void register(TargetAddress address)
            throws MsgFrameException {

        ITarget target = buildTarget(address);
        IProtocolService psv = ProtocolService.getInstance();
        Protocol[] protocols = psv.getProtocols(address.getMosHead(), address.getTargetID());

        for (Protocol p : protocols) {
            ISession session = SessionFactory.create(address, p);
            target.addSession(session);
        }
    }

    private ITarget buildTarget(TargetAddress address) {
        ITarget target = new SouthTarget(address);
        targetMap.put(target.id(), target);
        return target;
    }

    @Override
    public void unRegister(String targetId) {
        ITarget target = targetMap.remove(targetId);
        if (target != null) {
            target.unRegister();
        }
    }

    @Override
    public void modifyIp(TargetAddress address) throws MalformedURLException {
        // ID must be same
        ITarget target = targetMap.get(address.getTargetID());
        if (target != null) {
            target.setIp(address.getIpAddress());
        }
    }

    @Override
    public void recover(TargetAddress address) throws UnsupportedProtocolException {
        ITarget target = buildTarget(address);
        IProtocolService psv = ProtocolService.getInstance();
        Protocol[] protocols = psv.getProtocols(address.getMosHead(), address.getTargetID());

        for (Protocol p : protocols) {
            ISession session = SessionFactory.recover(address, p);
            target.addSession(session);
        }
    }

    @Override
    public void setConnectSwitch(String targetId, ConnectionState connectionState) throws UnregisteredException {
        ITarget target = this.getTarget(targetId);
        if (connectionState != UNKNOWN){
            target.setConnectSwitch(connectionState == ONLINE);
        }
    }


    @Override
    public CreateResponse create(Create operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).create(operation);
    }

    @Override
    public PingResponse ping(String targetId) throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        return target.ping();
    }

    @Override
    public DeleteResponse delete(Delete operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).delete(operation);
    }

    @Override
    public GetResponse get(Get operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).get(operation);
    }

    @Override
    public ManagementObject getMo(Get operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).getMo(operation);
    }

    @Override
    public ActionResponse action(Action operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).act(operation);
    }

    @Override
    public UpdateResponse update(Update operation, String targetId) throws MsgFrameException {
        return getSender(operation, targetId).update(operation);
    }

    @Override
    public AckResponse commit(Commit operation, String targetId) throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        return target.commit(operation);
    }

    @Override
    public void rollback(Rollback operation, String targetId) throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        target.rollback(operation);
    }

    @Override
    public void localRollback(String targetId) {
        ITarget target = null;
        try {
            target = this.getTarget(targetId);
            target.localRollback();
        } catch (UnregisteredException e) {
            return;
        }

    }

    @Override
    public ReverseSynResponse reverseSync(ReverseSyn operation, String targetId)
            throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        return target.reverseSyn(operation);
    }

    @Override
    public void setSessionCfg(ISessionConfiguration cfg, String targetId) throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        target.setSessionCfg(cfg, cfg.protocol());
    }

    private ISyncSender getSender(MoOperation operation, String targetId)
            throws MsgFrameException {
        ITarget target = this.getTarget(targetId);
        return target.getSender(operation);
    }

    private ITarget getTarget(String targetID) throws UnregisteredException {
        ITarget target = this.targetMap.get(targetID);
        if (target != null) {
            return target;
        } else {
            throw new UnregisteredException(targetID);
        }
    }


}
