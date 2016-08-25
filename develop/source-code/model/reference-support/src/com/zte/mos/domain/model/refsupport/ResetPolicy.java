package com.zte.mos.domain.model.refsupport;

import com.zte.mos.annotation.MoReferencePolicyAdapter;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by olinchy on 15-10-30.
 */
public class ResetPolicy extends MoReferencePolicyAdapter
{
    @Override
    public DistinguishedList<String> undo(String name, DN who) throws MOSException
    {
        return null;
    }
}
