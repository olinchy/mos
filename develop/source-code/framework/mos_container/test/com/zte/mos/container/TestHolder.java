package com.zte.mos.container;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy on 7/6/16.
 */

class Container
{
    private static class Holder
    {
        static final Container instance = new Container();
    }

    public static Container getInstance() {
        return Holder.instance;
    }

}
public class TestHolder
{
    @Test
    public void create_container()
    {
        List<Thread> list = new ArrayList<Thread>();
        final List<Container> containers = new BlockingArrayQueue<Container>();
        for(int i = 0; i< 4096;i++)
        {
            list.add(
                    new Thread(
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        containers.add(Container.getInstance());
                                    }
                                    catch (Throwable e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }));
        }

        for(Thread t : list)
        {
            t.start();
        }

        Container monitor = Container.getInstance();
        for(Container c : containers)
        {
            if ( c != monitor)
            {
                System.out.println("invalid container");
            }
        }

        System.out.println("good ");


    }

}
