package com.zte.container.ext.switchmodel;

import com.zte.mos.service.ReferenceObject;
import com.zte.mos.util.Visitor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 7/5/16.
 */
public class ReferenceObjectsRepository implements Serializable
{
    private Map<String, ReferenceObject> map = new HashMap<String, ReferenceObject>();

    public void put(ReferenceObject obj)
    {
        map.put(obj.dn, obj);
    }

    public void walk(Visitor<ReferenceObject> visitor)
    {
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            try
            {
                visitor.visit((ReferenceObject)entry.getValue());
            }
            catch (Throwable e)
            {
                logger(this.getClass()).error(" fail to walk reference object repository", e);
            }
        }
    }

}
