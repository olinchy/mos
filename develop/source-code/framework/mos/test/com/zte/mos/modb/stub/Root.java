package com.zte.mos.modb.stub;

import com.zte.mos.domain.BaseManagementObject;

/**
 * Created by olinchy on 8/7/14 for MO_JAVA.
 */
public class Root extends BaseManagementObject
{
    public GroupOf1EmsInRoot ems = new GroupOf1EmsInRoot(this, "Ems");

    public Root()
    {
        dn = "/";
    }
}
