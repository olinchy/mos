package com.zte.scope.bundle;

import com.odb.database.SimpleDB;
import com.zte.concept.DomainOperation;
import com.zte.concept.DomainState;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.NoSuchTransException;
import com.zte.scope.ems.EmsRoutingTable;

import java.util.HashMap;

import static com.zte.concept.DomainState.*;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-8-7.
 */
public class NeTransaction
{

    private SimpleDB<String, NeToBundleBind> neDb;

    private HashMap<Integer, HashMap<String, StateNe>> stateNeMap
            = new HashMap<Integer, HashMap<String, StateNe>>();
    private BundleDomain bundleDomain;


    public NeTransaction(SimpleDB<String, NeToBundleBind> neDb, BundleDomain bundle) {
        this.neDb = neDb;
        this.bundleDomain = bundle;
    }

    public void create(NeToBundleBind bind, int transId) throws MOSException {
        HashMap<String, StateNe> stateNes = stateNeMap.get(transId);
        if (stateNes == null) {
            stateNes = new HashMap<String, StateNe>();
            stateNeMap.put(transId, stateNes);
        }
        if (stateNes.containsKey(bind.neDN))
        {
            logger(NeTransaction.class).error("fail to create ne in bundle " + bind.neDN);
            throw new MOSException("id conflicts:" + bind.neDN);
        }
        stateNes.put(bind.neDN, new StateNe(bind));
        logger(NeTransaction.class).debug("start to add to ems routing table " + bind.neDN + " transId " + transId);
        EmsRoutingTable.service.addRoute(bind.neDN, bundleDomain);
        bundleDomain.increaseNeNum();
    }

    public void update(NeToBundleBind bind, int transId) throws MOSException {
        HashMap<String, StateNe> nes = stateNeMap.get(transId);
        if (nes != null) {
            StateNe ne = nes.get(bind.neDN);
            if (ne != null) {
                ne.bind = bind;
                ne.onOperation(DomainOperation.update);
            } else {
                NeToBundleBind committedBind = neDb.get(bind.neDN);
                if (committedBind == null) {
                    throw new MOSException("invalid transId:" + transId);
                } else {
                    nes.put(bind.neDN, new StateNe(bind, DomainState.Updated));
                }
            }
        }else{
            nes = new HashMap<String, StateNe>();
            stateNeMap.put(transId, nes);
            NeToBundleBind committedBind = neDb.get(bind.neDN);
            if (committedBind == null){
                throw new MOSException("invalid transId:" + transId);
            }else{
                nes.put(bind.neDN, new StateNe(bind, DomainState.Updated));
            }
        }

    }

    public void delete(String neId, int transId) throws BerkeleyDBException {
        HashMap<String, StateNe> nes = stateNeMap.get(transId);
        if (nes != null) {
            StateNe ne = nes.get(neId);
            if (ne != null) {
                ne.onOperation(DomainOperation.delete);
            } else {
                NeToBundleBind bind = neDb.get(neId);
                ne = new StateNe(bind, DomainState.Deleted);
                nes.put(bind.neDN, ne);
            }

        }else{
            nes = new HashMap<String, StateNe>();
            stateNeMap.put(transId, nes);
            NeToBundleBind bind = neDb.get(neId);
            StateNe ne = new StateNe(bind, DomainState.Deleted);
            nes.put(bind.neDN, ne);
        }
    }

    public void commit(int transId) throws MOSException {
        HashMap<String, StateNe> nes = stateNeMap.remove(transId);
        if (nes != null) {
            for (StateNe ne : nes.values())
            {
                ne.commit(neDb);
                if (ne.state == Empty)
                {
                    logger(NeTransaction.class).debug("start to remove ems routing table " + ne.bind.neDN + " when commit transId " + transId + " state " + ne.state.name());
                    EmsRoutingTable.service.delRoute(ne.getID());
                }
                else if (ne.state == Deleted)
                {
                    bundleDomain.decreaseNeNum();
                    logger(NeTransaction.class).debug("start to remove ems routing table " + ne.bind.neDN + " when commit transId " + transId + " state " + ne.state.name());
                    EmsRoutingTable.service.delRoute(ne.getID());
                }
            }
        }
        else
        {
            throw new NoSuchTransException("no such transaction:" + transId);
        }
    }

    public void rollback(int transId) throws MOSException {
        HashMap<String, StateNe> nes = stateNeMap.remove(transId);
        if (nes != null) {
            for (StateNe ne : nes.values()) {
                ne.rollback();
                if (ne.state == Created)
                {
                    logger(NeTransaction.class).debug("start to remove ems routing table " + ne.bind.neDN + " when rollback transId " + transId );
                    EmsRoutingTable.service.delRoute(ne.getID());
                    bundleDomain.decreaseNeNum();
                }
            }
        }else{
            throw new MOSException("no such transaction:" + transId);
        }
    }
}
