package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 15-9-23.
 */
public class TestConfResult
{
    @Test
    public void test() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();

        Mo trafficUnit = new Mo("RmudTrafficUnit");
        trafficUnit.setDn(new DN("/Ems/1/Ne/1/Product/1/Shelf/1/TrafficUnit/1"));

        trafficUnit.setAttr("mainSlot", 0);
        trafficUnit.setAttr("standbySlot", 4);

        Result res = service.add(trafficUnit);

        assertTrue(res.isSuccess());
    }

    @Test
    public void test_find_trafficUnit() throws Exception
    {
        Result res = new MosServiceHttp().find(
                "mo.mainSlot = 5 or mo.standBySlot = 5", "TrafficUnit", new DN(
                        "/Ems/1/Ne/2/Product/1/"));

        assertTrue(res != null);
    }
}
