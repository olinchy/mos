package com.zte.scope.bundle.oper;

import com.zte.concept.ModelTool;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MoState;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.bundle.NeToBundleBind;

import java.rmi.RemoteException;
import java.util.HashMap;

import static com.zte.domain.transaction.TransactionMonitorService.startMonitorTransaction;

/**
 * Created by luoqingkai on 15-8-25.
 */
public class NE_SET extends BUNDLE_OPER {
    @Override
    protected void doUserOperation(BundleDomain bundleDomain, MoMsg msg) throws MOSException {
        try {
            MOS mos = bundleDomain.getMos();
            BundleService service = bundleDomain.getService();
            ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());
//            MoState oldState = parseMoState(mo.toMoClass());
//            MoState destState = parseMoState(msg, mo.toMoClass());
//            if (!oldState.equals(destState)) {
//                service.setNeState(msg.dn() + "/Product/1", destState, msg.getTransactionID().getValue());
//            }

            String oldIp = mo.toMoClass().getMo().get("ipV4").toString();
            Object newIpObj = NeUtil.getMo((MoConfigMsg) msg).getMo().get("ipV4");
            if (newIpObj != null && !oldIp.equals(newIpObj.toString())){
                DN ipAddr = new DN(newIpObj.toString());
                String ip = ipAddr.value(4);
                service.updateNeIP(msg.dn() + "/Product/1", ip, msg.getTransactionID().getValue());
            }

            bundleDomain.update(new NeToBundleBind(bundleDomain.getID(),
                    msg.dn()), msg.getTransactionID().getValue());
            startMonitorTransaction(bundleDomain, msg.dn() + "/Product/1", msg.getTransactionID().getValue());
        } catch (RemoteException e) {
            throw new MOSException(e);
        }
    }

    private MoState parseMoState(MoMsg msg, Mo oldMo){
        HashMap<String, Object> valueMap = NeUtil.getMo((MoConfigMsg) msg).getMo();
        return buildFromMap(valueMap, oldMo.getMo());
    }

    private MoState parseMoState(Mo mo){
        HashMap<String, Object> valueMap = mo.getMo();
        return buildFromMap(valueMap);
    }
    private MoState buildFromMap(HashMap<String, Object> valueMap){
        String adminState = valueMap.get("adminState").toString();
        String linkState = valueMap.get("connectionState").toString();
        String workState = valueMap.get("workState").toString();
        MoState state = new MoState(adminState, workState, linkState);
        return state;
    }

    private MoState buildFromMap(HashMap<String, Object> valueMap, HashMap<String, Object> oldMap){

        String adminState = oldMap.get("adminState").toString();
        Object newAdminObj = valueMap.get("adminState");
        if (newAdminObj != null){
            adminState = newAdminObj.toString();
        }

        String linkState = oldMap.get("connectionState").toString();
        Object newConnectObj = valueMap.get("connectionState");
        if (newConnectObj != null){
            linkState = newConnectObj.toString();
        }
        String workState = oldMap.get("workState").toString();
        Object newWorkObj = valueMap.get("workState");
        if (newWorkObj != null){
            workState = newWorkObj.toString();
        }

        MoState state = new MoState(adminState, workState, linkState);
        return state;
    }

    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoSetRequest);
    }
}
