package com.zte.mos.msg.framework;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.ISession;


public abstract class MsgProcess implements ISyncSender {

    public abstract void setSession(ISession session);

    public abstract boolean isTypeSupported(String modelType);//nr8250

    public abstract boolean isVersionSupported(String modelVersion);//v242

    public abstract String[] moTypes();

    public abstract String operation();

    public abstract String protocol();

    protected boolean isSupported(TargetAddress target) {

        return isTypeSupported(target.getModelHead().modelName())
                && isVersionSupported(target.getModelHead().modelVersion());
    }

    @Override
    public PingResponse ping() {
        return null;
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException {
        return null;
    }

    @Override
    public UpdateResponse update(Update updateOperation) {
        return null;
    }

    @Override
    public DeleteResponse delete(Delete deleteOperation) {
        return null;
    }

    @Override
    public AckResponse commit(Commit commit) {
        return null;
    }

    @Override
    public AckResponse rollback(Rollback rollback) {
        return null;
    }

    @Override
    public GetResponse get(Get getOperation) {
        return null;
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation) {
        return null;
    }

    public ActionResponse act(Action actOperation)
    {
        return null;
    }
    @Override
    public ManagementObject getMo(Get getOperation) throws MsgFrameException {
        return null;
    }
}
