package com.zte.mos.container;

import com.odb.database.SimpleDB;
import com.odb.database.SingleKeyDB;
import com.sleepycat.je.CacheMode;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.zte.mos.exception.MOSException;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDBIndexer;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.tools.Prop;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static com.zte.mos.storage.MapDbService.DbNameEnum.BUNDLE;

/**
 * Created by ccy on 7/14/16.
 */
public class TestSingleKeyDb
{
    Environment env;
    SimpleDB<String, DataUnit> db;

    public TestSingleKeyDb()
    {
        Properties properties = new Properties();
        properties.put("storage_path", "/home/workspace/git_workspace");
        Prop.set(properties);
                EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(false);
        envConfig.setReadOnly(true);
        envConfig.setSharedCache(true);

        this.env = new Environment(
                new File("/home/workspace/git_workspace/mos-ems/mwne_store"), envConfig);
        this.db = new SingleKeyDB<String, DataUnit>(new MapDBIndexer("BUNDLE", false), this.env);
    }


    @Test
    public void testBdb() throws MOSException
    {
        DataUnit bundle = db.get("BUNDLE");
        Assert.assertTrue(bundle != null);
        Assert.assertTrue(bundle.getData().get("id") != null);

    }
}
