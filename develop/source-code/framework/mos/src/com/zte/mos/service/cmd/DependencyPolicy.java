package com.zte.mos.service.cmd;

import com.zte.mos.annotation.MoReferencePolicyAdapter;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by olinchy on 15-6-4.
 */
public class DependencyPolicy extends MoReferencePolicyAdapter
{

    protected final ManagementObject mo;
    protected final Maybe<Integer> transId;
    protected MOS mos;

    public DependencyPolicy(MOS mos, Maybe<Integer> transId, ManagementObject mo)
    {
        this.mos = mos;
        this.mo = mo;
        this.transId = transId;
    }

    @Override
    public DistinguishedList<String> undo(String name, DN who) throws MOSException
    {
        DistinguishedList<String> lst;
        new DELETE_MO().handle(mos, mo, transId, lst = new DistinguishedList<String>());
        return lst;
    }
}
