package com.zte.container.ext.total;

import com.zte.mos.container.NeDomain;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;

import static com.zte.container.ext.util.PairComparator.isEqual;
import static com.zte.mos.util.basic.Logger.logger;


public class SyncActionTask extends AbstractTotalSyncTask
{
    private static Logger log = logger(SyncActionTask.class);

    public SyncActionTask(String dn, Pair<String, Object>[] paras)
    {
        super(dn, paras);
        this.paras = paras;
    }

    private final Pair<String, Object>[] paras;

    @Override
    public void run()
    {
        NeDomain ne = RealBundleDomain.getInstance().getChild(getTargetId());
        ImagedSystem sys = ne.getSystem();
        if (sys != null)
        {
            ReverseSyncResult result = doSync(sys, log);
            sendEvent(result);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o))
        {
            return false;
        }
        return isEqual(this.paras, ((SyncActionTask) o).paras);
    }





}
