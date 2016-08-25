package com.zte.mos.modb.stub;

import com.zte.mos.domain.GroupOf;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;

/**
 * Created by root on 15-1-27.
 */
public class GroupOf10ShelfsInRoot extends GroupOf
{
    public GroupOf10ShelfsInRoot(ManagementObject parent, String thisName)
    {
        super(parent, thisName);
    }

    @Override
    public MoMetaClass meta() throws MOSException
    {
        return null;
    }
}
