package com.zte.mos.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.message.Mo;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

/**
 * Created by 10171693 on 15-4-1.
 */
public class TestJson
{
    @Test
    public void test() throws Exception
    {
        Pair<String, Object> pair = new Pair<String, Object>("loopbackTime", 1000);
        ObjectNode paraNode = JsonUtil.newObjNode();
        paraNode.put(pair.first(), JsonUtil.toNode(pair.second()));

        System.out.println(paraNode.toString());
    }

    @Test
    public void name() throws Exception
    {
        Mo mo = new Mo("aaa");
        mo.setAttr("aaa", null);

        Mo ser = JsonUtil.toObject(JsonUtil.toString(mo), Mo.class);

        System.out.println("");
    }
}
