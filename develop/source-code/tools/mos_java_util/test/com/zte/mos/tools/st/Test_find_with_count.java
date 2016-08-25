package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 16-1-15.
 */
public class Test_find_with_count
{
    static MosServiceHttp service;

    static
    {
        try
        {
            service = new MosServiceHttp();
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setUp() throws Exception
    {
        for (int i = 0; i < 20; i++)
        {
            Mo mo = new Mo("IpV4");
            String ipaddr;
            mo.setAttr("ip_addr", ipaddr = "10.0.0." + i);
            mo.setAttr("", "255.255.255.0");
            mo.setAttr("gate_way", "10.0.0.1");
            mo.setDn(new DN("/Ems/1/IpV4/" + ipaddr));

            service.add(mo);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        service.del(new DN("/Ems/1/IpV4/"));
    }

    @Test
    public void test_find() throws Exception
    {
        Result<Mo> res = service.find("", "IpV4", new DN("/"), 2, 5);

        assertThat(res.getMo().size(), is(5));
        assertThat(((FindResult) res).getSize(), is(20));
    }

    @Test
    public void test_find_not_paged() throws Exception
    {
        Result<Mo> res = service.find("", "IpV4", new DN("/"));

        assertThat(res.getMo().size(), is(20));
        assertThat(((FindResult) res).getSize(), is(20));
    }
}
