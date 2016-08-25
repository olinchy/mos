package com.zte.container.ext.total.config;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 6/14/16.
 */
public class ReverseConfig
{

    private boolean fault_tolerant_enabled = true;

    private ReverseConfig()
    {

    }

    public boolean isFault_tolerant_enabled()
    {
        return fault_tolerant_enabled;
    }

    public static boolean isFault_tolerant()
    {
        try
        {
            return getInstance(ReverseConfig.class).isFault_tolerant_enabled();
        }
        catch (Throwable e)
        {
            logger(ReverseConfig.class).error(" fail to get fault tolerant capability", e);
        }
        return false;
    }

}
