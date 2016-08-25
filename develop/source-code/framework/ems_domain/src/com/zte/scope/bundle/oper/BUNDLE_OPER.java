package com.zte.scope.bundle.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.scope.bundle.BundleDomain;

/**
 * Created by luoqingkai on 15-8-18.
 */
public abstract class BUNDLE_OPER implements IMoOperation{
    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        BundleDomain bundleDomain = (BundleDomain)domain;
        BundleService service = bundleDomain.getService();

        doUserOperation(bundleDomain, msg);
        return doInLocalMos(bundleDomain.getMos(), msg);
    }

    protected abstract void doUserOperation(BundleDomain bundleDomain, MoMsg msg)
            throws MOSException;

    private Result doInLocalMos(MOS mos, MoMsg msg){
        Result result;
        try
        {
            result = MoCommandExecutor.execute(msg, mos);
        }
        catch (MOSException e)
        {
            result = new Failure(e, msg.getTransactionID());
        }
        return result;
    }


    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
