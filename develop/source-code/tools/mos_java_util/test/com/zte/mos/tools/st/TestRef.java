package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 15-6-1.
 */
public class TestRef
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
        }
    }

    @Test
    public void test_set() throws Exception
    {
        Mo ces = new Mo("CesService");
        ces.setDn(new DN("/Ems/1/CesService/1"));
        ces.setAttr("aEnd", "/Ems/1/Ne/1/Product/1/CesRoute/1");
        ces.setAttr("zEnd", "/Ems/1/Ne/2/Product/1/CesRoute/1");
        service.add(ces);

        ces.setAttr("aEnd", "/Ems/1/Ne/1/Product/1/CesRoute/2");
        ces.setAttr("zEnd", "/Ems/1/Ne/2/Product/1/CesRoute/2");
        service.set(ces);

        service.del(new DN("/Ems/1/Ne/1/Product/1/CesRoute/2"));

        ces.setAttr("aEnd", "/Ems/1/Ne/1/Product/1/CesRoute/1");
        ces.setAttr("zEnd", "/Ems/1/Ne/2/Product/1/CesRoute/1");
        service.add(ces);

//        service.del(new DN("/Ems/1/CesService/1"));
    }

    @Test
    public void test_add_multi_ref() throws Exception
    {
//        createNe(4, 5);

        try
        {
            Mo ces = new Mo("CesService");
            DN cesDN;
            ces.setDn(cesDN = new DN("/Ems/1/CesService/1"));
            DN route_4_1 = new DN("/Ems/1/Ne/4/Product/1/CesRoute/1");
            DN route_4_2 = new DN("/Ems/1/Ne/4/Product/1/CesRoute/2");
            DN route_4_3 = new DN("/Ems/1/Ne/4/Product/1/CesRoute/3");

            DN route_5_1 = new DN("/Ems/1/Ne/5/Product/1/CesRoute/1");
            DN route_5_2 = new DN("/Ems/1/Ne/5/Product/1/CesRoute/2");
            DN route_5_3 = new DN("/Ems/1/Ne/5/Product/1/CesRoute/3");

            ces.setAttr("aEnd", route_5_1.toString());
            ces.setAttr("zEnd", route_4_2.toString());
            service.add(ces);

            Mo getCes = service.get(cesDN).getMo().get(0);
            assertTrue((getCes.get("aEnd")).equals(route_5_1.toString()));
            assertTrue(getCes.get("zEnd").equals(route_4_2.toString()));

            Mo get_5_1 = service.get(route_5_1).getMo().get(0);
            assertTrue(((ArrayList) get_5_1.get("refBy")).contains(cesDN.toString()));
            Mo get_4_2 = service.get(route_4_2).getMo().get(0);
            assertTrue(((ArrayList) get_4_2.get("refBy")).contains(cesDN.toString()));

            // test set
            ces.setAttr("aEnd", route_4_3);
            ces.setAttr("zEnd", route_5_2);
            service.set(ces);

            getCes = service.get(cesDN).getMo().get(0);
            assertTrue((getCes.get("aEnd")).equals(route_4_3.toString()));

            assertTrue((getCes.get("zEnd")).equals(route_5_2.toString()));

            Mo get_5_2 = service.get(route_5_2).getMo().get(0);
            assertTrue(((ArrayList) get_5_2.get("refBy")).contains(cesDN.toString()));
            Mo get_4_3 = service.get(route_4_3).getMo().get(0);
            assertTrue(((ArrayList) get_4_3.get("refBy")).contains(cesDN.toString()));

            get_5_1 = service.get(route_5_1).getMo().get(0);
            assertFalse((get_5_1.get("refBy")).equals(cesDN.toString()));
            get_4_2 = service.get(route_4_2).getMo().get(0);
            assertFalse((get_4_2.get("refBy")).equals(cesDN.toString()));

            // test del ces service
            service.del(cesDN);
            get_5_2 = service.get(route_5_2).getMo().get(0);
            assertFalse(((ArrayList) get_5_2.get("refBy")).contains(cesDN.toString()));

            get_4_3 = service.get(route_4_3).getMo().get(0);
            assertFalse(((ArrayList) get_4_3.get("refBy")).contains(cesDN.toString()));

            // add again
            ces.setAttr("aEnd", route_5_3.toString());
            ces.setAttr("zEnd", route_4_2.toString());
            service.add(ces);

            service.del(route_5_3);
            getCes = service.get(cesDN).getMo().get(0);
            assertTrue((getCes.get("zEnd")).equals(route_4_2.toString()));

            assertFalse(service.get(route_5_3).isSuccess());

            service.del(route_5_2);
            assertFalse(service.get(cesDN).isSuccess());

            get_4_2 = service.get(route_4_2).getMo().get(0);
            assertFalse(((ArrayList) get_4_2.get("refBy")).contains(cesDN.toString()));
        }
        finally
        {
//            deleteNe(4, 5);
        }
    }

    private void deleteNe(int... ids) throws MOSException
    {
        service.del(new DN("/Ems/1/Ne/"));
    }

    private void createNe(int... ids) throws MOSException
    {
        service.startTransaction();
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
                int id = ids[i];
                service.add(
                        new Mo("IpV4").setDn(new DN("/Ems/1/IpV4/124.0.0." + id)), new Mo("Ne").setDn(
                                new DN(
                                        "/Ems/1/Ne/" + id)), new Mo("Product").setDn(
                                new DN(
                                        "/Ems/1/Ne/" + id + "/Product/1")));
                for (int j = 1; j <= 3; j++)
                {
                    service.add(new Mo("CesRoute").setDn(new DN("/Ems/1/Ne/" + id + "/Product/1/CesRoute/" + j)));
                }
            }
        }
        catch (MOSException e)
        {
            service.rollback();
        }
        service.commit();
    }
}
