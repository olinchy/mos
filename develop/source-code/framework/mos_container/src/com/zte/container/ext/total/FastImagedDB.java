package com.zte.container.ext.total;

import com.zte.mos.domain.ManagementObject;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class FastImagedDB
{
    private Queue<ManagementObject> queue = new LinkedBlockingQueue<ManagementObject>();
    private Map<String, ManagementObject> map = new ConcurrentHashMap<String, ManagementObject>();
    private Set<String> dnSet = new ConcurrentHashSet<String>();
    private ManagementObject root;


    public FastImagedDB()
    {

    }

    public FastImagedDB(ManagementObject root)
    {
        this.root = root;
    }


    public void setRoot(ManagementObject root)
    {
        this.root = root;
    }

    public ManagementObject getRoot()
    {
        return this.root;
    }

    public void put(ManagementObject mo)
    {
        queue.add(mo);
        map.put(mo.dn().toString(), mo);
        dnSet.add(mo.dn().toString());
    }

    public Set<String> dns()
    {
        return dnSet;
    }

    public Queue<ManagementObject> moes()
    {
        return  queue;
    }

    public boolean contains(String dn)
    {
        return dnSet.contains(dn);
    }

    public ManagementObject get(String dn)
    {
        return map.get(dn);
    }

}
