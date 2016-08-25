package com.zte.mos.container;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.annotation.MoEnum;
import com.zte.mos.domain.Action;
import com.zte.mos.domain.ActionRepo;
import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.type.CommonTypeRegister;
import com.zte.mos.util.Scan;
import com.zte.mos.util.msg.MoEvent;
import com.zte.mos.util.msg.MoGetMsg;
import com.zte.mos.util.msg.Template;
import com.zte.mos.util.scaner.MoEnumScanner;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.mos.util.tools.Prop;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.*;

import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_DOMAIN;
import static com.zte.mos.util.Singleton.getInstance;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by luoqingkai on 15-11-17.
 */
public class TestMosLoading {

    private static final String dn_ne = "/Ems/1/Ne/1/Product/1";
    private static final String dn_Shelf = "/Ems/1/Ne/1/Product/1/Shelf/1";
    private static RealBundleDomain bundle;

    @BeforeClass
    public static void prepare() throws MOSException {
        Properties properties = new Properties();
        properties.setProperty("confpath", ".");
        properties.setProperty("storage_path", "ut_store");
        Prop.set(properties);

        for (MapDbService.DbNameEnum db : MapDbService.DbNameEnum.values()){
            MapDbService.setService(db, new HashMapSimpleDB<String, DataUnit>());
        }

        Set<Class<Object>> set = new LinkedHashSet<Class<Object>>();
        set.addAll(Scan.getClasses("com.zte.mos.domain"));
        set.addAll(Scan.getClasses("com.zte.app.smartlink"));
        set.addAll(Scan.getClasses("com.zte.mos.msg.impl"));

        new MoEnumScanner().scan(Scan.getClasses("com.zte.mos.domain", MoEnum.class));
        getInstance(ActionRepo.class).scan(Scan.getClasses("com.zte.mos.domain", Action.class));
        new PreLoadScanner().scan(set);

        CommonTypeRegister.regAll();

        CommServiceFactory.initService();
    }

    @Before
    public void setUp() throws Exception {
        bundle = RealBundleDomain.getInstance();
        bundle.setID("real_bunle");
    }

    @After
    public void tearDown() throws Exception {
        bundle.deleteNeDomain(dn_ne, 9);
        bundle.commit(9, new String[]{dn_ne});
        MapDbService.getDB(NE_DOMAIN).delete(dn_ne);
    }

    private MoEvent getModelStateEvent(){
        MoEvent moEvent = new MoEvent("NeModelChangeEvent");
        Map<String,Object> oldValueMap = new HashMap<String, Object>();
        Map<String,Object> newValueMap = new HashMap<String, Object>();
        oldValueMap.put("modelName", "nr8250");
        oldValueMap.put("modelVersion", "v242");
        newValueMap.put("modelName", "nr8250");
        newValueMap.put("modelVersion", "v242");

        moEvent.getContent().put("neid", "1");
        moEvent.getContent().put("netype", "");
        moEvent.getContent().put("ip","");
        moEvent.getContent().put("time",new Date());
        moEvent.getContent().put("oldValue",oldValueMap);
        moEvent.getContent().put("newValue", newValueMap);
        return moEvent;
    }

    @Test
    public void Given_ne_When_model_checked_Then_mos_loaded() throws RemoteException, MOSException {
        int transId_add_ne = 1;
        String[] dnList = new String[]{dn_ne};

        BundleObject obj = new BundleObject(bundle);
        NeIdentity identity = new NeIdentity(dn_ne, "127.0.0.1", "nr8250");
        obj.createNeDomain(identity, transId_add_ne);
        bundle.commit(transId_add_ne, dnList);

        MoEvent checkedEvent = getModelStateEvent();
        obj.onMsg(checkedEvent);
//        int transId_add_ne = 1;
//        int transId_set_model = 2;
//
//        String[] dnList = new String[]{dn_ne};
//        try {
//
//            NeIdentity identity = new NeIdentity("", "", dn_ne, "127.0.0.1");
//            bundle.createNeDomain(identity, transId_add_ne);
//            bundle.commit(transId_add_ne, dnList);
//
//            bundle.setNeModel(dn_ne, "v241", "nr8120", "checked", transId_set_model);
//            bundle.commit(transId_set_model, dnList);
//            MoMsg msg = new MoGetMsg(new Template(), new Maybe<Integer>(-2), dn_Shelf);
//            Result res = bundle.onMsg(msg);
//            assertTrue(res.isSuccess());
//
//            try {
//                Thread.sleep(15 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } catch (MOSException e) {
//            fail();
//        }

    }

    @Test
    public void Given_ne_model_checked_When_detected_Then_online_sync_begin() {
        int transId_add_ne = 1;
        int transId_set_model = 2;

        String[] dnList = new String[]{dn_ne};
        try {

            NeIdentity identity = new NeIdentity(dn_ne, "127.0.0.1", "nr8250");
            bundle.createNeDomain(identity, transId_add_ne);
            bundle.commit(transId_add_ne, dnList);

            //bundle.setNeModel(dn_ne, "v242", "nr8960a", "checked", transId_set_model);
            bundle.commit(transId_set_model, dnList);
            MoMsg msg = new MoGetMsg(new Template(), new Maybe<Integer>(-2), dn_Shelf);
            Result res = bundle.onMsg(msg);
            assertTrue(res.isSuccess());

            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MOSException e) {
            fail();
        }

    }
}
