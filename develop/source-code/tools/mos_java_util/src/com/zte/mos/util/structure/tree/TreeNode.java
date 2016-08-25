package com.zte.mos.util.structure.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy on 5/5/16.
 */
public class TreeNode<T> implements Serializable
{

    private final T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(TreeNode<T> parent, T data)
    {
        this.parent = parent;
        this.data = data;
    }

    public List<TreeNode<T>> getChildren()
    {
        return children != null ? children : new ArrayList<TreeNode<T>>();
    }

    public void addChild(TreeNode<T> node)
    {
        if (children == null)
        {
            children = new ArrayList<TreeNode<T>>();
        }
        children.add(node);
    }

    public T getData()
    {
        return data;
    }

}
