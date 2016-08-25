package com.zte.smartlink;

import com.zte.mos.logging_service.Log;

/**
 * Created by olinchy on 16-7-22.
 */
public class MemoryLog implements Log
{
    public MemoryLog(long used, long total)
    {
        this.used = used;
        this.total = total;
    }

    public final long used;
    public final long total;

    @Override
    public String toString()
    {
        return "current memory{" +
                "used=" + used +
                ", total=" + total +
                '}';
    }
}
