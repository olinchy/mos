package com.zte.mos.util.tools.joiner;

import java.util.HashMap;

/**
 * Created by olinchy on 16-2-24.
 */
public abstract class BoolChecker
{
    public BoolChecker(At at)
    {
        this.at = at;
    }

    protected final At at;

    public abstract <T> boolean mark(boolean previous, HashMap<String, T> source, HashMap<String, T> target);
}
