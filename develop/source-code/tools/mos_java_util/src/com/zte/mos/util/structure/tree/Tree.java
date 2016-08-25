package com.zte.mos.util.structure.tree;

import com.zte.mos.util.structure.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ccy on 5/5/16.
 */
public abstract class Tree<T> implements TreeIndexer<T>
{
    private TreeNode<T> root = null;
    private Map<Key, TreeNode<T>> map = new HashMap<Key, TreeNode<T>>();

    public void addTreeNode(T data)
    {
        Key self = self(data);
        TreeNode<T> parent = getParent(data);
        TreeNode<T> node = new TreeNode<T>(parent, data);

        if (isRoot(data))
        {
            this.root = node;
        }
        else
        {
            parent.addChild(node);
        }

        map.put(self, node);

    }

    public T getRoot()
    {
        return this.root.getData();
    }

    public List<T> getChildren(T data)
    {
        List<T> list = new ArrayList<T>();
        TreeNode<T> node = map.get(self(data));

        for(TreeNode<T> child:  node.getChildren())
        {
            list.add(child.getData());
        }
        return list;
    }

    public T get(Key key)
    {
        TreeNode<T> node = map.get(key);
        return node != null ? node.getData() : null;
    }

    private TreeNode<T> getParent(T data)
    {
        return map.get(parent(data));
    }

}
