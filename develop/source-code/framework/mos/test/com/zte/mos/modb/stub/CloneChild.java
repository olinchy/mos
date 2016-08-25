package com.zte.mos.modb.stub;

import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 8/14/14 for MO_JAVA.
 */
public class CloneChild extends BaseManagementObject
{
    @Override
    public DN dn()
    {
        return new DN("/Child/1");
    }
}
