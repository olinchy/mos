package com.zte.mos.util.structure.tree;

import com.zte.mos.util.structure.Key;

import java.io.Serializable;

/**
 * Created by ccy on 5/5/16.
 */
public interface TreeIndexer<T> extends Serializable
{
    Key self(T data);
    Key parent(T data);
    boolean isRoot(T data);
}
