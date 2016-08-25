package com.zte.mos.tools;

import com.zte.exp.mos.MosBaseListener;
import com.zte.exp.mos.Mos_expParser;
import com.zte.mos.persistent.Record;
import org.antlr.v4.runtime.misc.NotNull;

public class LiteMosListener extends MosBaseListener
{
    private final Record record;

    public LiteMosListener(Record record)
    {
        this.record = record;
    }

    @Override
    public void exitDbexp(@NotNull Mos_expParser.DbexpContext ctx)
    {
        String column = ctx.getText();
        this.pushObject(record.get(column));
    }

}
