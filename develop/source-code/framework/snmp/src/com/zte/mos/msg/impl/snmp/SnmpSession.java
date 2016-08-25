package com.zte.mos.msg.impl.snmp;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.observer.DefaultObserver;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.AbstractSession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.impl.exception.ConnectException;
import com.zte.mos.msg.impl.operation.SnmpAction;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static com.zte.mos.storage.MapDbService.DbNameEnum.SNMP_SECURITY;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-5-20.
 * support snmp
 */
public class SnmpSession extends AbstractSession
{
    private static Logger log = logger(SnmpSession.class);
    private SnmpConfiguration configuration;
    private Integer transId = 0;
    private boolean connected = false;
    private HashMap<Integer, Queue<IOperation>> transactions = new HashMap<Integer, Queue<IOperation>>();

    public SnmpSession(TargetAddress myAddress, SnmpConfiguration configuration)
    {
        super(myAddress);
        this.configuration = configuration;
    }

    public SnmpTarget buildTarget()
    {
        return configuration.buildSyncTarget(this.myAddress);
    }

    public void connect(SnmpTarget t) throws ConnectException {
        //retry 2 times
        for(int i=0; i<2; i++){
            try {
                printSnmpInfo(t);
                t.managing_v3_tables();
                return;
            } catch (SnmpException e) {
                log.warn(t.getTargetHost() + " managing_v3_table error,code=" + t.getErrorCode(), e);
            }
        }
        throw new ConnectException((t.getErrorCode() == -1) ? 951095 : t.getErrorCode());
    }

    private void printSnmpInfo(SnmpTarget t){
        log.info(String.format("snmpver=%s,ip=%s,port=%s,comm=%s,name=%s,level=%s,authprotol=%s,priv=%s,auth=%s",
                t.getSnmpVersion(), t.getTargetHost(), t.getTargetPort(),t.getCommunity(),
                t.getPrincipal(), t.getSecurityLevel(),t.getAuthProtocol(),
                t.getPrivPassword(), t.getAuthPassword()));
    }

    public int doOperation(IOperation operation)
    {
        int transactionId = this.getTransId(operation);
//        if (!operation.getTransactionID().nothing())
//        {
//            transactionId = operation.getTransactionID().getValue();
//        }else{
//            transactionId = this.getTransId()
//        }
        Queue<IOperation> transaction = this.getOperations(transactionId);
        transaction.add(operation);
        return transactionId;
    }

    private int genTransId()
    {
        synchronized (transId)
        {
            if (transId < Integer.MAX_VALUE)
            {
                transId++;
            }
            else
            {
                transId = 0;
            }
            return transId;
        }
    }

    @Override
    public PingResponse ping()
    {
        return null;
    }

    private Queue<IOperation> getOperations(int transId)
    {
        Queue<IOperation> operations = transactions.get(transId);
        if (operations == null)
        {
            operations = new LinkedList<IOperation>();
            transactions.put(transId, operations);
        }
        return operations;
    }

    private boolean canIncreamental(Class<? extends ManagementObject> clazz)
    {
        return false;//LocalOnlyPool.has(clazz.getName());
    }

    private int getTransId(IOperation operation)
    {
        Maybe<Integer> transIdMaybe = operation.getTransactionID();
        if (!transIdMaybe.isPresent())
        {
            return genTransId();
        }
        else
        {
            return transIdMaybe.getValue();
        }
    }

    private void cache(int transId, WriteOperation operation)
    {
        if (canIncreamental(operation.getMo().getClass()))
        {
            this.getOperations(transId).add(operation);
        }
    }

    @Override
    public CreateResponse create(Create createOperation)
    {
        int transId = getTransId(createOperation);
        cache(transId, createOperation);
        return new CreateResponse(0, transId);
    }

    @Override
    public UpdateResponse update(Update updateOperation)
    {
        int transId = getTransId(updateOperation);
        cache(transId, updateOperation);
        return new UpdateResponse(0, transId);
    }

    @Override
    public DeleteResponse delete(Delete deleteOperation)
    {
        int transId = getTransId(deleteOperation);
        cache(transId, deleteOperation);
        return new DeleteResponse(0, transId);
    }

    @Override
    public AckResponse commit(Commit commit)
    {
        log.info("***receive commit msg***");
        int transId = commit.getTransactionID().getValue();
        Queue<IOperation> transaction = transactions.remove(transId);
        int result = 0;
        if (transaction != null)
        {
            IncrementalExecutor executor = new IncrementalExecutor(this, transaction, transId,commit.getLocalTransId().getValue());
            result = executor.run();
            log.info("*** increment return is "+result+"***");
        }
        return new AckResponse(result);
    }

    @Override
    public AckResponse rollback(Rollback rollback)
    {
        int transId = rollback.getTransactionID().getValue();
        this.transactions.remove(transId);
        log.debug("snmp rollback");
        return new AckResponse(0);
    }

    @Override
    public GetResponse get(Get getOperation)
    {
        return null;
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation)
    {
        try
        {
            throw new Exception("invalid call trace");
        }
        catch (Throwable e)
        {
            logger(SnmpSession.class).error("fail to call reverse sync",  e);
        }
        return new ReverseSynResponse(1, 0, DefaultObserver.dummy);
    }

    @Override
    public ActionResponse act(Action action)
    {
        SnmpAction oper = (SnmpAction) action;
        SnmpTarget target = configuration.buildSyncTarget(myAddress);
        int result = SnmpMsgServiceFactory.getService().syncSET(target, oper.getOids(), oper.getVars());
        return new ActionResponse(result, null);
    }

    @Override
    public ManagementObject getMo(Get getOperation) throws NotConnectException
    {
        return null;
    }

    @Override
    public String protocol()
    {
        return "SNMP";
    }

    @Override
    public boolean mosSupport()
    {
        return false;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void localRollback()
    {
        this.transactions.clear();
    }

    @Override
    public void setConfiguration(ISessionConfiguration cfg) {
        this.configuration = (SnmpConfiguration)cfg;
        this.saveToDB();
    }

    @Override
    public void setConnectSwitch(boolean open) {
        this.connected = open;
    }

    @Override
    public boolean isSecure()
    {
        return configuration instanceof SnmpV3Configuration;
    }

    void saveToDB(){
        // IP is the primarykey
        DataUnit unit = new DataUnit(this.myAddress.getIpAddress());
        unit.putAll(this.configuration.toMap());
        try {
            MapDbService.getDB(SNMP_SECURITY).put(unit);
        } catch (MOSException e) {
            log.error(this.myAddress + " snmp config save to db error:", e);
        }
    }

    void delFromDB(){
        try {
            MapDbService.getDB(SNMP_SECURITY).delete(myAddress.getIpAddress());
        } catch (BerkeleyDBException e) {
            log.error(this.myAddress + " rpc config delete from db error:", e);
        }

    }

}
