package com.zte.scope;

import com.mos.UTBundleService;
import com.mos.UT_MOS;
import com.odb.database.HashMapSimpleDB;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.mos.util.msg.MoFindMsg;
import com.zte.mos.util.msg.Template;
import com.zte.persist.BundleToEmsBind;
import com.zte.persist.EmsDB;
import com.zte.scope.bundle.BundleDBFactory;
import com.zte.scope.bundle.BundleServiceFactory;
import com.zte.scope.bundle.NeToBundleBind;
import com.zte.scope.ems.EmsDomain;
import org.junit.Test;

import static com.zte.mos.util.msg.Template.FieldTypes.all;

/**
 * Created by luoqingkai on 15-8-28.
 */
public class TestFindNE {
    private static Mo getNeMo() throws MOSException {
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
    public void Given_existed_ne_When_find_Then_ne_returned() throws MOSException {
        EmsDB.setService(new HashMapSimpleDB<String, BundleToEmsBind>());
        BundleDBFactory.setService(new HashMapSimpleDB<String, NeToBundleBind>());
        BundleServiceFactory.setService(new UTBundleService());
        MoCommandExecutor.setUserHandler(new UT_ACK());
        MoCommandExecutor.setUserHandler(new UT_CREATE_MO());

        DN dn = new DN("/Ems/1/Ne/1");
        MoMsg createReq = new MoConfigMsg(MoCmds.MoCreateRequest, dn, getNeMo());

        MOS mos = new UT_MOS();
        EmsDomain ems = new EmsDomain("/Ems/1", mos);
        ems.allocateTrasnID(createReq);
        ems.createBundle("ut_001", "//127.0.0.1:9000/client/BUNDLE");

        ems.onMsg(createReq);

        MoAckMsg ack = new MoAckMsg(createReq.getTransactionID(), MoAckMsg.Ack.commit, "/Ems/1/Ne/1");
        ems.onMsg(ack);

        MoFindMsg find = new MoFindMsg(
        "Ne.version>='2.04.01.08' && Ne.netype=='nr8250'",
        new Template(all),
        new Maybe<Integer>(null),
        "/Ems/1/Ne",
        "Ne");

        Result result = ems.onMsg(find);

    }
}
