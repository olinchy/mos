package com.zte.mos.type;

/**
 * Created by olinchy on 15-11-12.
 */
public class Detach<T> extends Append
{
    public Detach(String content)
    {
        super(content);
    }

    public Detach()
    {
    }

    @Override
    public void work(Merger o)
    {
        o.del(this.content);
    }

    @Override
    public boolean isAppend()
    {
        return false;
    }
}
