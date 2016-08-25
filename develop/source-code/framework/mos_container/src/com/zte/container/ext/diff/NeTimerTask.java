package com.zte.container.ext.diff;

import com.zte.mos.container.NeDomain;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.domain.ConnectionState;
import com.zte.mos.domain.DataState;
import com.zte.mos.task.TaskScheduler;

import java.util.Collection;
import java.util.TimerTask;

import static com.zte.mos.domain.ModelState.checked;


public class NeTimerTask extends TimerTask
{
    RealBundleDomain bundle;

    public NeTimerTask(RealBundleDomain bundle) {
        this.bundle = bundle;
    }

    @Override
    public void run()
    {
        Collection<NeDomain> nes = bundle.getChildren();
        for (NeDomain ne : nes)
        {
            if (ne.getConnectionState() == ConnectionState.OFFLINE)
            {
                ne.updateDateState(DataState.init);
            }
            else
            {
                if (ne.getModelState() == checked)
                {
                    DiffPollingTask task = new DiffPollingTask(ne);
                    TaskScheduler.addTask(task);
                }
            }
        }
    }
}
