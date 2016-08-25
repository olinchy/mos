package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.common.resource.ppu.entity.LinkData;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ccy on 9/16/15.
 */
public class LinkDataSimulatorDb
{
    Queue<LinkDataStub> queue = new ConcurrentLinkedQueue<LinkDataStub>();

    private LinkDataSimulatorDb()
    {

    }

    public void add(LinkDataStub linkData)
    {
        queue.add(linkData);
    }

    public void clear()
    {
        queue.clear();
    }

    public LinkData[] getAll()
    {
       return queue.toArray(new LinkDataStub[queue.size()]);
    }
}
