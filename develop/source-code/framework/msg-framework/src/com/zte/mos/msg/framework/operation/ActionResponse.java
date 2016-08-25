package com.zte.mos.msg.framework.operation;

import java.util.LinkedHashMap;

/**
 * Created by ccy on 7/21/15.
 */
public class ActionResponse extends AbstractResponse
{
    private final LinkedHashMap mo;

    public ActionResponse(int result, LinkedHashMap data)
    {
        super(result);
        this.mo = data;
    }

    public LinkedHashMap getMo() {
        return mo;
    }
}
