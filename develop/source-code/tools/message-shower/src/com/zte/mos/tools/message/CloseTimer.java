package com.zte.mos.tools.message;

/**
 * Created by olinchy on 2/10/15 for mosjava.
 */
public class CloseTimer extends Thread
{

    private static long Interval = 3000;
    private final CustomFrame frame;
    private final PopUpScheduler scheduler;
    private final Integer pos;

    public CloseTimer(CustomFrame frame, PopUpScheduler scheduler, Integer pos)
    {
        this.frame = frame;
        this.scheduler = scheduler;
        this.pos = pos;
    }

    @Override public void run()
    {
        try
        {
            sleep(Interval);
            frame.dispose();
//            scheduler.release(pos);

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
