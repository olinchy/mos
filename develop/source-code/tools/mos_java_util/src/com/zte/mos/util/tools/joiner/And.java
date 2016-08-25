package com.zte.mos.util.tools.joiner;

import java.util.HashMap;

/**
 * Created by olinchy on 16-2-24.
 */
public class And extends BoolChecker
{
    public And(At at)
    {
        super(at);
    }

    @Override
    public <T> boolean mark(boolean previous, HashMap<String, T> source, HashMap<String, T> target)
    {
        return previous && at.match(source, target);
    }
}
