package com.zte.scope;

import com.mos.UTBundleService;
import com.mos.UT_MOS;
import com.odb.database.HashMapSimpleDB;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.GetMetaResult;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoGetMetaMsg;
import com.zte.persist.BundleToEmsBind;
import com.zte.persist.EmsDB;
import com.zte.scope.bundle.BundleDBFactory;
import com.zte.scope.bundle.BundleServiceFactory;
import com.zte.scope.bundle.NeToBundleBind;
import com.zte.scope.ems.EmsDomain;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by luoqingkai on 15-8-20.
 */
public class TestGetNeMeta {

    private void init(){
        EmsDB.setService(new HashMapSimpleDB<String, BundleToEmsBind>());
        BundleDBFactory.setService(new HashMapSimpleDB<String, NeToBundleBind>());
        BundleServiceFactory.setService(new UTBundleService());
    }
    @Test
    public void Given_empty_ems_When_get_ne_meta_Then_meta_got() throws MOSException {
        init();
        MoGetMetaMsg msg = new MoGetMetaMsg("/Ems/1/Ne/1", "", false);
        MOS mos = new UT_MOS();
        EmsDomain ems = new EmsDomain("/Ems/1", mos);
        Result result = ems.onMsg(msg);
        assertTrue(result instanceof GetMetaResult);
        GetMetaResult myResult = (GetMetaResult)result;
        assertNotNull(myResult.getMeta());
    }
}
