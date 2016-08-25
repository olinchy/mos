package com.zte.mos.httpservice;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Mo;

/**
 * Created by olinchy on 15-10-8.
 */
public class SET implements Config
{
    SET(Mo[] mo)
    {
        this.mo = mo;
    }

    private Mo[] mo;

    @Override
    public ConfResultSet doConfig(MosService service) throws MOSException
    {
        return (ConfResultSet) service.set(this.mo);
    }
}
