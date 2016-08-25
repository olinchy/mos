package com.zte.container.ext.total;

import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.util.basic.Logger.logger;


public class TotalSyncTask extends AbstractTotalSyncTask
{
    private static Logger log = logger(TotalSyncTask.class);

    private ImagedSystem sys;

    public TotalSyncTask(ImagedSystem sys, String targetId, Pair<String, Object>[] paras)
    {
        super(targetId, paras);
        this.sys = sys;
    }

    @Override
    public void run()
    {
        ReverseSyncResult result = doSync(sys, log);
        sendEvent(result);
    }

}
