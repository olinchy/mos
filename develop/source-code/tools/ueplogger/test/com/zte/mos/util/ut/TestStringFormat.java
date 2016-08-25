package com.zte.mos.util.ut;

/**
 * Created by olinchy on 15-6-25.
 */
public class TestStringFormat
{
    @org.junit.Test
    public void testName() throws Exception
    {
        String a = "test adadasd \n adadasdada";
        System.out.println(String.format("[thr: %1$s] %2$s", Thread.currentThread().getName(), a));

    }
}
