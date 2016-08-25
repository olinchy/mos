package com.zte.mos.container;

import org.junit.Test;

/**
 * Created by ccy on 7/1/16.
 */
class A
{
    public void lock() throws Exception
    {
        synchronized (this)
        {
            System.out.println("start lock");
            throw new Exception("");
        }
    }
}

class B implements Runnable
{
    B(A a)
    {
        this.a = a;
    }

    private final A a;

    @Override
    public void run()
    {
        try
        {
            a.lock();
            System.out.println("end lock");
        }
        catch (Exception e)
        {

        }
    }

}
public class TestLock
{
    @Test
    public void test_Lock() throws InterruptedException
    {
        A a = new A();
        new Thread(new B(a)).start();
        new Thread(new B(a)).start();
        Thread.sleep(10000);

    }

}
