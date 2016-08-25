package com.zte.scope.bundle.oper;

import com.zte.concept.ModelTool;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.BundleService;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.bundle.NeToBundleBind;

import java.rmi.RemoteException;
import java.util.HashMap;

import static com.zte.domain.transaction.TransactionMonitorService.startMonitorTransaction;

/**
 * Created by luoqingkai on 15-8-18.
 *
 */
public class NE_CREATION extends BUNDLE_OPER
{
    @Override
    public String mib()
    {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoCreateRequest);
    }

    @Override
    protected void doUserOperation(BundleDomain bundleDomain, MoMsg msg) throws MOSException
    {
        try
        {
            BundleService service = bundleDomain.getService();
            NeIdentity identity = parseNeIdentity(msg);
            service.createNeDomain(identity, msg.getTransactionID().getValue());
            bundleDomain.create(
                    new NeToBundleBind(
                            bundleDomain.getID(),
                            msg.dn()), msg.getTransactionID().getValue());
            startMonitorTransaction(bundleDomain, identity.dn, msg.getTransactionID().getValue());
        }
        catch (RemoteException e)
        {
            throw new MOSException(e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new MOSException("parse error", e);
        }
    }

    private NeIdentity parseNeIdentity(MoMsg msg)
    {
        HashMap<String, Object> valueMap = NeUtil.getMo((MoConfigMsg) msg).getMo();
        String ipDn = valueMap.get("ipV4").toString();
        DN ipAddr = new DN(ipDn);
        String ip = ipAddr.value(4);
        NeIdentity identity = new NeIdentity(msg.dn() + "/Product/1", ip, valueMap.get("netype").toString());
        return identity;
    }
}
