package com.zte.mos.storage.stub;

import com.zte.mos.annotation.Mo;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.BaseManagementObject;

@Mo(tid = 142)

public class Shelf extends BaseManagementObject
{
    @MoAttribute(field = "UCSHELFNO", minimum = 1, maximum = 2)
    public Integer shelf = 1;
    @MoAttribute(field = "", minimum = 1, maximum = 2)
    public Integer refShelf = 1;
    @MoChild
    @MoSet(count = 8)

    public GroupOf8BoardsInShelf board = new GroupOf8BoardsInShelf(this, "Board");
}
