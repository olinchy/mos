package com.mos.lite.http_service;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.persistent.Log;

import java.util.LinkedList;

/**
 * Created by olinchy on 15-12-28.
 */
public class CommitLog
{
    public CommitLog(int id, LinkedList<Log<ManagementObject>> logs)
    {
        this.id = id;
        this.logs = logs;
    }

    public CommitLog(int revision)
    {
        this.id = revision;
    }

    public int id;
    public LinkedList<Log<ManagementObject>> logs;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommitLog commitLog = (CommitLog) o;

        return id == commitLog.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }
}
