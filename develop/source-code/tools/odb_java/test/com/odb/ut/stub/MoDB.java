package com.odb.ut.stub;

import com.odb.database.Odb;
import com.odb.database.extended_odb.CommittedODB;
import com.odb.database.extended_odb.OperatingSet;

/**
 * Created by olinchy on 5/16/14.
 */
public class MoDB
{

    public static Odb<Mo> instance()
    {
        MoIndexer indexer = new MoIndexer();
        return new CommittedODB<Mo>(indexer, new OperatingSet<Mo>(indexer));
    }

    public static Odb<MO_Stub> instance1()
    {
        MO_StubIndexer indexer = new MO_StubIndexer();
        return new CommittedODB<MO_Stub>(indexer, new OperatingSet<MO_Stub>(indexer));
    }
}
