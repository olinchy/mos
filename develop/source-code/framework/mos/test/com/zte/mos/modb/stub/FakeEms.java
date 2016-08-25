package com.zte.mos.modb.stub;

/**
 * Created by app on 14-6-11.
 */

import com.zte.mos.domain.BaseManagementObject;

@SuppressWarnings("serial")
@com.zte.mos.annotation.Mo(tid = 0)
public class FakeEms extends BaseManagementObject
{
    public FakeEms()
    {
        dn = "/Ems/1";
    }
}

