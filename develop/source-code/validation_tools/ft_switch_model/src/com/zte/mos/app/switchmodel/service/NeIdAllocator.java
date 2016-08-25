package com.zte.mos.app.switchmodel.service;

import static com.zte.mos.util.Singleton.*;
/**
 * Created by ccy on 6/2/16.
 */
public class NeIdAllocator
{
    private int start = 0;

    private NeIdAllocator()
    {
        start = (int)(Math.random()*1024);
    }

    public static int allocNeId()
    {
        try
        {
            return getInstance(NeIdAllocator.class).alloc();
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    private int alloc()
    {
        return start++;
    }
}
