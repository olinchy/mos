package com.zte.mos.app.switchmodel.output;

import com.zte.mos.message.Mo;
import com.zte.mos.type.Pair;

/**
 * Created by ccy on 6/1/16.
 */
public enum FormatterDefs
{
    Create(new CreatedMoFormatter()), Update(new UpdatedMoFormatter()), Delete(new DeletedMoFormatter()), NA(new DefaultFormatter());

    private Formatter formatter;

    FormatterDefs(Formatter formatter)
    {
        this.formatter = formatter;
    }

    public String doFormat(Pair<Mo, Mo> pair)
    {
        return this.formatter.format(pair);
    }


    public static String format(String name, Pair<Mo, Mo> pair)
    {
        return valueOf(name).doFormat(pair);
    }



}
