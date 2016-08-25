package com.zte.mos.app.switchmodel.framework;

import com.zte.app.common.util.MosUtil;
import com.zte.mos.app.switchmodel.ModelSwitchFtContext;
import com.zte.mos.app.switchmodel.util.FileAccess;
import com.zte.mos.comparator.BerkeleyDBComparator;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.node.app.ft.modelswitch.ModelSwitchApp;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.DeliverService;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.zte.mos.app.switchmodel.service.ModelSwitchEventMonitor.registerObserver;
import static com.zte.mos.app.switchmodel.service.ModelSwitchResult.getModelSwitchResult;
import static com.zte.mos.app.switchmodel.service.NeIdAllocator.allocNeId;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/24/16.
 */
public class ModelSwitchFt
{
    private static final String REVERSE_CFG_ACTION = "reverseCfg";
    private static final String MODEL_CHANGE_ACTION = "modelChange";

    private static final String PRODUCT_SUFFIX = "/Product/1";
    private static final String FIXED_NE_IP = "192.168.0.1";

    public List<Map<String, List<Pair<Mo, Mo>>>> getResult()
    {
        return result;
    }

    private List<Map<String, List<Pair<Mo, Mo>>>> result = new ArrayList<Map<String, List<Pair<Mo, Mo>>>>();

    private int currentNeId;

    MosUtil util = new MosUtil();


    @BeforeClass
    public static void setupClassEnv()
    {
        setUpReverseCfgFilePath();
        setUpMosNeStorePath();
        setUpOutputStorePath();
    }

    private static void setUpMosNeStorePath()
    {
        System.setProperty("mos_ne_store", "../../../.././build/mos-ne/2.04.01.06/store");

    }

    private static void setUpOutputStorePath()
    {
        System.setProperty("output_ne_store", System.getProperty("user.home") +  "/output_ne_store");
    }

    @AfterClass
    public static void tearDownClassEnv()
    {
        System.setProperty("reverse_file_path", "");
        System.setProperty("mos_ne_store", "");

    }

    private static void setUpReverseCfgFilePath()
    {
        System.setProperty("reverse_file_path", System.getProperty("user.home") +  "/reversecfg");
    }


    @Before
    public void setup()
    {
        setUpTestEnv();
        try
        {
            this.currentNeId = allocNeId();
            util.startApp();
//            new AppSimulator(ModelSwitchApp.class).start();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    public void prepareModelSwitch()
    {
        setupClassEnv();
        setup();
    }

    public void unprepareModelSwitch()
    {
        teardown();
        tearDownClassEnv();
    }


    protected void setUpTestEnv()
    {
        delExistedMoes();
    }

    protected void delExistedMoes()
    {
        util.deleteAllMO(MosUtil.NE_DN_PATH);
        util.deleteAllMO(MosUtil.IPV4_DN_PATH);
    }
    @After
    public void teardown()
    {

    }


    protected List<Map<String, List<Pair<Mo, Mo>>>> doModelSwitchFt(ModelSwitchFtContext context) throws MOSException, IOException
    {
        doModelSwitchTest(context);
        doReverseTest(context);
        return checkBerkleyDb(context);
    }

    protected void doModelSwitchFt(final String ... paras) throws MOSException, IOException
    {
        ModelSwitchFtContext context = new ModelSwitchFtContext()
        {
            @Override
            public String oldModelName()
            {
                return paras[0];
            }

            @Override
            public String oldModelVersion()
            {
                return paras[1];
            }

            @Override
            public String newModelName()
            {
                return paras[2];
            }

            @Override
            public String newModelVersion()
            {
                return paras[3];
            }

            @Override
            public String neType()
            {
                return paras[4];
            }

            @Override
            public String oldCfgPath()
            {
                return paras[5];
            }

            @Override
            public String newCfgPath()
            {
                return paras[6];
            }

            @Override
            public String oldOutputPath()
            {
                return paras[7];
            }

            @Override
            public String newOutputPath()
            {
                return paras[8];
            }
        };

        this.result = doModelSwitchFt(context);
    }


    private List<Map<String, List<Pair<Mo, Mo>>>> summarizeBerkleyMoDb(ModelSwitchFtContext context)
    {
        try
        {
            return new BerkeleyDBComparator().compareBerkleyMoDb(context.oldOutputPath(), context.newOutputPath());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void parseBerkleyDb(ModelSwitchFtContext context)
    {
        try
        {
            new BerkeleyDBComparator().compareDB(
                    context.oldOutputPath(), context.newOutputPath(),
                    context.oldOutputPath(), context.newOutputPath());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }




    private List<Map<String, List<Pair<Mo, Mo>>>> checkBerkleyDb(ModelSwitchFtContext context)
    {
        parseBerkleyDb(context);
        return summarizeBerkleyMoDb(context);
    }

    protected void doReverseTest(ModelSwitchFtContext context) throws MOSException, IOException
    {
        restoreTestEnv();
        createNe(context.neType());
        startLoadModel(context.neType(), context.newModelName(), context.newModelVersion());
        startPollNeState(context.neType());
        startReverse(context.newCfgPath());
        checkReverseOK();
        delay(5);
        copyOutBerkleyDbStore(context.newOutputPath());
    }

    private void startPollNeState(String neType)
    {
        try
        {

            MoEvent moEvent = new MoEvent("NeStateChangeEvent");
            Map<String,Object> oldValueMap = new HashMap<String, Object>();
            Map<String,Object> newValueMap = new HashMap<String, Object>();
            oldValueMap.put("connectionState", "offline");
            newValueMap.put("connectionState", "online");

            moEvent.put("neid", new DN(neDn()).value(-1));
            moEvent.put("netype", neType);
            moEvent.put("ip",ipDn());
            moEvent.put("time",new Date());
            moEvent.put("stateType","connectionState");
            moEvent.put("oldValue",oldValueMap);
            moEvent.put("newValue", newValueMap);


            if (!new DeliverService("SIMU_MODEL_SWITCH_APP").send(moEvent).isSuccess())
            {
                logger(this.getClass()).debug("fail to startPollNeState");
                return ;
            }
            delay(5);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }


    protected void doModelSwitchTest(ModelSwitchFtContext context) throws MOSException, IOException
    {
        restoreTestEnv();
        createNe(context.neType());
        startLoadModel(context.neType(), context.oldModelName(), context.oldModelVersion());
        startPollNeState(context.neType());
        startReverse(context.oldCfgPath());
        checkReverseOK();
        delay(15);
        registerObserver(new ModelSwitchResultObserver(System.getProperty("mos_ne_store"),context.oldOutputPath()));
        startModelSwitch(context);
        checkModelSwitchOk();
//        startReverse(context.newCfgPath());
//        checkReverseOK();
//        delay(6); // for ref db sync
//        copyOutBerkleyDbStore(context.oldOutputPath());

    }

    private void restoreTestEnv()
    {
        delExistedMoes();
        delExistedCfgFiles();
    }

    private void delExistedCfgFiles()
    {
        File dir = new File(System.getProperty("reverse_file_path"));

        if (dir.isDirectory())
        {
            File[] files = dir.listFiles();

            for(File file : files)
            {
                file.delete();
            }
        }
    }

    private void checkModelSwitchOk()
    {
        Assert.assertTrue(getModelSwitchResult());
    }

    private void startModelSwitch(ModelSwitchFtContext context)
    {
        try
        {
            MosServiceHttp serviceHttp = new MosServiceHttp();
            serviceHttp.act(new DN(neDn() + PRODUCT_SUFFIX), MODEL_CHANGE_ACTION, createModelChangeParas(context.newModelName(), context.newModelVersion()));
            delay(20);
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }




    private Pair<String, Object>[] createModelChangeParas(String modelName, String modelVersion)
    {
        List<Pair<String, Object>> list =  new ArrayList<Pair<String, Object>>();
        list.add(new Pair<String, Object>("modelName", modelName));
        list.add(new Pair<String, Object>("modelVersion", modelVersion));
        return list.toArray(new Pair[list.size()]);
    }

    protected void createNe(String neType)
    {
        try
        {
            MosServiceHttp serviceHttp = new MosServiceHttp();
            serviceHttp.startTransaction();
            Result<Mo> result = serviceHttp.add(initMoes(neType));
            if (result.isSuccess())
            {
                serviceHttp.commit();
            }
            else
            {
                serviceHttp.rollback();
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    private Mo[] initMoes(String neType)
    {
        List<Mo> list = new ArrayList<Mo>();
        Mo ip = new Mo("IpV4");
        ip.setDn(new DN(ipDn()));
        ip.setAttr("ip_addr", FIXED_NE_IP);
        list.add(ip);

        Mo ne = new Mo("Ne");
        ne.setDn(new DN(neDn()));
        ne.setAttr("netype", neType);
        ne.setAttr("ipV4", ipDn());
        ne.setAttr("sysType", "20801");
        ne.setAttr("neName", "ccy");
        list.add(ne);

        return list.toArray(new Mo[list.size()]);
    }





    private void copyReverseConfigFile(String path)
    {
        createReverseCfgDir();
        FileAccess.Copy(getCfgFile(path), System.getProperty("reverse_file_path") + "/" + "neid=" + new DN(neDn()).value(-1) + ".xml");
    }

    private File getCfgFile(String path)
    {
        File dir = new File(path);
        if (dir.isDirectory())
        {
            for (File file : dir.listFiles())
            {
                if (file.getName().contains("ODU.xml.backup"))
                {
                    continue;
                }
                else if (file.getName().toUpperCase().contains("MODSTATUS.XML"))
                {
                    continue;
                }
                else if (file.getName().contains("sql.backup"))
                {
                    continue;
                }
                return file;
            }
        }

        return null;
    }

    private void createReverseCfgDir()
    {
        String outputDir = System.getProperty("reverse_file_path");
        if (outputDir != null && !outputDir.isEmpty())
        {
            File file = new File(outputDir);
            if (!file.exists() && !file.isDirectory())
            {
                file.mkdirs();
            }
        }
    }




    protected void startReverse(String cfgPath)
    {
        try
        {
            copyReverseConfigFile(cfgPath);
            MosServiceHttp serviceHttp = new MosServiceHttp();
            Result<ActionRsp> resp =  serviceHttp.act(new DN(neDn() + PRODUCT_SUFFIX), REVERSE_CFG_ACTION);
            if (!resp.isSuccess())
            {
                logger(ModelSwitchFt.class).debug("fail to start reverse ");
                return;
            }
            delay(20);
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
    }



    private void startLoadModel(String neType, String modelName, String modelVersion)
    {
        try
        {
            MosServiceHttp serviceHttp = new MosServiceHttp();
            serviceHttp.act(new DN(neDn() + PRODUCT_SUFFIX), MODEL_CHANGE_ACTION, createModelChangeParas(modelName,modelVersion));
            delay(30);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    private void delay(long seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    protected void checkReverseOK() throws MOSException
    {
        Result<Mo> result = new MosServiceHttp().find("", "Board", new DN(neDn() + "/Product/1"));
        Assert.assertTrue(result.isSuccess());
        Assert.assertTrue(result.getMo().size() > 0);
    }

    private String neDn()
    {
        return "/Ems/1/Ne/" + currentNeId;
    }

    private String ipDn()
    {
        return "/Ems/1/IpV4/" + FIXED_NE_IP;
    }


    private void copyOutBerkleyDbStore(String outPutPath) throws IOException
    {
        String store = System.getProperty("mos_ne_store");
        FileAccess.copyDir(new File(store), new File(outPutPath));
    }



}
