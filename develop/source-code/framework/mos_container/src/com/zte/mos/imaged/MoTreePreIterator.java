package com.zte.mos.imaged;

import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;
import com.zte.mos.util.basic.Logger;

import java.util.HashMap;
import java.util.HashSet;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-11.
 */
public class MoTreePreIterator
{
    private static Logger log = logger(MoTreePreIterator.class);
    private HashMap<BaseManagementObject, BaseManagementObject> brotherNextMap = new HashMap<BaseManagementObject, BaseManagementObject>();

    private RawMos repo;

    private HashSet<BaseManagementObject> visitedSet = new HashSet<BaseManagementObject>();

    public MoTreePreIterator(RawMos repo)
    {
        this.repo = repo;
    }

    public BaseManagementObject getRoot() throws MOSException
    {
        return (BaseManagementObject) repo.getMO(MOS.ROOT_INTERNAL_DN, MOS.SYNC_TRANS_ID);
    }

    private boolean isVisited(BaseManagementObject mo)
    {
        return visitedSet.contains(mo);
    }

    public BaseManagementObject next(BaseManagementObject mo) throws MOSException
    {
        if (!isVisited(mo))
        {
            visitedSet.add(mo);
            ManagementObject[] children = repo.getMO(mo.dn().toString(), MOS.SYNC_TRANS_ID)
                    .ls();//mo.ls();
            if (children != null && children.length > 0)
            {
                setParent(children, mo);
                setBrother(children);
                //log.info("find:" + ((BaseManagementObject) children[0]).dn);
                return (BaseManagementObject) children[0];

            }
            else
            {
                return findVisitedMoNext(mo);
            }
        }
        else
        {
            return findVisitedMoNext(mo);
        }
    }

    private BaseManagementObject findVisitedMoNext(BaseManagementObject mo) throws MOSException
    {
        BaseManagementObject next = null;
        do
        {
            BaseManagementObject parentBrother = brotherNextMap.get(mo);
            if (parentBrother != null)
            {
                visitedSet.remove(mo);
                next = parentBrother;
                break;
            }
            else
            {
                visitedSet.remove(mo);
                mo = (BaseManagementObject) mo.getParent();
                if (mo == null)
                {
                    break;
                }
            }
        } while (isVisited(mo));
        if (next != null)
        {
            //log.info(next.dn);
        }

        return next;
    }

    private void setParent(ManagementObject[] children, BaseManagementObject parent)
    {
        for (ManagementObject child : children)
        {
            child.setParent(parent);
        }
    }

    private void setBrother(ManagementObject[] children)
    {
        for (int i = 0; i < children.length; i++)
        {
            if (i + 1 < children.length)
            {
                brotherNextMap.put((BaseManagementObject) children[i],
                        (BaseManagementObject) children[i + 1]);
            }
        }
    }
}
