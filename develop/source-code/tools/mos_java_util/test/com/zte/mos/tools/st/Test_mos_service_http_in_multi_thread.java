package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Result;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by olinchy on 16-6-22.
 */
public class Test_mos_service_http_in_multi_thread
{
    private static final int THREAD_COUNT = 400;
    private static final int OPERATION_PER_THREAD = 500;

    @BeforeClass
    public static void setUp() throws Exception
    {
        new MosServiceHttp();
    }

    @Test
    public void test() throws Exception
    {
        Long currentTime = System.currentTimeMillis();
        final int[] count = {THREAD_COUNT * OPERATION_PER_THREAD};
        final int[] countSucc = {0};
        final int[] countFail = {0};
        final Observer observer = new Observer()
        {
            @Override
            public synchronized void update(Observable o, Object arg)
            {
                count[0]--;
            }
        };

        final Observer observerFail = new Observer()
        {
            @Override
            public synchronized void update(Observable o, Object arg)
            {
                countFail[0]++;
            }
        };
        final Observer observerSucc = new Observer()
        {
            @Override
            public synchronized void update(Observable o, Object arg)
            {
                countSucc[0]++;
            }
        };

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            final int finalI = i;
            new Thread()
            {
                @Override
                public void run()
                {
                    for (int j = 0; j < OPERATION_PER_THREAD; j++)
                    {
                        try
                        {
                            Result result = get();
                            System.out.println("in " + finalI + "_" + j + " result is " + result);

                            observer.update(null, null);
                            if (result.isSuccess())
                                observerSucc.update(null, null);
                            else
                            {
                                System.out.println(
                                        "in " + finalI + "_" + j + " result is " + JsonUtil.toString(result));
                                observerFail.update(null, null);
                            }
                            sleep(1);
                        }
                        catch (MOSException e)
                        {
                            e.printStackTrace();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        while (true)
        {
            System.out.println(count[0] + " operations left");
            System.out.println(countSucc[0] + " operations executed successfully!");
            System.out.println(countFail[0] + " operations executed failure!");
            System.out.println("costing " + (System.currentTimeMillis() - currentTime) + " ms");
            System.out.println("current active thread count " + Thread.currentThread().getThreadGroup().activeCount());
            if (Thread.currentThread().getThreadGroup().activeCount() <= 2)
            {
                return;
            }
            Thread.sleep(1000);
        }
    }

    private Result get() throws MOSException
    {
        MosServiceHttp service = new MosServiceHttp();

        return (Result) service.get(new DN("/Ems/1/Ne/1"));
    }

    private Result action() throws MOSException
    {
        MosServiceHttp service = new MosServiceHttp();

        return (Result) service.act(
                new DN("/Ems/1/Ne/1"), "setAttribute", new Pair<String, Object>("siteName", "test_set"),
                new Pair<String, Object>("sysType", "aaa"), new Pair<String, Object>("adminState", "activate"));
    }

    @Test
    public void test_set_ne_action() throws Exception
    {
        Result result = action();

        System.out.println(JsonUtil.toString(result));
    }
}

