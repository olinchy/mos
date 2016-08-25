package com.zte.mos.msg.impl.restful.session;

import com.zte.mos.domain.*;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

import java.util.UUID;

/**
 * Created by dongyue on 16-7-13.
 */
public class MockImagedSystem implements ImagedSystem {
    @Override
    public UUID lock() throws LockedByTransException {
        return null;
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
    public void setDataState(DataState state) {

    }

    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transId) throws MOSException {
        return null;
    }

    @Override
    public ModelMETA getModelMETA() {
        return new ModelMETA(null, null);
    }

    @Override
    public ImagedSystem switchTo(ModelMETA meta) {
        return null;
    }

    @Override
    public void hangUp() {

    }

    @Override
    public void resume() {

    }
}
