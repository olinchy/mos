package com.zte.scope.bundle.oper;

import com.zte.concept.IDomain;
import com.zte.concept.ModelTool;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.BundleService;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.scope.bundle.BundleDomain;

import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-12-21.
 */
public class PRODUCT_ACTION extends PRODUCT_LS
{
    @Override
    public String mib()
    {
        return ModelTool.buildKey("/Ems/Ne/Product", MoCmds.MoAction);
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        MoActionMsg action = (MoActionMsg) msg;
        if (action.actionName().equals("modelChange")){
            return doModelChangeAction(action, domain);
        }else if(action.actionName().equals("reverseCfg")) {
            return doReverseConfigAction(action, domain);
        }else{
            return super.doOperation(msg, domain);
        }
    }

    private Result doReverseConfigAction(MoActionMsg action, IDomain domain)
    {
        BundleDomain bundleDomain = (BundleDomain)domain;
        try
        {
            BundleService service = bundleDomain.getService();
            service.syncConfig(action);
            return new Successful();
        }
        catch (MOSException e)
        {
            logger(this.getClass()).error(action.dn() + " fail to do reverse config action", e);
            return new Failure((int)e.getErrorCode().getErrorCode(), e);
        }
        catch(Throwable e)
        {
            logger(this.getClass()).error(action.dn() + " fail to do reverse config action", e);
            return new Failure(e);
        }
    }

    private Result doModelChangeAction(MoActionMsg action, IDomain domain){
        BundleDomain bundleDomain = (BundleDomain)domain;
        try {
            BundleService service = bundleDomain.getService();
            Pair<String, Object>[] paras = action.paras();
            HashMap<String, String> map = new HashMap<String, String>();
            for (Pair<String, Object> p : paras){
                map.put(p.first(), p.second().toString());
            }
            service.updateModel(action.dn(),map.get("modelName"), map.get("modelVersion"));
            return new Successful();
        } catch (Exception e) {
            e.printStackTrace();
            return new Failure(e);
        }
    }
}
