package com.odb.ut.stub;

import com.odb.database.Key;

/**
 * Created by olinchy on 5/19/14.
 */
public class MoIndexer implements com.odb.database.Indexer<Mo>
{
    @Override
    public Key key(Mo data)
    {
        return new Key(data.id);
    }

    @Override
    public int compare(Key o1, Key o2)
    {
        return 0;
    }
}
