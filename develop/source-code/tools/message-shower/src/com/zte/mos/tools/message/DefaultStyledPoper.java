package com.zte.mos.tools.message;

import com.zte.mos.type.Pair;

/**
 * Created by olinchy on 2/9/15 for mosjava.
 */
public class DefaultStyledPoper implements StyledPoper
{
    @Override public CustomFrame createFrame(Pair<MsgType, String> msg, Integer pos)
    {
        MsgFrame frame = new MsgFrame(msg, pos);
        frame.setAlwaysOnTop(true);
        return new CustomFrame<MsgFrame>(frame);
    }
}
