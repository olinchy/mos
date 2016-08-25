package com.mos.container;

import com.zte.mos.container.BundleObject;
import com.zte.mos.container.RealBundleDomain;


public class TestUpdateNE {
    private String neId = "/Ems/1/Ne/1/Product/1";
    private RealBundleDomain bundleDomain = null;
    private BundleObject obj = null;
/*    private NeIdentity ne = new NeIdentity("NR8250", "", neId, "127.0.0.1");
    private static int neNum = 1;

    private static String getNeID() {
        String id = "/Ems/1/Ne/" + neNum + "/Product/1";
        neNum++;
        return id;
    }

    @Before
    public void setup() throws Exception {
        neId = getNeID();
        ne = new NeIdentity("NR8250", "", neId, "127.0.0.1");
        MosFactory.setBuilder(new UtMosBuilder());
        NeDB.setService(new HashMapSimpleDB<String, NeIdentity>());

        bundleDomain = RealBundleDomain.getInstance();
        obj = new BundleObject(bundleDomain);

        obj.createNeDomain(ne, 1);
        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), MoAckMsg.Ack.commit, neId);
        obj.onMessage(ack);
    }

    @Test(expected = NullPointerException.class)
    public void Given_new_added_ne_When_update_model_version_Then_new_mos_loaded() throws Exception {
        NeDomain neDomain = bundleDomain.getChild(neId);
        assertNull(neDomain.getMos());
        //obj.setNeModel(this.neId, "v241", "NR8250", "checked",2);
        neDomain = bundleDomain.getChild(neId);
        assertNotNull(neDomain.getMos());
    }

    @Test
    public void Given_new_added_ne_When_update_model_to_new_version_Then_poll_start() {

    }
    */
}
