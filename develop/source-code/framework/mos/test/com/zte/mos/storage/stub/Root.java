package com.zte.mos.storage.stub;

import com.zte.mos.annotation.Mo;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.modb.stub.GroupOf10ShelfsInRoot;

@Mo(tid = 1)
public class Root extends BaseManagementObject
{
    @MoChild
    @MoSet(count = 10)
    public GroupOf10ShelfsInRoot shelf = new GroupOf10ShelfsInRoot(this, "Shelf");
}
