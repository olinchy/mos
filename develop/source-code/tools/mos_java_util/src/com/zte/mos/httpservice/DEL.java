package com.zte.mos.httpservice;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ConfResultSet;

/**
 * Created by olinchy on 15-10-8.
 */
public class DEL implements Config
{
    DEL(DN[] dnArray)
    {
        this.dn = dnArray;
    }

    private DN[] dn;

    @Override
    public ConfResultSet doConfig(MosService service) throws MOSException
    {
        return (ConfResultSet) service.del(dn);
    }
}
