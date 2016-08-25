package com.zte.mos.imaged;

import com.zte.container.ext.diff.V11SyncDiffThread;
import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.RawMos;

import java.util.UUID;

public class V11ImagedMos extends ImagedMos
{

    public V11ImagedMos(ModelMETA meta, String dn, String smartlinkName) throws MOSException
    {
        super(meta, dn, smartlinkName);
        initState();
    }

    V11ImagedMos(ModelMETA meta, RawMos mos)
    {
        super(meta, mos);
        initState();
    }

    @Override
    public UUID lock() throws LockedByTransException
    {
        synchronized (locker)
        {
            if (key != null)
            {
                logger.debug(this.getRootExternalDN() + " fail to lock , current key= " + key.toString());
                throw new LockedByTransException("has locked by " + this.key);
            }
            this.adminState = new MosSyncState(this);
            V11SyncDiffThread.removeTaskOf(this.getRootExternalDN());
            key = UUID.randomUUID();
            logger.debug(this.getRootExternalDN() + " start lock key= " + key.toString());
            return key;
        }
    }

    @Override
    public void unlock(UUID key)
    {
        synchronized (locker)
        {
            if (this.key.equals(key))
            {
                this.adminState = new MosIdleState(this);
                logger.debug(this.getRootExternalDN() + " start unlock key= " + key.toString());
                this.key = null;
            }
        }
    }


    @Override
    protected void initState() {
        this.adminState = new MosIdleState(this);
    }
}
