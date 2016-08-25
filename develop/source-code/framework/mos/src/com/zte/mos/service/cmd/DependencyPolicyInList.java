package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.util.CloneHelper;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by olinchy on 15-7-15.
 */
public class DependencyPolicyInList extends DependencyPolicy
{
    private HashMap<String, Object> map = new HashMap<String, Object>();
    public DependencyPolicyInList(
            MOS mos, Maybe<Integer> transId, ManagementObject mo)
    {
        super(mos, transId, mo);
    }

    @Override
    public DistinguishedList<String> undo(String name, DN who) throws MOSException
    {
        DistinguishedList<String> lst = new DistinguishedList<String>();
        ManagementObject cloneMo = CloneHelper.clone(mo);
        if (map.get(name) == null || (map.get(name) != null && map.get(name) instanceof ArrayList && ((ArrayList) map.get(name)).size() == 0))
        {
            todoWhenRefIsClear(lst,cloneMo,  name,  map.get(name));
        }
        else
        {
            new SET_MO().handle(
                    mos, cloneMo, new Mo(cloneMo.getClass().getSimpleName()).setDn(cloneMo.dn()).setAttr(
                            name, map.get(name)), transId, lst);
        }

        return lst;
    }

    @Override
    public void handleMo(String name, DN who) throws MOSException
    {
        Object fieldValue = mo.referencedKilled(name, who);
        map.put(name, fieldValue);
    }

    protected void todoWhenRefIsClear(DistinguishedList<String> lst, ManagementObject cloneMo, String name, Object fieldValue) throws MOSException
    {
        new DELETE_MO().handle(mos, mo, transId, lst);
    }
}
