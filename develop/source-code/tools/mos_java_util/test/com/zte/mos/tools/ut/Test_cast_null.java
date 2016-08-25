package com.zte.mos.tools.ut;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by olinchy on 16-7-16.
 */
public class Test_cast_null
{
    HashMap<String, Object> map = new HashMap<String, Object>();

    @Test(expected = NullPointerException.class)
    public void test_cast_to_primitive() throws Exception
    {
        int a = (Integer) map.get("a");
    }

    @Test
    public void test_cast_to_object() throws Exception
    {
        Integer a = (Integer) map.get("a");
    }
}
