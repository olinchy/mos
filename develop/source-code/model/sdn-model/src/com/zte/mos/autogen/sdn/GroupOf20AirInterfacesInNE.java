package com.zte.mos.autogen.sdn;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.annotation.*;
import com.zte.mos.domain.*;

import com.zte.mos.autogen.sdn.*;

import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.MOS;
import com.zte.mos.util.*;
import com.zte.mos.exception.MOSException;
import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.tools.JsonUtil.toNode;


@MoSet(count=20)
public class GroupOf20AirInterfacesInNE extends GroupOf
{
    private static final long serialVersionUID = 1L;
    public GroupOf20AirInterfacesInNE(){

    }

    public GroupOf20AirInterfacesInNE(ManagementObject parent, String thisName){
        super(parent, thisName);
    }

    @Override
    public MoMetaClass meta() throws MOSException{
        return mos.getMeta("AirInterface");
    }

    @Override
    public MoMetaClass childMeta(String childName) throws MOSException
    {
        return meta();
    }

    public com.zte.mos.message.Mo afterGet() throws MOSException
    {
        AfterGetter<ManagementObject, com.zte.mos.message.Mo> getter;
        if ((getter = mos.getAfterGetter(GroupOf20AirInterfacesInNE.class)) == null)
        {
            return super.afterGet();
        }
        return getter.get(this, mos);
    }

}
