package com.odb.database.operstate;

import com.odb.database.extended_odb.PostProcess;
import com.odb.database.extended_odb.TransactionODB;
import com.odb.database.operation.OperTypes;
import com.odb.exception.OperationMergeFailure;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 5/20/14.
 */
public interface OperationState<T extends Group> extends DataState<T>, DesicionState<T>
{
    OperationState<T> merge(OperTypes oper, T t) throws OperationMergeFailure;

    DataState getState();

    void post(PostProcess postProcess) throws MOSException;
}
