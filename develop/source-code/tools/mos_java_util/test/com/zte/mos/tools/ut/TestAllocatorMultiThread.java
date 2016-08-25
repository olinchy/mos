package com.zte.mos.tools.ut;

import com.zte.mos.util.tools.IDAllocator;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-5-17.
 */
public class TestAllocatorMultiThread
{
    @Test
    public void name() throws Exception
    {
        final IDAllocator id = new IDAllocator(0, 200000000);
        final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < 30; i++)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        int a = id.allocate();
                        System.out.println(Thread.currentThread().getName() + " allocate " + a);
                        assertTrue(!map.containsKey(a));
                        map.put(a, a);
                        try
                        {
                            sleep(0);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        Thread.sleep(120000);
    }
}
