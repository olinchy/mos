package com.zte.container;


import com.odb.database.HashMapSimpleDB;
import com.zte.container.kernel.BundleContainer;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.storage.MapDbService.DbNameEnum;
import com.zte.mos.util.tools.Prop;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

public class TestContainer {

    private static BundleContainer container;

    @BeforeClass
    public static void setup() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("confpath", ".");
        properties.setProperty("storage_path", "ut_store");
        Prop.set(properties);
        for (DbNameEnum db : DbNameEnum.values()){
            MapDbService.setService(db, new HashMapSimpleDB<String, DataUnit>());
        }

        container = new BundleContainer("//127.0.0.1:9000/client/BUNDLE");
        container.getServicesHolder().setRegService(new UTRegService());
        container.start();
    }

    @Test
    public void t(){

    }

}
