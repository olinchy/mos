package com.zte.container.ext.switchmodel;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;
import com.zte.mos.util.structure.tree.MoTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ccy on 5/5/16.
 */
public class MoTreeBuilder
{
    public static MoTree buildMoTree(MOS mos) throws MOSException
    {
        MoTree tree = new MoTree();
        Queue<ManagementObject> queue = new LinkedList<ManagementObject>();
        queue.add(mos.getMO(MOS.ROOT_INTERNAL_DN, new Maybe<Integer>()));

        while (!queue.isEmpty())
        {
            ManagementObject mo = queue.poll();

            tree.addTreeNode(mo.toMoClass());

            if (mo != null)
            {
                addChildren(mos, mo, queue);
            }
        }

        return tree;
    }

    private static void addChildren(MOS mos, ManagementObject parent, Queue<ManagementObject> queue) throws MOSException
    {
        ManagementObject[] children = parent.ls();
        if (children != null)
        {
            for (ManagementObject child : children)
            {
                queue.add(child);
            }
        }
    }
}
