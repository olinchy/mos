package com.zte.mos.app.rpcserver.ut;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Result;
import com.zte.mos.util.LambdaConverter;
import org.junit.Test;

import java.util.List;

import static com.zte.mos.util.To.map;

/**
 * Created by olinchy on 16-2-19.
 */
public class TestBatchDel
{
    @Test
    public void test() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        Result result = service.del(
                new DN("/Ems/1/IpV4/10.0.0.3"), new DN("/Ems/1/IpV4/10.0.0.2"), new DN("/Ems/1/IpV4/10.0.0.1"));
        System.out.println(result.toString());
    }

    @Test
    public void test_create_50_ne() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();

//        ArrayList<Config> list = new ArrayList<Config>();
//        for (int i = 0; i < 50; i++)
//        {
//            list.add(add(
//                    new Mo("IpV4").setDn(new DN("/Ems/1/IpV4/10.0.0." + i)),
//                    new Mo("Ne").setDn(new DN("/Ems/1/Ne/" + i)).setAttr("ipV4", "/Ems/1/IpV4/10.0.0." + i).setAttr(
//                            "netype", "nr8960")));
//        }
//
//        Result res = service.configs(list.toArray(new Config[list.size()]));

        Result<String> ls = service.ls(new DN("/Ems/1/Ne"));

        List<DN> toDel = map(ls.getMo(), new LambdaConverter<DN, String>()
        {
            @Override
            public DN to(String content)
            {
                return new DN("/Ems/1/Ne/" + String.valueOf(content));
            }
        });
        Result del = service.del(toDel.toArray(new DN[toDel.size()]));

        System.out.println(del);
    }
}
