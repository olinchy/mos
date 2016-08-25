package com.zte.mos.type;

import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-3-17.
 */
public class TestIPV4Deserialize
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Log4JRegister.init();
    }

    @Test
    public void test() throws Exception
    {
        ObjHasIpV4 obj = new ObjHasIpV4("10.0.0.3");

        ObjHasIpV4 obj1 = JsonUtil.toObject(JsonUtil.toString(obj), ObjHasIpV4.class);

        assertTrue(obj1 != null);
    }
}
