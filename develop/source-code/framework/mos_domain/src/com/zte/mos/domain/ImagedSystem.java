package com.zte.mos.domain;

import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

import java.util.UUID;

/**
 * Created by luoqingkai on 16-1-7.
 * Hold all the interfaces between MOS
 */
public interface ImagedSystem
{
    UUID lock() throws LockedByTransException;

    void unlock(UUID key);

    boolean isLocked();

    boolean sync(SysSnapSpot sysData, UUID key) throws MOSException;

    void syncDiff(Diff diff) throws MOSException;

    int revision();

    DataState getDataState();

    void setDataState(DataState state);

    ManagementObject getMO(String dn, Maybe<Integer> transId)throws MOSException;

    ModelMETA getModelMETA();
    ImagedSystem switchTo(ModelMETA meta);

    void hangUp();
    void resume();

}
