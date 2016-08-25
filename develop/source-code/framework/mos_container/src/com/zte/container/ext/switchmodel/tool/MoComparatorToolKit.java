package com.zte.container.ext.switchmodel.tool;

import com.zte.mos.message.Mo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ccy on 5/23/16.
 */
public class MoComparatorToolKit
{
    public static boolean isMoEqual(Mo oldMo, Mo newMo)
    {
        Map<String, Object> oldMap = oldMo.getMo();
        Map<String, Object> newMap = newMo.getMo();

        if (oldMap.size() != newMap.size())
        {
            return false;
        }

        Iterator iterator = oldMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object newObj = newMo.get(entry.getKey().toString());
            Object oldObj = entry.getValue();

            if (!isObjectEqual(oldObj, newObj))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean isObjectEqual(Object oldObj, Object newObj)
    {

        if (oldObj == null || newObj == null)
        {
            return newObj == oldObj;
        }

        if (oldObj.getClass() != newObj.getClass())
        {
            return false;
        }

        if (oldObj.getClass().isArray())
        {
            return isArrayEqual((Object[])oldObj, (Object[])newObj);
        }
        else if (Collection.class.isAssignableFrom(oldObj.getClass()))
        {
            return isCollectionEqual((Collection)oldObj, (Collection)newObj);
        }
        else
        {
            return oldObj.equals(newObj);
        }
    }

    private static boolean isCollectionEqual(Collection l, Collection r)
    {
        if (l != null && r != null)
        {
            return l.containsAll(r) && r.containsAll(l);
        }
        else
        {
            return l == r;
        }

    }

    private static boolean isArrayEqual(Object[] myArray, Object[] hisArray)
    {
        if (myArray != null && hisArray != null)
        {
            if (myArray.length == hisArray.length)
            {
                for (int i = 0; i < myArray.length; i++)
                {
                    if (myArray[i] == hisArray[i])
                    {
                        continue;
                    }
                    if (myArray[i] == null)
                    {
                        return false;
                    }
                    if (!myArray[i].equals(hisArray[i]))
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return myArray == hisArray;
        }
    }

}
