package com.zte.mos.tools.ut;

import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-5-28.
 */
public class TestDeserializeUnicode
{
    private static String json = "{\"sid\":2,\"stuName\":\"\u6C5F\u5357Style\",\"sex\":true,\"logTime\":\"2012-12-04 19:22:36\"}";

    @BeforeClass
    public static void setUp() throws Exception
    {
        Log4JRegister.init();
    }

    @Test
    public void name() throws Exception
    {
        DemoObj obj = JsonUtil.toObject(json, DemoObj.class);

        assertTrue(obj != null);
    }
}
