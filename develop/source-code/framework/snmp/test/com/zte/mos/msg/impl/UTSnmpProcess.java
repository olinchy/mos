package com.zte.mos.msg.impl;

import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.impl.snmp.SnmpProcess;

/**
 * Created by luoqingkai on 15-9-30.
 */
public class UTSnmpProcess extends SnmpProcess {

    private static String operation = OperEnum.Create.name();

    private static boolean isVersionSupported = true;
    private static boolean isTypeSupported = true;

    public static void setIsVersionSupported(boolean isVersionSupported) {
        UTSnmpProcess.isVersionSupported = isVersionSupported;
    }

    public static void setIsTypeSupported(boolean isTypeSupported) {
        UTSnmpProcess.isTypeSupported = isTypeSupported;
    }

    public static void setOperation(String operation) {
        UTSnmpProcess.operation = operation;
    }

    @Override
    public boolean isTypeSupported(String modelType) {
        return isTypeSupported;
    }

    @Override
    public boolean isVersionSupported(String modelVersion) {
        return isVersionSupported;
    }

    @Override
    public String[] moTypes() {
        return new String[]{"MockMo_V233", "MockMo_V241", "MockMo_V242x"};
    }

    private static String[] moTypes;
    public static void setMoTypes(String[] types){
        moTypes = types;
    }

    @Override
    public String operation() {
        return operation;
    }


    @Override
    public PingResponse ping() {
        return null;
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException {
        return new CreateResponse(0, 0);
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

    public ActionResponse act(Action actOperation) {
        return null;
    }
}
