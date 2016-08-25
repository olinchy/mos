package com.zte.mos.util.structure.tree;

import com.zte.mos.message.Mo;
import com.zte.mos.util.structure.Key;

/**
 * Created by ccy on 5/5/16.
 */
public class MoTree extends Tree<Mo>
{
    @Override
    public Key self(Mo data)
    {
        return new Key(data.getDn());
    }

    @Override
    public Key parent(Mo data)
    {
        return new Key(data.getDn().parent());
    }

    @Override
    public boolean isRoot(Mo data)
    {
        return data.getDn().toString().equalsIgnoreCase("/");
    }
}
