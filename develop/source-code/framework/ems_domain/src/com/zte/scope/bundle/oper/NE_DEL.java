package com.zte.scope.bundle.oper;

import com.zte.concept.ModelTool;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.BundleService;
import com.zte.scope.bundle.BundleDomain;

import java.rmi.RemoteException;

import static com.zte.domain.transaction.TransactionMonitorService.startMonitorTransaction;

/**
 * Created by luoqingkai on 15-8-18.
 */
public class NE_DEL extends BUNDLE_OPER
{
    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne", MoCmds.MoDeleteRequest);
    }

    @Override
    protected void doUserOperation(BundleDomain bundleDomain, MoMsg msg) throws MOSException {
        BundleService service = bundleDomain.getService();
        try
        {
            service.deleteNeDomain(msg.dn() + "/Product/1", msg.getTransactionID().getValue());
            bundleDomain.delete(msg.dn(), msg.getTransactionID().getValue());
            startMonitorTransaction(bundleDomain, msg.dn() + "/Product/1", msg.getTransactionID().getValue());
        }
        catch (RemoteException e)
        {
            throw new MOSException(e);
        }
    }

}
