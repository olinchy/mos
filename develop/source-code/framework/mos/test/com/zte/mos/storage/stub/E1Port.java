package com.zte.mos.storage.stub;

import com.zte.mos.annotation.Mo;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.domain.BaseManagementObject;

/**
 * Created by olinchy on 1/12/15 for mosjava.
 */
@Mo(tid = 126)
public class E1Port extends BaseManagementObject
{
    @MoAttribute(field = "PORTSTATE")
    public long state = 1l;
}
