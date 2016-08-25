package com.zte.mos.type;

/**
 * Created by olinchy on 15-11-12.
 */
public class Append implements Appendage
{
    public Append(String content)
    {
        this.content = content;
    }

    public Append()
    {
    }

    public String content;

    @Override
    public void work(Merger o)
    {
        o.add(this.content);
    }

    @Override
    public boolean isAppend()
    {
        return true;
    }
}
