package com.zte.mos.storage;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 14-12-4.
 * this is a transaction without commit listener, used by sync between MOSes
 */
public class MosSyncTransaction extends MosTransaction
{
    public MosSyncTransaction(int transID, Odb<ManagementObject> odb, Revision revision, MOS mos)
    {
        super(transID, odb, revision, mos);
    }

    @Override
    public List<Log<ManagementObject>> commit() throws MOSException
    {
        List<Log<ManagementObject>> lst = odb.commit();
        revision.reset();
        return lst;
    }

//        protected MosTransaction newTransaction() throws MOSException {
//            Odb<ManagementObject> odbTransaction = odb.startTransaction();
//            ((ExtendedODB<ManagementObject>)odbTransaction).setCommitListener(null);
//            return new MosSyncTransaction(transactionID, odbTransaction, revision, trigger);
//        }
}
