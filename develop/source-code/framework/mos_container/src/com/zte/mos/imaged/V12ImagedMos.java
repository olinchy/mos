package com.zte.mos.imaged;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.RawMos;

import java.util.UUID;

/**
 * Created by luoqingkai on 2/23/16.
 */
public class V12ImagedMos extends ImagedMos
{
    public V12ImagedMos(ModelMETA meta, String dn, String smartlinkName) throws MOSException
    {
        super(meta, dn, smartlinkName);
        initState();
    }

    protected V12ImagedMos(ModelMETA meta, RawMos mos)
    {
        super(meta, mos);
        initState();
    }

    @Override
    public void unlock(UUID key)
    {
        synchronized (locker)
        {
            if (this.key.equals(key))
            {
                this.adminState = new V12MosIdleState(this);
                logger.debug(this.getRootExternalDN() + " start unlock key= " + key.toString());
                this.key = null;
            }
        }
    }


    @Override
    protected void initState() {
        this.adminState = new V12MosIdleState(this);
    }
}
