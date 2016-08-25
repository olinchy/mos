package com.zte.concept;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.exception.AckException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.AckResult;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-7-14.
 * This class decides whether the destination of msg is local or remote
 */
public abstract class AbstractDomain implements IDomain{
    private static Logger log = logger(AbstractDomain.class);
    protected IOperationPool operationPool;
    protected String ID;


    protected MOS mos;


    public AbstractDomain(String ID, MOS mos) {
        this.ID = ID;
        this.mos = mos;
        //DomainManager.getInstance().register(this);
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public MOS getMos() {
        return mos;
    }

    public void setMos(MOS mos)
    {
        this.mos = mos;
    }


    @Override
    public Result onMsg(MoMsg msg) {
        try{
            if (msg.getCmd().equals(MoCmds.MoAck)){
                MoAckMsg ack = (MoAckMsg)msg;
                return onAck(ack);
            }
            else {
                return onSingleDestMsg(msg);
            }
        }catch (MOSException e){
            return new Failure(e.getErrorCode().getErrorCode(), e, msg.getTransactionID());
        }
    }

    private Result onSingleDestMsg(MoMsg msg) throws MOSException {
        DN dn = new DN(msg.dn());
        if(localOperable(dn, msg.getCmd()) || localSupported(dn)){
            return dealInLocal(msg, dn);
        }
        else{
            return forward(msg);
        }
    }

    protected Result dealInLocal(MoMsg msg, DN dn) throws MOSException {
        IMoOperation oper = operationPool.getOperation(dn, msg.getCmd());
        return oper.doOperation(msg, this);
    }

    /**
     * classify local and forward dns
     * @param ack
     * @return
     * @throws MOSException
     */
    public Result onAck(MoAckMsg ack) throws MOSException {
        String dnsStr = "";
        for (String dn : ack.dns()){
            dnsStr = dnsStr + dn + ",";
        }
        log.info("ack:" + dnsStr);
        ArrayList<String> localAckArray = new ArrayList<String>();
        ArrayList<String> forwardAckArray = new ArrayList<String>();

        String[] dnArray = ack.dns();

        for (String dn : dnArray){
            DN dnObj = new DN(dn);
            if (localSupported(dnObj) || localOperable(dnObj, ack.getCmd())){
                localAckArray.add(dn);
            }else{
                forwardAckArray.add(dn);
            }
        }

        Result response = new Successful(ack.getTransactionID());
        if (!localAckArray.isEmpty()) {
            response = ackLocally(localAckArray, ack);
        }
        if (!forwardAckArray.isEmpty()){
            response = ackForward(forwardAckArray, ack);
        }

        return response;
    }

    /**
     * set dns for local
     * @param localAckArray
     * @param ack
     * @return
     */
    private Result ackLocally(ArrayList<String> localAckArray, MoAckMsg ack) throws MOSException {
        MoAckMsg localAck = ack.clone();
        String[] dns = localAckArray.toArray(new String[localAckArray.size()]);
        localAck.setDn(dns);
        return dealAckInLocal(localAck);
    }

    protected Result dealAckInLocal(MoAckMsg ack) throws MOSException{
        AckResult result = new AckResult(ack.getTransactionID());
        HashMap<IMoOperation, ArrayList<String>> tmp = new HashMap<IMoOperation, ArrayList<String>>();
        for (String oneDn : ack.dns()) {
            DN dnObj = new DN(oneDn);
            IMoOperation oper = operationPool.getOperation(dnObj, MoCmds.MoAck);

            if (tmp.containsKey(oper)) {
                tmp.get(oper).add(oneDn);
            } else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(oneDn);
                tmp.put(oper, list);
            }
        }
        for(IMoOperation oper : tmp.keySet()){
            MoAckMsg cloneAck = ack.clone();
            ArrayList<String> dnList = tmp.get(oper);
            cloneAck.setDn(dnList.toArray(new String[dnList.size()]));
            try {
                oper.ack(cloneAck, this);
            } catch (MOSException e) {
                result.push(new AckException(e, cloneAck.dns()));
            }
        }
        return result;
    }

    private Result ackForward(
            ArrayList<String> forwardAckArray, MoAckMsg ack) throws MOSException {
        MoAckMsg forwardAck = ack.clone();
        String[] dns = forwardAckArray.toArray(new String[forwardAckArray.size()]);
        forwardAck.setDn(dns);
        return forward(forwardAck);
    }

    public abstract Result forward(MoMsg msg) throws MOSException;
}
