package com.zte.mos.util;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;

import java.util.*;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-13.
 */
public class DaemonScanner
{
    private final Properties prop;
    private LinkedList<String> lst;

    public DaemonScanner(Properties prop, LinkedList<String> lst)
    {
        this.prop = prop;
        this.lst = lst;
    }


    public void scan(Set<Class<Daemon>> set) throws MOSException
    {
        LinkedList<Daemon> lst = new LinkedList<Daemon>();

        for (Class<Daemon> daemonClass : set)
        {
            try
            {
                Daemon daemon = daemonClass.newInstance();
                lst.add(daemon);
            } catch (Exception e)
            {
                logger(DaemonScanner.class).warn("newInstance "
                        + daemonClass.getName() + " failed, continue!", e);
            }
        }

        Collections.sort(lst);
        for (Daemon daemon : lst)
        {
            daemon.start(prop, this.lst == null ? null : this.lst.toArray(new String[this.lst.size()]));
        }
    }
}
