package com.zte.mos.msg.framework;

import com.zte.mos.domain.*;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

import java.util.UUID;

/**
 * Created by love on 16-3-22.
 */
public class UTImagedSystem implements ImagedSystem {

    private ModelMETA meta;

    public UTImagedSystem(ModelMETA meta) {
        this.meta = meta;
    }

    @Override
    public UUID lock() throws LockedByTransException
    {
        return UUID.randomUUID();
    }

    @Override
    public void unlock(UUID key) {

    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean sync(SysSnapSpot sysData, UUID key) throws MOSException {
        return false;
    }

    @Override
    public void syncDiff(Diff diff) throws MOSException {

    }

    @Override
    public int revision() {
        return 0;
    }

    @Override
    public DataState getDataState() {
        return null;
    }

    @Override
    public void setDataState(DataState state)
    {

    }

    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transId) throws MOSException {
        return null;
    }

    @Override
    public ModelMETA getModelMETA() {
        return this.meta;
    }

    @Override
    public ImagedSystem switchTo(ModelMETA meta) {
        return null;
    }

    @Override
    public void hangUp()
    {

    }

    @Override
    public void resume()
    {

    }

}
