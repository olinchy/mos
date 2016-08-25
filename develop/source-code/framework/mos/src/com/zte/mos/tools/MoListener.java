package com.zte.mos.tools;

import com.zte.exp.mos.MosBaseListener;
import com.zte.exp.mos.Mos_expParser;
import com.zte.mos.message.Mo;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created by olinchy on 16-5-24.
 */
public class MoListener extends MosBaseListener
{
    public MoListener(Mo mo)
    {
        this.mo = mo;
    }

    private final Mo mo;

    @Override
    public void exitMoexp(@NotNull Mos_expParser.MoexpContext ctx)
    {
        pushObject(mo.get(ctx.propertyName().getText()));
    }
}
