package com.zte.scope.bundle;

import com.odb.database.SimpleDB;
import com.odb.database.SingleKeyDB;

/**
 * Created by luoqingkai on 15-8-3.
 */
public class BundleDBFactory {
    private static SimpleDB<String, NeToBundleBind> service = null;

    public static SimpleDB<String, NeToBundleBind> newService(String dbName){
        if (service == null){
            return new SingleKeyDB<String, NeToBundleBind>(new NeToBundleIndexer(dbName));
        }
        return service;
    }

    public static void setService(SimpleDB<String, NeToBundleBind> userSv){
        service = userSv;
    }
}
