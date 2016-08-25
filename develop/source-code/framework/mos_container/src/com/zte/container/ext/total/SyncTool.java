package com.zte.container.ext.total;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zte.container.MoOperateType;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DataState;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.observer.Observer;
import com.zte.mos.msg.framework.operation.ReverseSyn;
import com.zte.mos.msg.framework.operation.ReverseSynResponse;
import com.zte.mos.util.basic.Logger;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

import static com.zte.mos.util.basic.Logger.logger;

class Result
{
    Result(boolean keyChanged, boolean attributeChanged)
    {
        this.keyAttributeChanged = keyChanged;
        this.attributeChanged = attributeChanged;
    }

    private final boolean attributeChanged;
    private boolean keyAttributeChanged;

    public boolean isKeyAttributeChanged()
    {
        return keyAttributeChanged;
    }

    public boolean isAttributeChanged()
    {
        return attributeChanged;
    }
}



public class SyncTool
{
    private static Logger log = logger(SyncTool.class);
    public static void sync(ImagedSystem sys, String targetId) throws Exception
    {
        UUID key = sys.lock();
        boolean result = false;
        Observer observer = null;

        try
        {
            ISouthService sv = CommServiceFactory.getService();
            ReverseSyn cmd = new ReverseSyn(targetId);
            ReverseSynResponse response = sv.reverseSync(cmd, targetId);
            observer = response.getObserver();
            if (!response.isSuccess())
            {
                sys.setDataState(DataState.unsynced);
                throw new SyncFailedException(response.getResult(), response.getThrowable());
            }
            result = sys.sync(response, key);
        }
        finally
        {
            sys.unlock(key);
            if (observer != null)
            {
                log.info("start notify observer, result " + result);
                observer.update(result);
            }
        }
    }

    public static ReverseSynResponse sendReverseRequest(String targetId) throws MsgFrameException {
        ISouthService sv = CommServiceFactory.getService();
        ReverseSyn cmd = new ReverseSyn(targetId);
        ReverseSynResponse response = sv.reverseSync(cmd, targetId);
        return response;
    }

    public static boolean isValidAttribute(Field f){
        boolean isAttribute = f.isAnnotationPresent(MoAttribute.class)
                || f.isAnnotationPresent(MoReference.class);
        boolean isIgnored = f.isAnnotationPresent(JsonIgnore.class);
        if ((!isAttribute) || isIgnored)
        {
            return false;
        }

        return true;
    }

    public static MoOperateType getMoOperateType(BaseManagementObject local, BaseManagementObject remote)
    {
        if (local == null && remote == null)
        {
            return MoOperateType.na;
        }

        if (local == null && remote != null)
        {
            return MoOperateType.add;
        }
        if (local != null && remote == null)
        {
            return MoOperateType.del;
        }

        Result result = compareMoAttributes(local, remote);

        if (result.isKeyAttributeChanged())
        {
            return MoOperateType.replace;
        }
        else if (result.isAttributeChanged())
        {
            return MoOperateType.set;
        }
        else
        {
            return MoOperateType.na;
        }
    }

    private static Result compareMoAttributes(BaseManagementObject local, BaseManagementObject remote)
    {
        boolean keyChanged = false;
        boolean attributeChanged = false;
        Field[] fields = local.getClass().getFields();
        if (local.getClass() == remote.getClass())
        {
            for (Field field : fields)
            {
                if (!isValidAttribute(field))
                {
                    continue;
                }
                if (!isFieldEquals(field, local, remote))
                {
                    attributeChanged = true;
                    break;
                }
            }
            return new Result(keyChanged, attributeChanged);
        }
        else
        {
            return new Result(true, true);
        }
    }


    public static boolean isDeepEquals(BaseManagementObject m1, BaseManagementObject m2)
    {
        if (m1 == m2)
        {
            return true;
        }
        if (m1 == null || m2 == null)
        {
            return false;
        }


        Field[] fields = m1.getClass().getFields();
        for (Field f : fields)
        {
            if (!isValidAttribute(f))
            {
                continue;
            }
            if (!isFieldEquals(f, m1, m2))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean isArrayEquals(Object[] myArray, Object[] hisArray)
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

    private static boolean isObjEquals(Object myObj, Object hisObj)
    {
        if (myObj == null)
        {
            return myObj == hisObj;
        }
        else
        {
            return myObj.equals(hisObj);
        }
    }

    public static boolean isFieldEquals(Field f, BaseManagementObject my, BaseManagementObject his)
    {
        try
        {
            if (f.getType().isArray())
            {
                return isArrayEquals((Object[]) f.get(my), (Object[]) f.get(his));
            }
            else if (Collection.class.isAssignableFrom(f.getType()))
            {
                return isCollectionEquals((Collection)f.get(my), (Collection)f.get(his));
            }
            else if (BaseManagementObject.class.isAssignableFrom(f.getType()))
            {
                return isDeepEquals((BaseManagementObject)f.get(my), (BaseManagementObject)f.get(his));
            }
            else
            {
                return isObjEquals(f.get(my), f.get(his));
            }
        }
        catch (Exception e)
        {
            log.info(my.getClass().getName() + ":" + f.getName() + e.getMessage());
            return false;
        }

    }

    private static boolean isCollectionEquals(Collection l, Collection r)
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



    public static MoOperateType getSwitchMoOperateType(BaseManagementObject local, BaseManagementObject remote)
    {
        if (local == null && remote == null)
        {
            return MoOperateType.na;
        }

        if (local == null && remote != null)
        {
            return MoOperateType.add;
        }
        if (local != null && remote == null)
        {
            return MoOperateType.na;
        }

        Result result = compareMoAttributes(local, remote);

        if (result.isKeyAttributeChanged())
        {
            return MoOperateType.replace;
        }
        else if (result.isAttributeChanged())
        {
            return MoOperateType.set;
        }
        else
        {
            return MoOperateType.na;
        }
    }
}
