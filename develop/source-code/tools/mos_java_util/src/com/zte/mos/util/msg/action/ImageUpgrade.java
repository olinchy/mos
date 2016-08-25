package com.zte.mos.util.msg.action;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoAction;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.MoActionMsg;

/**
 * Created by root on 15-1-12.
 */
public class ImageUpgrade extends MoActionMsg
{

    private final String oldVersion;
    private final String newVersion;

    public ImageUpgrade(String dn,
            Maybe<Integer> transId,
            String oldVersion, String newVersion)
    {
        super(dn, transId);
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    @Override
    public String actionName()
    {
        return MoAction.ImageUpgrade.name();
    }

    @Override public Pair<String, Object>[] paras()
    {
        return new Pair[] { new Pair("version", newVersion) };
    }
}
