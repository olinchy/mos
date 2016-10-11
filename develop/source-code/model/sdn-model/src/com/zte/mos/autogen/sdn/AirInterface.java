package com.zte.mos.autogen.sdn;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.odb.database.Odb;
import com.odb.database.trigger.TriggerTiming;
import com.zte.mos.annotation.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.*;
import com.zte.mos.service.cmd.*;
import com.zte.mos.domain.*;
import com.zte.mos.type.*;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.*;
import com.zte.mos.domain.model.refsupport.*;
import java.util.*;

import com.zte.mos.autogen.sdn.*;

import static com.zte.mos.msg.MsgMode.*;
import static com.zte.mos.msg.SupportedProtocol.*;
import static com.zte.mos.util.Singleton.getInstance;




@Mo(tid=2)
public class AirInterface extends BaseManagementObject
{

    private static final long serialVersionUID = 1L;

    public AirInterface(DN dn)
    {
        super(dn);
        txFrequency=1480000l;
        rxFrequency=1480000l;
    
    
    }
    public AirInterface()
    {
        super();
        txFrequency=1480000l;
        rxFrequency=1480000l;
    
    
    }
   
    @MoAttribute(field ="f_txFrequency", type = Type.WORD32)

    public  Long txFrequency;
    
    @MoAttribute(field ="f_rxFrequency", type = Type.WORD32)

    public  Long rxFrequency;
    
   
   
    

    public com.zte.mos.message.Mo afterGet() throws MOSException
    {
        AfterGetter<ManagementObject, com.zte.mos.message.Mo> getter;
        if ((getter = mos.getAfterGetter(AirInterface.class)) == null)
        {
            return super.afterGet();
        }
        return getter.get(this, mos);
    }
}
