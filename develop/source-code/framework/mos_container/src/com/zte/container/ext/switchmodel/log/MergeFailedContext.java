package com.zte.container.ext.switchmodel.log;

import com.zte.mos.message.Mo;
import com.zte.mos.util.structure.tree.MoTree;

/**
 * Created by ccy on 5/23/16.
 */
public class MergeFailedContext implements Context
{
    public MergeFailedContext(Mo mo, MoTree tree)
    {
        this.mo = mo;
        this.tree = tree;
    }

    private final Mo mo;
    private final MoTree tree;

    public Mo failedMo()
    {
        return mo;
    }

    public MoTree moTree()
    {
        return tree;
    }


}
