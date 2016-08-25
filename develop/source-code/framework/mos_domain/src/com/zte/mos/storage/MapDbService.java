package com.zte.mos.storage;

import com.odb.database.SimpleDB;
import com.odb.database.SingleKeyDB;
import com.sleepycat.je.Environment;

import java.util.HashMap;

public class MapDbService {
    private static HashMap<String, SimpleDB<String, DataUnit>> allDB =
            new HashMap<String, SimpleDB<String, DataUnit>>();

    public static void initService(){
        for (DbNameEnum nameElement : DbNameEnum.values()){
            allDB.put(nameElement.name(),
                    new SingleKeyDB<String, DataUnit>(
                            new MapDBIndexer(nameElement.name())
                    )
            );
        }
    }

    public static void initService(Environment env, boolean autoSync){
        for (DbNameEnum nameElement : DbNameEnum.values()){
            allDB.put(nameElement.name(),
                    new SingleKeyDB<String, DataUnit>(
                            new MapDBIndexer(nameElement.name(), autoSync), env
                    )
            );
        }
    }

    public static SimpleDB<String, DataUnit> getDB(DbNameEnum dbName){
        return allDB.get(dbName.name());
    }

    public static void setService(DbNameEnum dbName, SimpleDB<String, DataUnit> userSv){
        allDB.put(dbName.name(), userSv);
    }

    public enum DbNameEnum{
        NE_DOMAIN,
        RPC_SECURITY,
        SNMP_SECURITY,
        RESTFUL,
        NE_MODEL,
        BUNDLE
    }

}
