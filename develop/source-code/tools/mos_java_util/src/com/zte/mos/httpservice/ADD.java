package com.zte.mos.httpservice;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Mo;

/**
 * Created by olinchy on 15-10-8.
 */
public class ADD implements Config
{
    ADD(Mo[] moArray)
    {
        this.mo = moArray;
    }

    private final Mo[] mo;

    @Override
    public ConfResultSet doConfig(MosService service) throws MOSException
    {
        return (ConfResultSet) service.add(mo);
    }
}
