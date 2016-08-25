package com.zte.persist;

import com.odb.database.SimpleDB;
import com.odb.database.SingleKeyDB;


/**
 * Created by luoqingkai on 15-7-29.
 */
public class EmsDB {

    private static SimpleDB<String, BundleToEmsBind> service = null;

    public static SimpleDB<String, BundleToEmsBind> getService(){
        if (service == null){
            service = new SingleKeyDB<String, BundleToEmsBind>(new BundleToEmsIndexer());
        }
        return service;
    }


    public static void setService(SimpleDB<String, BundleToEmsBind> userSv){
        service = userSv;
    }


}
