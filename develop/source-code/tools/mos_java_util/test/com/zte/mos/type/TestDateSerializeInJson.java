package com.zte.mos.type;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-3-17.
 */
public class TestDateSerializeInJson
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Log4JRegister.init();
    }
    @Test
    public void test_obj_serial_with_date() throws Exception
    {
        String a = JsonUtil.toString(new ObjHasDate());
        System.out.println(a);
    }

    @Test
    public void test_deserialize() throws Exception
    {
        ObjectNode node = JsonUtil.newObjNode();
        node.put("date", "2016-03-17 14:40:35");

        ObjHasDate obj = JsonUtil.toObject(node.toString(), ObjHasDate.class);

        assertTrue(obj != null);
    }
}
