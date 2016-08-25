package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Mo;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

import static com.zte.mos.httpservice.Config.ConfigMethods.add;

/**
 * Created by olinchy on 15-10-9.
 */
public class TestBatchConfig
{
    @Test
    public void test() throws Exception
    {
        MosServiceHttp serviceHttp = new MosServiceHttp();

        Mo ip = new Mo("IpV4");
        ip.setDn(new DN("/Ems/1/IpV4/10.10.10.10"));

        Mo ne = new Mo("Ne");
        ne.setDn(new DN("/Ems/1/Ne/1"));
        ne.setAttr("netype", "nr8250");
        ne.setAttr("ipV4", "/Ems/1/IpV4/10.10.10.10");

        ConfResultSet resultSet = serviceHttp.configs(add(ip), add(ne));

        System.out.println(JsonUtil.toString(resultSet));
    }
}
