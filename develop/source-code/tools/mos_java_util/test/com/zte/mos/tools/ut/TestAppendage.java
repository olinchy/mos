package com.zte.mos.tools.ut;

import com.zte.mos.domain.DN;
import com.zte.mos.message.Mo;
import com.zte.mos.type.Append;
import com.zte.mos.type.Appendage;
import com.zte.mos.type.Merger;
import com.zte.mos.type.Range;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by olinchy on 15-11-12.
 */
public class TestAppendage
{
    @Test
    public void test() throws Exception
    {
        Mo mo = new Mo("Ne");
        mo.setDn(new DN("/Ems/1/Ne/1"));

        Range range = new Range().add("1-5");

        mo.setAttr("range", new Append("1-5"));

        System.out.println(JsonUtil.toString(mo));

        System.out.println(JsonUtil.toString(new Append("1-5")));
        Append appendage = JsonUtil.toObject(JsonUtil.toString(new Append("1-5")), Append.class);

        Mo mo1 = JsonUtil.toObject(JsonUtil.toString(mo), Mo.class);
        System.out.println(appendage);
    }

    @Test
    public void test_deserialize_appendage() throws Exception
    {
        Append append = new Append("1-5");

        Appendage appendage = JsonUtil.toObject(JsonUtil.toString(append), Appendage.class);

        assertTrue(appendage.getClass().equals(Append.class));
    }

    @Test
    public void test_set_mo() throws Exception
    {
        Mo mo = new Mo("Ne");
        mo.setDn(new DN("/Ems/1/Ne/1"));
        mo.setAttr("range", new Append("1-5"));

        Object obj = mo.get("range");
        Stub_MO stub_mo = new Stub_MO();
        Field f_range = stub_mo.getClass().getField("range");
        Appendage appendage = JsonUtil.toObject(JsonUtil.toString(obj), Appendage.class);

        appendage.work((Merger) f_range.get(stub_mo));

        Range afterMerge = (Range) f_range.get(stub_mo);

        assertTrue(afterMerge.contains("1", "2", "3", "4", "5", "7", "8"));
        assertFalse(afterMerge.contains("6"));
    }

    private class Stub_MO
    {
        public Range range = new Range("7-8");
    }
}
