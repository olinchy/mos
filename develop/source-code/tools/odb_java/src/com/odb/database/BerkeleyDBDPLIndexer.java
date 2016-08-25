package com.odb.database;

import com.sleepycat.je.CacheMode;
import com.sleepycat.je.Database;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.*;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.Prop;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-7-22.
 */
public abstract class BerkeleyDBDPLIndexer<PK, E> implements BerkeleyDBIndexer<PK, E>, Indexer<E>
{
    private static Timer timer;
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    public BerkeleyDBDPLIndexer(boolean autoSync)
    {
        this.autoSync = autoSync;
    }

    public BerkeleyDBDPLIndexer()
    {
    }

    protected EntityStore store;
    protected PrimaryIndex<PK, E> primaryIndex;
    private boolean autoSync = true;
    private Environment env;
    private HashMap<String, SecondaryIndex> secondaryIndexes;
    private TimerTask timerTask;

    @Override
    public void createDB(Environment env)
    {
        logger(this.getClass()).info(this.getClass().getSimpleName() + " is creating BDB.");
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setSharedCache(true);
        envConfig.setCacheMode(CacheMode.UNCHANGED);

        File file = new File(Prop.get("storage_path") + File.separator + this.getClass().getSimpleName());
        if (!file.exists())
        {
            file.mkdirs();
        }
        this.env = new Environment(file, envConfig);

//        if (Prop.get("debug_bdb") != null && Prop.get("debug_bdb").equalsIgnoreCase("true"))
//        {
//            envStatusWatcher = new Timer();
//            final StatsConfig statsConfig = new StatsConfig();
//            envStatusWatcher.schedule(new TimerTask()
//            {
//                @Override
//                public void run()
//                {
//                    logger.info("outputting env statues ------------------");
//                    logger.info(env.getStats(statsConfig));
//                }
//            }, 1000, 60000);
//        }

//        this.env = env;

        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setDeferredWrite(true);
        store = new EntityStore(this.env, dbName(), storeConfig);
        startSyncTask();
        this.primaryIndex = createPrimaryIndex();
        this.secondaryIndexes = createSecondIndexes();
    }

    private void startSyncTask()
    {
        if (autoSync)
        {
            timer = new Timer("Timer-" + BerkeleyDBDPLIndexer.class.getSimpleName(), true);
            this.timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    threadPool.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            BerkeleyDBDPLIndexer.this.sync();
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 5000);
        }
    }

    private void stopSyncTask()
    {
        if (timerTask != null)
            timerTask.cancel();
    }

    protected abstract String dbName();

    protected abstract PrimaryIndex<PK, E> createPrimaryIndex();

    protected abstract HashMap<String, SecondaryIndex> createSecondIndexes();

    @Override
    public void all(Walker<PK, E> walker) throws MOSException
    {
        walker.walk(this);
    }

    @Override
    public void remove(PK key) throws BerkeleyDBException
    {
        primaryIndex.delete(key);
    }

    @Override
    public E get(PK key)
    {
        return primaryIndex.get(key);
    }

    @Override
    public void put(E data) throws MOSException
    {
        primaryIndex.put(data);
    }

    @Override
    public void stop()
    {
        stopSyncTask();

        if (secondaryIndexes != null)
        {
            for (SecondaryIndex secondaryIndex : secondaryIndexes.values())
            {
                String name = secondaryIndex.getDatabase().getDatabaseName();
                secondaryIndex.getDatabase().close();
                env.removeDatabase(null, name);
            }
        }
        String primaryName = primaryIndex.getDatabase().getDatabaseName();
        primaryIndex.getDatabase().close();
        env.removeDatabase(null, primaryName);
        store.close();
    }

    @Override
    public void clearAll()
    {
        EntityCursor<PK> keys = null;
        try {
            keys = primaryIndex.keys();
            for (PK key : keys) {
                primaryIndex.delete(key);
            }
        } finally {
            if(keys != null) {
                keys.close();
            }
        }
    }

    @Override
    public void sync()
    {
        try
        {
            store.sync();
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error("fail to sync berkley db , name " + store.getStoreName(), e);
        }
    }

    @Override
    public Database getDatabase()
    {
        return primaryIndex.getDatabase();
    }

    @Override
    public PrimaryIndex<PK, E> getPrimaryIndex()
    {
        return primaryIndex;
    }

    public SecondaryIndex secondIndex(String name)
    {
        if (secondaryIndexes != null)
            return secondaryIndexes.get(name);
        return null;
    }

    public EntityCursor<E> entities()
    {
        return primaryIndex.entities();
    }

    @Override
    public void rename(String dbName)
    {
        String oldName = this.getDatabase().getDatabaseName();
        int i = 1;
        if (secondaryIndexes != null)
        {
            for (SecondaryIndex secondaryIndex : secondaryIndexes.values())
            {
                String name = secondaryIndex.getDatabase().getDatabaseName();
                String newName = name.replace("#" + dbName() + "#", "#" + dbName + "#");
                secondaryIndex.getDatabase().close();
                env.renameDatabase(null, name, newName);
            }
        }

        this.getDatabase().close();
        env.renameDatabase(null, oldName, oldName.replace("#" + dbName() + "#", "#" + dbName + "#"));

        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setDeferredWrite(true);
        store = new EntityStore(env, dbName, storeConfig);
        primaryIndex = createPrimaryIndex();
        this.secondaryIndexes = createSecondIndexes();
    }

    @Override
    public Key key(E data) throws MOSException
    {
        return null;
    }

    @Override
    public int compare(Key o1, Key o2)
    {
        return 0;
    }
}
