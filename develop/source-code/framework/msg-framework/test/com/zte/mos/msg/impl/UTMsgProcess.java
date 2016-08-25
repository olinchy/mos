package com.zte.mos.msg.impl;


import com.zte.mos.msg.framework.MsgProcess;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.ISession;

/**
 * Created by luoqingkai on 15-5-14.
 *
 */
public class UTMsgProcess extends MsgProcess {
    private String[] types;
    private String version;
    private String mo;
    private OperEnum operation;
    private String protocol = "RPC";
    boolean isTypeSupported = true;
    boolean isVersionSupported = true;

    public UTMsgProcess() {
        super();
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public void setOperation(OperEnum operation) {
        this.operation = operation;
    }

    @Override
    public void setSession(ISession session) {

    }

    @Override
    public boolean isTypeSupported(String modelType) {
        return this.isTypeSupported;
    }

    @Override
    public boolean isVersionSupported(String modelVersion) {
        return this.isVersionSupported;
    }

    @Override
    public String[] moTypes() {
        return this.types;
    }


    @Override
    public String operation() {
        return this.operation.name();
    }

    @Override
    public String protocol() {
        return this.protocol;
    }


    @Override
    public PingResponse ping() {
        return new PingResponse(null, null);
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
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation)
    {
        return null;
    }
}
