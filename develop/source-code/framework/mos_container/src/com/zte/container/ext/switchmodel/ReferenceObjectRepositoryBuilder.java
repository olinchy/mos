package com.zte.container.ext.switchmodel;

import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.Walker;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.persist.EntityCursor;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;

/**
 * Created by ccy on 7/5/16.
 */
public class ReferenceObjectRepositoryBuilder
{
    public static ReferenceObjectsRepository buildRefObjectsRepository(ReferenceDBInBerkeley db) throws MOSException
    {
        final ReferenceObjectsRepository repository = new ReferenceObjectsRepository();
        db.all(
                new Walker<String, ReferenceObject>()
                {
                    @Override
                    public void walk(BerkeleyDBIndexer<String, ReferenceObject> indexer) throws MOSException
                    {
                        CursorConfig config = new CursorConfig();
                        config.setReadUncommitted(true);
                        EntityCursor<ReferenceObject> cursor = null;
                        try
                        {
                            cursor = indexer.getPrimaryIndex().entities();

                            for (ReferenceObject obj : cursor)
                            {
                                repository.put(obj);
                            }
                        }
                        finally
                        {
                            cursor.close();
                        }
                    }
                });
        return repository;
    }

}
