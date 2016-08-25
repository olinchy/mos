package com.zte.mos.modb.stub;

import com.zte.mos.annotation.MoChild;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 8/14/14 for MO_JAVA.
 */
public class CloneParent extends BaseManagementObject
{
    public int id = 0;
    public String value = "0";

    @MoChild()
    public GroupOf100CloneChildInCloneParent cloneChildGroupOf = new GroupOf100CloneChildInCloneParent(this, "Child");

    @Override
    public DN dn()
    {
        return new DN("/");
    }
}
