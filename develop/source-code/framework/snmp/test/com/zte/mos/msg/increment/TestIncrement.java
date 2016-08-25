package com.zte.mos.msg.increment;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.msg.impl.util.ftp.FtpInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ccc on 16-3-24.
 */
public class TestIncrement
{
    @Before
    public void setUp() throws Exception
    {
        setMoc();
       // reverse();
       //TestUtil.clearEnv();
    // TestUtil.bulidMosEnv("increment.xml");
    }



    private void setMoc()
    {
        //prepareFtp();
        setFtpInfo();
    }

    private void setFtpInfo()
    {
        FtpInfo info = new FtpInfo();
        info.setUseHome(getFtpHomePath());
        info.setFtpUserName("mwRM");
        info.setFtpUserPassWord("1234");
        info.setIpAddress("127.0.0.1");
     //   IncrementalExecutor.setFtpInfo(info);
    }

    private void prepareFtp()
    {
       // FtpManager.setFtpManager(new FtpManagerMock(getFtpHomePath()));

    }

    private String getFtpHomePath()
    {
        String filePath = TestUtil.getPath("increment.xml");

        return filePath.substring(0,filePath.lastIndexOf("increment.xml"));
    }



 //  @Test
    public void testTransOnOneNe()
    {
     Mo mdMo = new Mo("CfmMD");
        mdMo.setDn(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/mm"));
        mdMo.setAttr("level", "6");

        MosService service = null;
        Result res = null;
        try
        {
            service = new MosServiceHttp();

          service.startTransaction();
            Result res1 =  service.add(mdMo);
            Result res2 = service.del(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/dd"));

            Mo cc =
           service.get(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/v9")).getMo().get(0);
            cc.setAttr("level", "6");


            Result res3 = service.set(cc);


            if(res1.isSuccess() && res2.isSuccess() && res3.isSuccess())
            {
                System.out.print("CC");
              //
            }
            service.commit();
            System.out.print("kkkk");
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }
    @Test
    public void testReturnValue() throws Exception
    {
        Mo mdMo1 = new Mo("CfmMD");
        mdMo1.setDn(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/cc"));
        mdMo1.setAttr("level", "6");


        MosService service    = new MosServiceHttp();

        service.startTransaction();

        Result res1 =  service.add(mdMo1);
        Result res2 = null;

        if(res1.isSuccess())
        {
            System.out.println("kkkkkkk");
            service.commit();

        }




    }
 /*   @Test
    public void testMap()
    {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map.put("aaa",1);
        map.put("bbb",2);



    }*/

  //  @Test
    public void testOneTransOnNes()
    {
        Mo mdMo1 = new Mo("CfmMD");
        mdMo1.setDn(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/mm1"));
        mdMo1.setAttr("level", "6");

        Mo mdMo11 = new Mo("CfmMD");
        mdMo1.setDn(new DN("/Ems/1/Ne/1/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/m11"));
        mdMo1.setAttr("level", "6");
        Mo mdMo2 = new Mo("CfmMD");
        mdMo2.setDn(new DN("/Ems/1/Ne/2/Product/1/EthProtocol/EthL2Protocol/Cfm/MD/mm1"));
        mdMo2.setAttr("level", "6");



        try
        {
            MosService service    = new MosServiceHttp();

          //  service.startTransaction();

            Result res1 = service.add(mdMo1);
            Result res2 = service.add(mdMo2);
            Result res3 = service.add(mdMo11);



            // service.commit();
            System.out.print("kkkk");
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }


}
