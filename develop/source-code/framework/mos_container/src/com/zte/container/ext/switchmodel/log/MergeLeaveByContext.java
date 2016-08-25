package com.zte.container.ext.switchmodel.log;

import com.zte.mos.message.Mo;
import com.zte.mos.util.structure.tree.MoTree;

import java.util.Queue;

/**
 * Created by ccy on 5/23/16.
 */
public class MergeLeaveByContext implements Context
{
    public MergeLeaveByContext(Queue<Mo> queue, MoTree tree)
    {
        this.queue = queue;
        this.tree = tree;
    }

    private final Queue<Mo> queue;
    private final MoTree tree;

    public MoTree moTree()
    {
        return this.tree;
    }

    public Queue<Mo> leaveByQueue()
    {
        return this.queue;
    }
}
