package com.odb.database;

import com.sleepycat.je.CacheMode;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.StatsConfig;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.Prop;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by olinchy on 15-7-7.
 */
public class BerkeleyDB<PK, T> implements PersistDB<PK, T>
{
    private static Environment env;
    private static Timer envStatusWatcher;
    private static Logger logger = Logger.logger(BerkeleyDB.class);

    static
    {
        if (!(Prop.get("readOnly") != null && Prop.get("readOnly").equalsIgnoreCase("true")))
        {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setCachePercent(Integer.parseInt(setCacheSize()));
            envConfig.setSharedCache(true);
            envConfig.setCacheMode(CacheMode.UNCHANGED);


            File file = new File(Prop.get("storage_path"));
            if (!file.exists())
            {
                file.mkdirs();
            }
            env = new Environment(file, envConfig);

            if (Prop.get("debug_bdb") != null && Prop.get("debug_bdb").equalsIgnoreCase("true"))
            {
                envStatusWatcher = new Timer();
                final StatsConfig statsConfig = new StatsConfig();
//                envStatusWatcher.schedule(new TimerTask()
//                {
//                    @Override
//                    public void run()
//                    {
//                        logger.info("outputting env statues ------------------");
//                        logger.info(env.getStats(statsConfig));
//                    }
//                }, 1000, 60000);
            }
        }
    }

    private static String setCacheSize()
    {
        String cacheSize = Prop.get("cache_size");
        if (cacheSize == null || !cacheSize.matches("[\\d]+"))
        {
            cacheSize = "30";
        }
        return cacheSize;
    }

    private final BerkeleyDBIndexer<PK, T> primaryIndex;

    public BerkeleyDB(BerkeleyDBIndexer<PK, T> primaryIndex, Environment customEnv)
    {
        this.primaryIndex = primaryIndex;
        primaryIndex.createDB(customEnv);
    }

    public BerkeleyDB(BerkeleyDBIndexer<PK, T> primaryIndex)
    {
        this.primaryIndex = primaryIndex;
        primaryIndex.createDB(env);
    }

    public void all(Walker<PK, T> walker) throws MOSException
    {
        primaryIndex.all(walker);
    }

    public void remove(PK key) throws BerkeleyDBException
    {
        primaryIndex.remove(key);
    }

    public T get(PK key) throws BerkeleyDBException
    {
        return primaryIndex.get(key);
    }

    public void put(T data) throws MOSException
    {
        primaryIndex.put(data);
    }

    public void stop()
    {
        primaryIndex.stop();
    }

    public void clearAll()
    {
        primaryIndex.clearAll();
    }

    public void sync()
    {
        primaryIndex.sync();
    }

    @Override
    public Indexer<T> getPrimaryIndex()
    {
        return (Indexer<T>) primaryIndex;
    }
}
