package com.zte.mos.app.switchmodel.service;

import static com.zte.mos.util.Singleton.*;

/**
 * Created by ccy on 5/25/16.
 */
public class ModelSwitchResult
{
    private boolean result = false;

    private ModelSwitchResult()
    {

    }

    private void set(boolean result)
    {
        this.result = result;
    }

    private boolean get()
    {
        boolean temp = result;
        this.result = false;
        return temp;
    }

    public static void setModelSwitchResult(boolean result)
    {
        try
        {
            getInstance(ModelSwitchResult.class).set(result);
        }
        catch (Throwable e)
        {

        }
    }

    public static boolean getModelSwitchResult()
    {
        try
        {
            return getInstance(ModelSwitchResult.class).get();
        }
        catch (Throwable e)
        {

        }
        return false;
    }
}
