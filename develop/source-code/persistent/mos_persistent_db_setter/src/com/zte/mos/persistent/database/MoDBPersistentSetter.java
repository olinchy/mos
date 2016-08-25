package com.zte.mos.persistent.database;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.persistent.PersistentHolder;
import com.zte.mos.util.Singleton;

/**
 * Created by 10171693 on 15-2-12.
 */
@PreLoad
public class MoDBPersistentSetter
{

    public MoDBPersistentSetter() throws Exception
    {
        Singleton.getInstance(PersistentHolder.class).setPersistent(new MoDBPersistentContext());
    }
}
