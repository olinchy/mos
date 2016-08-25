package com.zte.container.ext.switchmodel.tm.remote;

import com.zte.container.ext.switchmodel.ModelSwitchContext;
import com.zte.container.ext.switchmodel.ModelSwitchMsgBuilder;
import com.zte.container.ext.switchmodel.tm.ITransaction;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.service.BundleService;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.action.ModelSwitchAck;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/9/16.
 */
public class RemoteModelSwitchTransactionProxy implements ITransaction
{
    private static final String EMS_BUNDLES = "/Ems/1/Bundles/1";

    public RemoteModelSwitchTransactionProxy(ModelSwitchContext ctx)
    {
        this.context = ctx;
        this.remote = pickBundle(context.getNewModelName(), context.getNewModelVersion());
    }

    private final BundleService remote;
    private final ModelSwitchContext context;

    private BundleService pickBundle(String modelName, String modelVersion)
    {
        try
        {
            Result<ActionRsp> result = new MosServiceHttp().act(new DN(EMS_BUNDLES), "pickBundle", packPickBundleParas(modelName, modelVersion));

            if (result.isSuccess())
            {
                String address = String.valueOf(result.getMo().get(0).get("address"));
                return (BundleService)Naming.lookup(address);
            }

        }
        catch(Throwable e)
        {
            logger(RemoteModelSwitchTransactionProxy.class).error("fail to get remote bundle", e);
        }
        return null;
    }

    private Pair<String, Object>[] packPickBundleParas(String modelName, String modelVersion)
    {
        List<Pair<String,Object>> list = new ArrayList<Pair<String,Object>>();
        list.add(new Pair<String, Object>("modelName", modelName));
        list.add(new Pair<String, Object>("modelVersion", modelVersion));
        return list.toArray(new Pair[list.size()]);
    }

    @Override
    public void start() throws MOSException, RemoteException
    {
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " start remote model switch session ");
        remote.switchModelReq(ModelSwitchMsgBuilder.newModelSwitchReq(context));
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " end start remote model switch session ");

    }

    @Override
    public void commit() throws MOSException, RemoteException
    {
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " start commit remote model switch session ");
        remote.switchModelAck(ModelSwitchMsgBuilder.newModelSwitchAck(context, ModelSwitchAck.Ack.commit));
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " end commit remote model switch session ");

    }

    @Override
    public void rollback() throws MOSException, RemoteException
    {
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " start rollback remote model switch session ");
        remote.switchModelAck(ModelSwitchMsgBuilder.newModelSwitchAck(context, ModelSwitchAck.Ack.rollback));
        logger(RemoteModelSwitchTransactionProxy.class).debug(context.getID() + " end rollback remote model switch session ");

    }

    @Override
    public void onTimeOut()
    {

    }

    @Override
    public boolean isTimeOut()
    {
        return false;
    }
}
