package com.zte.mos.storage.temp;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.extended_odb.CommittedODB;
import com.odb.database.extended_odb.OperatingSet;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.storage.OdbOperation;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.storage.TriggerRegister;

/**
 * Created by luoqingkai on 15-7-3.
 */
public class TempOdb extends OdbOperation {
    //private Indexer<ManagementObject> primaryIndex;
    public TempOdb() {
        Indexer<ManagementObject> primaryIndex = new HashMapDBIndexer();
        OperatingSet<ManagementObject> operatingSet = new OperatingSet<ManagementObject>(primaryIndex);
        this.odb = new CommittedODB<ManagementObject>(new HashMapDB(primaryIndex), operatingSet);
        new TriggerRegister().registerAll(odb);
    }

    public Odb<ManagementObject> getOdb(){
        return this.odb;
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(ManagementObject mo) throws MOSException {
        return null;
    }

    @Override
    public long createMoInTransaction(ManagementObject e) throws MOSException {
        return 0;
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(DN key) throws MOSException {
        return null;
    }

    @Override
    public long updateMoInTransaction(ManagementObject e) throws MOSException {
        return 0;
    }

    @Override
    public void commit(long operTransId) throws MOSException {

    }

    @Override
    public void rollback(long operTransId) throws MOSException {

    }


    @Override
    public ManagementObject get(DN dn) throws MOSException {
        return odb.get(new Key(dn));
    }

}
