package com.zte.mos.ems;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.exception.MOSException;
import com.zte.mos.node.VersionRegImpl;
import com.zte.mos.service.VersionReg;
import com.zte.persist.BundleToEmsBind;
import com.zte.persist.EmsDB;
import com.zte.scope.bundle.BundleDBFactory;
import com.zte.scope.bundle.NeToBundleBind;
import com.zte.scope.ems.EmsDomain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;


public class TestRegBundle {

    @Before
    public void setup(){
        EmsDB.setService(new HashMapSimpleDB<String, BundleToEmsBind>());
        BundleDBFactory.setService(new HashMapSimpleDB<String, NeToBundleBind>());
    }

    @Test
    public void Given_empty_ems_When_reg_a_new_bundle_Then_success() {
        try {
            EmsDomain ems = new EmsDomain("/Ems/1", new UtMos4RegBundle());
            VersionReg reg = new VersionRegImpl(ems);
            String bundleId = reg.register("2.04.01.06", "//127.0.0.1:9000/client/BUNDLE");
            BundleToEmsBind bundle = EmsDB.getService().get(bundleId);
            Assert.assertNotNull(bundle);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void Given_registed_bundle_When_reg_again_Then_update(){
        try {
            EmsDomain ems = new EmsDomain("/Ems/1", new UtMos4RegBundle());
            VersionReg reg = new VersionRegImpl(ems);
            String bundleId = reg.register("2.04.01.06", "//127.0.0.1:9000/client/BUNDLE");
            BundleToEmsBind bundle = EmsDB.getService().get(bundleId);
            Assert.assertNotNull(bundle);

            reg.update("2.04.01.06", "//127.0.0.1:8000/client/BUNDLE", bundleId);
            Assert.assertEquals("//127.0.0.1:8000/client/BUNDLE", ems.getBundle(bundleId).getAddress());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = RemoteException.class)
    public void Given_a_bundle_not_in_ems_When_reg_Then_throws_exception() throws MOSException, RemoteException {
        EmsDomain ems = new EmsDomain("/Ems/1", new UtMos4RegBundle());
        VersionReg reg = new VersionRegImpl(ems);
        String bundleId = reg.register("2.04.01.06", "//127.0.0.1:9000/client/BUNDLE");
        BundleToEmsBind bundle = EmsDB.getService().get(bundleId);
        Assert.assertNotNull(bundle);

        reg.update("2.04.01.06", "//127.0.0.1:8000/client/BUNDLE", "sdfsdfsfsd");
    }
}
