package com.zte.mos.annotation;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by ruitao on 16-5-16.
 */
public class MoReferencePolicyAdapter implements MoReferencePolicy
{
    @Override
    public DistinguishedList<String> undo(String name, DN who) throws MOSException
    {
        return null;
    }

    @Override
    public void handleMo(String name, DN who) throws MOSException
    {

    }
}
