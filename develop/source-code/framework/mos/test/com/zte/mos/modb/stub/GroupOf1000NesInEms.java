package com.zte.mos.modb.stub;

import com.zte.mos.domain.GroupOf;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;

/**
 * Created by root on 15-1-27.
 */
public class GroupOf1000NesInEms extends GroupOf
{
    public GroupOf1000NesInEms(ManagementObject parent, String thisName)
    {
        super(parent, thisName);
    }

    @Override
    public MoMetaClass meta() throws MOSException
    {
        return null;
    }
}
