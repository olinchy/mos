package com.zte.mos.modb.stub;

import com.zte.mos.domain.GroupOf;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;

/**
 * Created by root on 15-1-27.
 */
public class GroupOf100ParentInPP extends GroupOf
{
    public GroupOf100ParentInPP(ManagementObject parent, String thisName)
    {
        super(parent, thisName);
    }

    @Override
    public MoMetaClass meta() throws MOSException
    {
        return null;
    }
}
