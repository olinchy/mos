package com.zte.container.ext.diff;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.RemoteException;
import com.zte.mos.msg.framework.operation.Get;
import com.zte.mos.service.MOS;
import com.zte.mos.service.SLRouterPool;
import com.zte.mos.util.MinusIDGenerator;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.util.basic.Logger.logger;

public class V11SyncDiffTask implements Runnable {
    private enum Operation{
        ADD(0), SET(1), DEL(2), REPLACE(3);
        final int value;

        Operation(int value) {
            this.value = value;
        }
    }

    private static Logger log = logger(V11SyncDiffTask.class);

    private TargetAddress target;
    private MOS mos;
    private JsonNode moOperList;
    private final Maybe<Integer> transId;

    public V11SyncDiffTask(TargetAddress target, JsonNode moOperList) {
        this.target = target;
        mos = (MOS)target.getSystem();
        this.moOperList = moOperList;
        transId = new Maybe<Integer>(MinusIDGenerator.genNextId());
    }

    @Override
    public void run() {
        ArrayNode operList = (ArrayNode)moOperList;
        for (JsonNode oneNode : operList){
            String dnStr = oneNode.get("dn").asText();
            int operType = oneNode.get("operation").asInt();
            String moClazz = oneNode.get("moClass").asText();
            syncOperation(dnStr, operType, moClazz);

        }
        try
        {
            SLRouterPool.getRouter(mos.getRootExternalDN()).noti(mos.commit(transId));
        }
        catch (MOSException e)
        {
            log.warn("commit error:"+e);
        }
    }

    private void syncOperation(String dn, int operation, String moClazz) {
        try{
            if (operation == Operation.DEL.value){
                del(dn);
            }else{
                getAndUpdate(dn, operation, moClazz);
            }
        }catch (MOSException e){
            log.warn(dn, e);
        }

    }

    private void getAndUpdate(String dn, int operation, String moClazz) throws MOSException {
        ManagementObject mo = null;
        try {
            mo = getFromRemote(dn, moClazz);
        } catch (RemoteException e) {
            if (e.getErrorCode() == 217092){
                del(dn);
            }else{
                log.warn(dn + " online reverse error:" + operation + ", code=" + e.getErrorCode());
                throw new MOSException(e);
            }
        } catch (MsgFrameException e){
            throw new MOSException(e);
        }
        update(dn, mo, operation);
    }

    private void update(String dn, ManagementObject mo, int operation) throws MOSException {
        if (mo != null){
            ManagementObject moInDb = mos.getMO(dn, transId);
            if (moInDb == null){
                if (operation == Operation.ADD.value){
                    log.info("adding :" + mo);
                    mos.createMO(mo,transId);
                }else{
                    log.info("update but has no mo locally:" + mo.toString());
                }
            }else{
                log.info("updating:" + mo);
                mos.updateMO(mo, null, transId);
            }
        }
    }

    private ManagementObject getFromRemote(String dn, String moClazz) throws MsgFrameException {
        ISouthService sv = CommServiceFactory.getService();
        Get get = new Get(dn, moClazz);
        ManagementObject response = sv.getMo(get, target.getTargetID());
        return response;

    }

    private void del(String dn) throws MOSException {
        try{
            mos.deleteMO(dn, transId);
        }catch (NullMoException e){
            log.info(dn + " not exist.");
        }
        log.info(dn + " deleted.");
    }

    public boolean belongTo(String targetId){
        return target.getTargetID().equals(targetId);
    }
}
