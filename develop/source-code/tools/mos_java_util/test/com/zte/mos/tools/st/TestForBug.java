package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 15-6-26.
 */
public class TestForBug
{
    @Test
    public void test_set_not_exist_mo() throws Exception
    {
        MosServiceHttp serviceHttp = new MosServiceHttp("220.220.0.55");
        Mo mo = new Mo("Device");
        mo.setDn(new DN("/Ems/1/Ne/2/ConfigSet/1/SysPara/1/Device/1"));
        mo.setAttr("siteId", 100);

        Result res = serviceHttp.set(mo);

        assertThat(1l, is(res.getResult()));
        assertTrue(!res.isSuccess());
    }

    @Test
    public void test_get() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        service.get(new DN("/"));
    }
}
