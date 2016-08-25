package com.zte.mos.msg.increment;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ccc on 16-4-2.
 */
public class TestIncrement_8250_v242_FT
{
    ///reverse-config/NR8250/NR8250-2.4.2/NR8250-24208B05-RCUC-AOU1+1HSB-ACM-ATPC-CAR-ACL-6VLAN"

    //Run com.zte.mos.app.cm.client.reverse.cases.NR8250_2_4_2.Test_nr8250_v242_ReverseF first, build mo tree

    @Before
    public void setup() throws MOSException
    {
        reset();
    }

    private void reset() throws MOSException
    {
        MosService service = new MosServiceHttp();
        service.del(new DN("/Ems/1/Ne/5/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/test"));
    }

   // @Test
    public void testOneNeTrans() throws MOSException
    {
        MosService service = new MosServiceHttp();

        Mo mdMo = new Mo("CfmMD");
        mdMo.setDn(new DN("/Ems/1/Ne/5/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/test"));
        mdMo.setAttr("level", "6");

        Result res = service.add(mdMo);

        assertTrue(res.isSuccess());



    }

   // @Test
    public void testNesInOneTrans() throws Exception
    {
        MosService service = new MosServiceHttp();
        service.startTransaction();

        Mo mo1 = new Mo("CfmMD");
        mo1.setDn(new DN("/Ems/1/Ne/12/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/ccc"));
        mo1.setAttr("level", "6");

        Mo mo2 = new Mo("CfmMD");
        mo2.setDn(new DN("/Ems/1/Ne/9/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/ccc"));
        mo2.setAttr("level", "6");

        Result res1 = service.add(mo1);
        Result res2 = service.add(mo2);

        if(res1.isSuccess() && res2.isSuccess())
        {
          service.commit();
        }
        else{
            service.rollback();
        }


    }

   @Test
    public void testVlans() throws Exception
    {
        MosService service = new MosServiceHttp();
      /*  Mo vlan = service.get(new DN("/Ems/1/Ne/12/Product/1/Shelf/1/TrafficPort/GBE_OE-4-2/Vlan")).getMo().get(0);

        vlan.setAttr("defaultPriority",1);

        Result res = service.set(vlan);*/

     //   assertTrue(res.isSuccess());

        service.startTransaction();
        Mo ctos = new Mo("Customer_CToS");
        ctos.setDn( new DN("/Ems/1/Ne/1/Product/1/Shelf/1/TrafficPort/GBE_OE-4-2/Vlan/Details/CToS/3"));
        //ctos.setAttr("activeFlag", 1);
        ctos.setAttr("cVlanMin",1);
        ctos.setAttr("cVlanMax",1);
        ctos.setAttr("sVlan",1);
        ctos.setAttr("sVlanPriority",1);

        Result res2 = service.add(ctos);

        if(res2.isSuccess())
        {
            service.commit();
        }
    }

  //  @Test
    public void testCfm() throws Exception
    {
        MosService service = new MosServiceHttp();
        service.startTransaction();

        Mo mo1 = new Mo("CfmMD");
        mo1.setDn(new DN("/Ems/1/Ne/12/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/ccc"));
        mo1.setAttr("level", "6");

        Mo ma = new Mo("CfmMD");
        mo1.setDn(new DN("/Ems/1/Ne/12/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/ccc"));
        mo1.setAttr("level", "6");

    }

   // @Test
    public void testCfgBatch() throws Exception
    {
        MosService service = new MosServiceHttp();
        service.startTransaction();

        Mo acm  = service.get(new DN("/Ems/1/Ne/1/Product/1/Shelf/1/TrafficUnit/111/LogicAirPort/1/Acm/1")).getMo().get(0);
        acm.setAttr("mse",10);

        Result res1 = service.set(acm);

        Mo snmptrap = service.get(new DN("/Ems/1/Ne/1/Product/1/Security/1/SnmpCfg/1/TrapCfg/EMSServer1")).getMo().get(0);






    }

    @Test
    public void testQos()
    {

    }


}
