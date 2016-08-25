package com.zte.mos.node;

import com.mos.UTBundleService;
import com.odb.database.HashMapSimpleDB;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.domain.MosSettings;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.util.Scan;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.mos.util.tools.Prop;
import com.zte.persist.BundleToEmsBind;
import com.zte.persist.EmsDB;
import com.zte.scope.bundle.BundleDBFactory;
import com.zte.scope.bundle.BundleServiceFactory;
import com.zte.scope.bundle.NeToBundleBind;
import com.zte.scope.ems.EmsDomain;
import com.zte.scope.ems.EmsRoutingTable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;
import static junit.framework.Assert.assertNotNull;

public class TestCreateNE
{
    @BeforeClass
    public static void setup() throws MOSException
    {
        getInstance(MosSettings.class).set("Root");
        getInstance(MosSettings.class).setProcessName("MOS");

        Set<Class<Object>> set = Scan.getClasses("com.zte.mos.persistent", Object.class);
        PreLoadScanner scanner = new PreLoadScanner();
        scanner.scan(set);

        ServiceStarter.start();
        EmsDB.setService(new HashMapSimpleDB<String, BundleToEmsBind>());
        BundleDBFactory.setService(new HashMapSimpleDB<String, NeToBundleBind>());
        BundleServiceFactory.setService(new UTBundleService());
    }

    private static Mo getNeMo() throws MOSException
    {
        Mo neMo = new Mo("Ne");
        neMo.setDn(new DN("/Ems/1/Ne/1"));
        neMo.setAttr("netype", "nr8250");
        neMo.setAttr("longitude", "27.66");
        neMo.setAttr("latitude", "56.22");
        neMo.setAttr("location", "Tianjin");
        neMo.setAttr("siteId", "1");
        neMo.setAttr("siteName", "AA");
        neMo.setAttr("ipV4", "/Ems/1/IpV4/127.0.0.1");
        return neMo;
    }

    @Test
    public void Given_add_ne_When_commit_Then_a_rout_and_ne_added() throws MOSException
    {

        Properties properties = new Properties();
        properties.setProperty("confpath", ".");
        properties.setProperty("storage_path", "ut_store");
        Prop.set(properties);

        DN dn = new DN("/Ems/1/Ne/1");
        MoMsg createReq = new MoConfigMsg(MoCmds.MoCreateRequest, dn, getNeMo());

        MOS mos = MosFactory.createMOS(getInstance(MetaStringStore.class).getMeta("ems", "v1540", "Root"),
                                       MOS.ROOT_INTERNAL_DN,
                                       null, null);

        EmsDomain ems = new EmsDomain("/Ems/1", mos);
        ems.allocateTrasnID(createReq);
        ems.createBundle("ut_001", "//127.0.0.1:9000/client/BUNDLE");

        ems.onMsg(createReq);

        MoAckMsg ack = new MoAckMsg(createReq.getTransactionID(), MoAckMsg.Ack.commit, "/Ems/1/Ne/1");
        ems.onMsg(ack);
        assertNotNull(EmsRoutingTable.service.getDestBundle(dn));
        //assertNotNull(ems.getMos().getMO("/Ems/1/Ne/1", new Maybe<Integer>(null)));
    }
}
