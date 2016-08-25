package com.zte.mos.modb.stub;

import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 8/13/14 for MO_JAVA.
 */
public class PP extends Mo
{
    @MoChild()
    @MoSet(count = 100)
    public GroupOf100ParentInPP parent = new GroupOf100ParentInPP(this, "Parent");

    public PP()
    {
    }

    public PP(DN dn)
    {
        super(dn);
    }
}
