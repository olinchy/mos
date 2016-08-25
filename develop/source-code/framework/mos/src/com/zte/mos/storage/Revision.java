package com.zte.mos.storage;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.util.basic.Logger;

import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-3.
 * The class to process revision of mos data
 */
public class Revision
{
    private static final Logger log = logger(Revision.class);
    private int revision = 0;

    public final synchronized List<Log<ManagementObject>> commit(Odb<ManagementObject> odb) throws MOSException
    {
        List<Log<ManagementObject>> lst = odb.commit();
        revisionIncrease();
        return lst;
    }

    public final synchronized void increase()
    {
        revisionIncrease();
    }

    private final void revisionIncrease()
    {
        if (revision == 0)
        {
            revision = 1;
        }
    }

    public int getDisplayRevision()
    {
        return revision == 0 ? 0 : 1;
    }

    public final synchronized void reset()
    {
        revision = 0;
    }
}
