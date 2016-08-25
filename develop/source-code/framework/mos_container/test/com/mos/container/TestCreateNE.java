package com.mos.container;

import com.mos.UtMosBuilder;
import com.odb.database.HashMapSimpleDB;
import com.zte.mos.container.BundleObject;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.ModelNotSpecException;
import com.zte.mos.imaged.MosFactory;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoConfigMsg;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_DOMAIN;
import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_MODEL;
import static org.junit.Assert.*;

/**
 * Created by luoqingkai on 15-8-10.
 *
 */
public class TestCreateNE {

    private static BundleObject obj = null;
    private static int neNum = 1;

    private static String getNeID() {
        String id = "/Ems/1/Ne/" + neNum + "/Product/1";
        neNum++;
        return id;
    }

    @BeforeClass
    public static void setup() throws RemoteException {
        MosFactory.setBuilder(new UtMosBuilder());
        for (MapDbService.DbNameEnum db : MapDbService.DbNameEnum.values()){
            MapDbService.setService(db, new HashMapSimpleDB<String, DataUnit>());
        }
        RealBundleDomain bundleDomain = RealBundleDomain.getInstance();
        obj = new BundleObject(bundleDomain);
    }

    @Test
    public void Given_empty_bundle_When_create_ne_Then_get_success() throws Exception {

        String neId = getNeID();
        NeIdentity ne = new NeIdentity(neId, "127.0.0.1","nr8250");

        obj.createNeDomain(ne, 1);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));
        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.commit, neId);
        obj.onMessage(ack);
        assertNotNull(MapDbService.getDB(NE_DOMAIN).get(neId));
    }

    @Test
    public void Given_empty_bundle_When_create_ne_and_rollback_Then_stay_empty() throws Exception {
        String neId = getNeID();
        NeIdentity ne = new NeIdentity(neId, "127.0.0.1","nr8250");

        obj.createNeDomain(ne, 1);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));
        String[] neIds = {neId};
        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.rollback, neId);
        obj.onMessage(ack);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));
    }

    @Test(expected = LockedByTransException.class)
    public void Given_not_commit_create_ne_When_del_by_other_trans_Then_exception_thrown() throws Exception {
        String neId = getNeID();
        NeIdentity ne = new NeIdentity(neId, "127.0.0.1","nr8250");

        obj.createNeDomain(ne, 1);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));
        obj.deleteNeDomain(neId, 2);
    }

    @Test
    public void Given_bundle_When_add_ne_Then_ne_added_and_no_mos_start() throws Exception {
        String neId = getNeID();
        NeIdentity ne = new NeIdentity(neId, "127.0.0.1","nr8250");

        obj.createNeDomain(ne, 1);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.commit, neId);
        obj.onMessage(ack);
        assertNotNull(MapDbService.getDB(NE_DOMAIN).get(neId));

        DN dn = new DN(neId + "/Shelf/1/Board/1");
        MoMsg msg = new MoConfigMsg(MoCmds.MoCreateRequest, dn, null);
        Result res = obj.onMessage(msg);
        assertFalse(res.isSuccess());
        assertTrue(res.exception() instanceof ModelNotSpecException);

    }

    @Test
    public void Given_not_specified_ne_When_update_ip_Then_updated() throws Exception {
        String neId = getNeID();
        this.given_not_specified_ne(neId);

        String newIp = "127.0.0.2";
        obj.updateNeIP(neId, newIp, 2);
        DataUnit savedNe = MapDbService.getDB(NE_DOMAIN).get(neId);
        assertEquals("127.0.0.1", savedNe.getData().get("ip"));

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(2), MoAckMsg.Ack.commit, neId);
        obj.onMessage(ack);
        savedNe = MapDbService.getDB(NE_DOMAIN).get(neId);
        assertEquals(newIp, savedNe.getData().get("ip"));
    }

    private void given_not_specified_ne(String neId) throws MOSException, RemoteException {

        NeIdentity ne = new NeIdentity(neId, "127.0.0.1","nr8250");

        obj.createNeDomain(ne, 1);
        assertNull(MapDbService.getDB(NE_DOMAIN).get(neId));

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.commit, neId);
        obj.onMessage(ack);
        DataUnit modelUnit = MapDbService.getDB(NE_MODEL).get(neId);
        assertNull(modelUnit);
    }

    @Test
    public void Given_not_specified_ne_When_update_ip_and_rollback_Then_stay_unchanged() throws Exception {
        String neId = getNeID();
        this.given_not_specified_ne(neId);

        String newIp = "127.0.0.2";
        obj.updateNeIP(neId, newIp, 2);
        DataUnit savedNe = MapDbService.getDB(NE_DOMAIN).get(neId);
        assertEquals("127.0.0.1", savedNe.getData().get("ip"));
        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.rollback, neId);
        obj.onMessage(ack);
        savedNe = MapDbService.getDB(NE_DOMAIN).get(neId);
        assertEquals("127.0.0.1", savedNe.getData().get("ip"));
    }
}
