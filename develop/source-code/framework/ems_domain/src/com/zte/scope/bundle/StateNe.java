package com.zte.scope.bundle;

import com.odb.database.SimpleDB;
import com.zte.concept.DomainOperation;
import com.zte.concept.DomainState;
import com.zte.concept.NeDecTable;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.scope.ems.EmsRoutingTable;

import static com.zte.concept.DomainState.*;

/**
 * Created by luoqingkai on 15-8-7.
 */
public class StateNe {
    DomainState state = Created;
    NeToBundleBind bind ;

    public StateNe(NeToBundleBind bind) {
        this.bind = bind;
    }
    public StateNe(NeToBundleBind bind, DomainState initState) {
        this.bind = bind;
        this.state = initState;
    }

    public String getID(){
        return bind.neDN;
    }

    public DomainState onOperation(DomainOperation operation){
        return NeDecTable.getNextState(state, operation);
    }

    public void commit(SimpleDB<String, NeToBundleBind> neDb) throws MOSException {
        if(state == Deleted){
            neDb.delete(bind.neDN);
        }else if(state == Created || state == Updated || state == Replaced){
            neDb.put(bind);
        }
    }

    public void rollback(){

    }
}
