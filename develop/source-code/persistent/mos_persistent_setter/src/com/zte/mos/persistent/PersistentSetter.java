package com.zte.mos.persistent;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.persistent.xml.MoXmlPersistentContext;
import com.zte.mos.util.Singleton;

@PreLoad
public class PersistentSetter
{

    public PersistentSetter() throws Exception
    {
        Singleton.getInstance(PersistentHolder.class).setPersistent(new MoXmlPersistentContext());
    }
}
