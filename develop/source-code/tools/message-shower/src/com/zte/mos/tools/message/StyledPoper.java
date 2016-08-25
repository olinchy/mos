package com.zte.mos.tools.message;

import com.zte.mos.type.Pair;

/**
 * Created by olinchy on 2/9/15 for mosjava.
 */
public interface StyledPoper
{
    CustomFrame createFrame(Pair<MsgType, String> peek, Integer pos);
}
