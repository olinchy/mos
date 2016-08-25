package com.zte.mos.app.switchmodel.output;

import com.zte.mos.message.Mo;
import com.zte.mos.type.Pair;

/**
 * Created by ccy on 6/1/16.
 */
public class CreatedMoFormatter implements Formatter
{
    @Override
    public String format(Object dataSource)
    {
        Pair<Mo, Mo> pair = (Pair<Mo, Mo>) dataSource;
        return pair.first().getDn().toString() + "\r\n";
    }
}
