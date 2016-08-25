package com.zte.mos.util.tools;

import com.zte.mos.util.GreaterThan;

/**
 * Created by olinchy on 7/25/14 for MO_JAVA.
 */
public class StringGreaterThan implements GreaterThan
{
    @Override public boolean isGreater(Object left, Object right)
    {
        return String.valueOf(left).compareTo(String.valueOf(right)) > 0;
    }
}
