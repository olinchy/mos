package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-7-16.
 */
public class TestDelContinuesly
{

    @Test
    public void test() throws Exception
    {
        MosServiceHttp serviceHttp = new MosServiceHttp();
        for (int i = 1; i <= 10; i++)
        {
            serviceHttp.add(
                    new Mo("IpV4").setDn(new DN("/Ems/1/IpV4/127.0.0." + i)), new Mo("Ne").setDn(
                            new DN(
                                    "/Ems/1/Ne/" + i)).setAttr("ipV4", "/Ems/1/IpV4/127.0.0." + i));
        }

        delAll("/Ems/1/Ne");
        delAll("/Ems/1/IpV4");

    }

    private void delAll(String dn) throws MOSException
    {
        MosServiceHttp serviceHttp = new MosServiceHttp();
        Result<String> ls = serviceHttp.ls(new DN(dn));
        ArrayList<DN> lstDN = new ArrayList<DN>();
        for (String s : ls.getMo())
        {
            lstDN.add(new DN(dn).append(s));
        }

        serviceHttp.del(lstDN.toArray(new DN[lstDN.size()]));
    }
}
