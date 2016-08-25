package com.zte.mos.domain.model.refsupport;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.DependencyPolicyInList;
import com.zte.mos.service.cmd.SET_MO;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.basic.Logger;

/**
 * Created by olinchy on 15-6-4.
 */
public class LVDependencyPolicy extends DependencyPolicyInList
{
    private static Logger logger = Logger.logger(LVDependencyPolicy.class);

    public LVDependencyPolicy(MOS mos, Maybe<Integer> transId, ManagementObject mo)
    {
        super(mos, transId, mo);
    }

    @Override
    protected void todoWhenRefIsClear(
            DistinguishedList<String> lst, ManagementObject cloneMo, String name, Object fieldValue) throws MOSException
    {
        new SET_MO().handle(
                mos, cloneMo, new Mo(cloneMo.getClass().getSimpleName()).setDn(cloneMo.dn()).setAttr(
                        name, fieldValue), transId, lst);
    }
}
