package com.zte.mos.util.tools;

/**
 * Created by olinchy on 6/5/14 for MO_JAVA.
 */
public class IDAllocator
{
    private final int start;
    private final int end;
    private int current = 0;

    public IDAllocator(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public synchronized int allocate()
    {
        if (current >= this.end)
        {
            current = start;
        }
        current++;
        return current;
    }

    public synchronized void setStart(int start)
    {
        if (current < start)
            this.current = start;
    }
}
