package com.zte.smartlink.deliver;

import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.smartlink.Sending;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-12-31.
 */
public class AsyncMethod implements SendMethod
{
    @Override
    public Result send(final Sending sending)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sending.send();
                }
                catch (Throwable e)
                {
                    logger(this.getClass()).warn("send msg " + sending.toString() + " caught exception!", e);
                }
            }
        }.start();
        return new Successful();
    }
}
