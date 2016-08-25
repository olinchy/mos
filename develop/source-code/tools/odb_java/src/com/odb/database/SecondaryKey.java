package com.odb.database;

import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.Transaction;

/**
 * Created by olinchy on 15-7-13.
 */
public interface SecondaryKey
{
    void create();

    void close(Transaction txn);

    boolean match(String keyword);

    SecondaryDatabase database();

    void remove(Transaction txn);

    void rename(String name);

    String getDBName();

    void setDatabase(SecondaryDatabase secondaryDatabase);
}
