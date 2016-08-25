package com.zte.mos.inf;

import org.junit.Test;

/**
 * Created by olinchy on 16-3-23.
 */
public class TestSuper
{
    @Test
    public void test_calling_super() throws Exception
    {
        A a = new A();
        a.a();

        B b = new B();
        b.a();
    }
}
