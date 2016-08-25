package com.zte.mos.tools.ut;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.message.ActionClass;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.tools.ut.stub.AAA;
import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.zte.mos.util.tools.JsonUtil.newObjNode;
import static junit.framework.Assert.assertTrue;

/**
 * Created by olinchy on 4/22/15 for ems-mos.
 */
public class TestJson
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Log4JRegister.init();
    }

    @Test
    public void test() throws Exception
    {
        Object aaa = JsonUtil.toObject(JsonUtil.toString(new AAA()), Object.class);

        assertTrue(aaa != null);
        System.out.println(aaa.getClass());
    }

    @Test
    public void test_actionRsp() throws Exception
    {
        ObjectNode node = newObjNode();
        node.put("a", 1);
        node.put("b", "2");

        Result res = new Successful<ActionRsp>(new ActionRsp(new ActionClass(), node));

        System.out.println(JsonUtil.toString(res));
    }

    @Test
    public void test_template() throws Exception
    {
        String template = "{\"displayName\":\"LMT\"}";
        Object node = JsonUtil.toObject(template, Object.class);

        System.out.println("");
    }
}
