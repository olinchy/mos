package com.zte.smartlink.addressbook.service;

import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Walker;
import com.sleepycat.je.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Visitor;
import com.zte.smartlink.Address;

/**
 * Created by olinchy on 16-1-8.
 */
public class AddressCacheWalker implements Walker<Address, ClientAddress>
{
    public AddressCacheWalker(Visitor<ClientAddress> visitor)
    {
        this.visitor = visitor;
    }

    private final Visitor<ClientAddress> visitor;

    @Override
    public void walk(
            BerkeleyDBIndexer<Address, ClientAddress> indexer) throws MOSException
    {
        Cursor cursor = null;
        CursorConfig config = new CursorConfig();
        config.setReadUncommitted(true);
        try
        {
            cursor = indexer.getDatabase().openCursor(null, config);
            DatabaseEntry keyEntry = new DatabaseEntry();
            DatabaseEntry dataEntry = new DatabaseEntry();

            while (OperationStatus.SUCCESS.equals(cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT)))
            {
                visitor.visit(((BerkeleyDBObjectIndexer<Address, ClientAddress>) indexer).entryToObject(dataEntry));
            }
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
}
