package com.odb.database.operstate;

import com.odb.database.Odb;
import com.odb.database.extended_odb.ExtendedODB;
import com.odb.database.extended_odb.PostProcess;
import com.odb.database.extended_odb.TransactionODB;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public interface DataState<T extends Group>
{
    T data();

    void commitTo(Odb<T> odb) throws MOSException;

    boolean isDataDestroyed();

    void logTo(List<Log<T>> logs, ExtendedODB<T> parent) throws MOSException;

    void post(PostProcess postProcess) throws MOSException;
}
