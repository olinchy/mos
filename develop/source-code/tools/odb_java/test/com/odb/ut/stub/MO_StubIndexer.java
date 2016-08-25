package com.odb.ut.stub;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.zte.mos.exception.MOSException;

public class MO_StubIndexer implements Indexer<MO_Stub>
{

    @Override
    public Key key(MO_Stub data) throws MOSException
    {
        return new Key(data.name);
    }

    @Override
    public int compare(Key o1, Key o2)
    {
        return 0;
    }
}
