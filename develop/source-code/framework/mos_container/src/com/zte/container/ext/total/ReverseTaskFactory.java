package com.zte.container.ext.total;

import com.zte.mos.container.NeDomain;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.type.Pair;

import static com.zte.mos.util.basic.Logger.logger;

public class ReverseTaskFactory {

    public static Runnable createUserTask(String targetId, Pair<String, Object>[] paras)
    {
        NeDomain ne = RealBundleDomain.getInstance().getChild(targetId);

        if (ne == null)
        {
            logger(ReverseTaskFactory.class).debug(targetId + " ne domain is null");
            return null;
        }
        ImagedSystem sys = ne.getSystem();
        if (sys == null)
        {
            logger(ReverseTaskFactory.class).debug(targetId + " ne image is null");
            return null;
        }
        ModelHead modelHead = sys.getModelMETA().modelHead;
        if (modelHead.modelName().startsWith("nr8960"))
        {
            return new AsyncReverseActionTask(ne, sys, paras);
        }
        else
        {
            return new SyncActionTask(targetId, paras);
        }
    }

    public static Runnable createSystemTask(String targetId, ImagedSystem sys)
    {
        NeDomain ne = RealBundleDomain.getInstance().getChild(targetId);

        if (ne == null)
        {
            logger(ReverseTaskFactory.class).debug(targetId + " ne domain is null");
            return null;
        }

        ModelHead modelHead = sys.getModelMETA().modelHead;
        if (modelHead.modelName().startsWith("nr8960")
                && modelHead.modelVersion().equals("v242"))
        {
            return new AsyncReverseActionTask(ne, sys, null);
        }
        else
        {
            return new TotalSyncTask(sys, targetId, null);
        }
    }


}
