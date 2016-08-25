package com.odb.database.extended_odb;

import com.zte.mos.domain.Group;

/**
 * Created by olinchy on 5/21/14 for MO_JAVA.
 */
public class StOrigin<T extends Group> extends StNull<T>
{
    public StOrigin(T t)
    {
        super(t);
    }

    @Override public boolean isDataDestroyed()
    {
        return false;
    }

}
