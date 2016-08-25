package com.zte.mos.app.alarm.stub;

import com.zte.mos.app.necache.Cache;
import com.zte.mos.app.necache.CacheService;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Visitor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ccy on 8/13/15.
 */
public class CacheServiceStub implements CacheService
{

    private Map<String, Map<String, Object>> db = new ConcurrentHashMap<String, Map<String, Object>>();

    private CacheServiceStub()
    {

    }





    @Override
    public void insert(DN key, Map<String, Object> data) throws MOSException
    {
        db.put(key.toString(), data);
    }

    @Override
    public void delete(DN dn) throws BerkeleyDBException {

    }

    @Override
    public void delete(DN key, Map<String, Object> data) throws BerkeleyDBException {

    }

    @Override
    public Cache get(DN dn) throws BerkeleyDBException
    {
        if (db.containsKey(dn.toString()))
        {
            return new Cache(dn.toString(), db.get(dn.toString()));
        }
        return null;
    }

    @Override
    public Cache get(String ip) throws MOSException
    {
        Iterator iter = db.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getKey();
            Map<String, Object> values = (Map<String, Object>)entry.getValue();

            if (values.containsKey("ip") && String.valueOf(values.get("ip")).equals(ip))
            {
                return new Cache(key.toString(), values);
            }
        }
        return null;
    }

    @Override
    public void modify(DN key, Map<String, Object> data) {

    }

    @Override
    public void clearAll()
    {

    }

    @Override
    public void walk(Visitor<Cache> visitor)
    {
        Iterator iter = db.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getKey();
            Map<String, Object> values = (Map<String, Object>)entry.getValue();

            try
            {
                visitor.visit(new Cache(key.toString(), values));
            }
            catch (MOSException e)
            {
                e.printStackTrace();
            }
        }
    }


}
