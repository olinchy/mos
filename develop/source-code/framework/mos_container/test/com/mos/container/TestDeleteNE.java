package com.mos.container;

import com.zte.mos.container.BundleObject;
import com.zte.mos.container.RealBundleDomain;

/**
 * Created by luoqingkai on 15-8-13.
 *
 */
public class TestDeleteNE {

    private String specialNeId = "/Ems/1/Ne/999/Product/1";
    private RealBundleDomain bundleDomain = RealBundleDomain.getInstance();
    private BundleObject obj;
/*    private NeIdentity ne;

    private static int neNum = 1;

    private static String getNeID() {
        String id = "/Ems/1/Ne/" + neNum + "/Product/1";
        neNum++;
        return id;
    }

    @BeforeClass
    public static void setupStatic() throws Exception {
        MosFactory.setBuilder(new UtMosBuilder());
        NeDB.setService(new HashMapSimpleDB<String, NeIdentity>());
    }

    @Before
    public void setup() throws Exception {
        prepare();
    }

    @After
    public void tearDown() {

    }

    private void prepare() throws Exception {
        this.specialNeId = getNeID();
        ne = new NeIdentity("NR8250", "", specialNeId, "127.0.0.1");
        obj = new BundleObject(bundleDomain);
    }

    private void initAnNe() throws Exception {
        int transId_1 = 1;

        obj.createNeDomain(ne, transId_1);
        assertNull(NeDB.getService().get(specialNeId));

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(1), commit, specialNeId);
        obj.onMessage(ack);
        assertNotNull(NeDB.getService().get(specialNeId));
    }

    @Test
    public void Given_an_ne_When_delete_Then_domain_is_deleted_in_transaction() throws Exception {
        initAnNe();

        int transId_2 = 2;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());
    }

    @Test(expected = LockedByTransException.class)
    public void Given_an_deleted_ne_When_delete_by_other_Then_throw_exception() throws Exception {
        initAnNe();

        int transId_2 = 2;
        int transId_3 = 3;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());

        obj.deleteNeDomain(specialNeId, transId_3);
    }

    @Test
    public void Given_an_deleted_ne_When_delete_by_ohter_Then_state_stay_unchanged() throws Exception {
        initAnNe();

        int transId_2 = 2;
        int transId_3 = 3;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());

        try{
            obj.deleteNeDomain(specialNeId, transId_3);
            fail();
        }catch (LockedByTransException e){
            // do nothing
        }
        assertEquals(Deleted, neDomain.getState());
    }

    @Test
    public void Given_an_deleted_ne_When_commit_Then_delete_accepted() throws Exception {
        initAnNe();

        int transId_2 = 2;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(transId_2), commit, specialNeId);
        obj.onMessage(ack);

        assertEquals(Empty, neDomain.getState());
        assertNull(NeDB.getService().get(specialNeId));
        assertNull(this.bundleDomain.getChild(specialNeId));
        assertNull(this.bundleDomain.getChild(specialNeId, transId_2));
    }

    @Test
    public void Given_an_deleted_ne_When_commit_by_ohter_Then_throw_exception() throws Throwable {
        initAnNe();

        int transId_2 = 2;
        int transId_3 = 3;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(transId_3), commit, specialNeId);
        Result res = obj.onMessage(ack);
        assertTrue(res instanceof Failure);
        BatchException be = (BatchException) ((Failure) res).exception();

        assertTrue(be.getExceptionList().get(0) instanceof NoSuchTransException);

    }

    @Test
    public void Given_an_deleted_ne_When_commit_by_ohter_Then_state_stay_unchanged() throws Exception {

        initAnNe();

        int transId_2 = 2;
        int transId_3 = 3;

        obj.deleteNeDomain(specialNeId, transId_2);
        NeDomain neDomain = bundleDomain.getChild(specialNeId, transId_2);
        assertNull(neDomain);
        neDomain = bundleDomain.getChild(specialNeId);
        assertNotNull(neDomain);
        assertEquals(Deleted, neDomain.getState());

        MoAckMsg ack = new MoAckMsg(new Maybe<Integer>(transId_3), commit, specialNeId);
        Result res = obj.onMessage(ack);
        assertTrue(res instanceof Failure);

        assertEquals(Deleted, neDomain.getState());
    }
*/
}
