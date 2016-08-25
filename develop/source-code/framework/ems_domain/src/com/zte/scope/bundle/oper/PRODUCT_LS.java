package com.zte.scope.bundle.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.concept.ModelTool;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.scope.bundle.BundleDomain;

import java.rmi.RemoteException;

/**
 * Created by luoqingkai on 15-8-20.
 */
public class PRODUCT_LS implements IMoOperation
{
    @Override
    public String mib() {
        return ModelTool.buildKey("/Ems/Ne/Product", MoCmds.MoLsRequest);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        BundleDomain bundleDomain = (BundleDomain)domain;
        BundleService service = bundleDomain.getService();
        if (!new DNWrapper(msg.dn()).isSizeInOdd())
        {
            try
            {
                return service.onMessage(msg);
            }
            catch (RemoteException e)
            {
                return new Failure(e);
            }
        }else{
            return MoCommandExecutor.execute(msg, bundleDomain.getMos());
        }
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
