package com.zte.concept;

import static com.zte.concept.DomainState.*;

/**
 * Created by luoqingkai on 15-8-5.
 */
public class NeDecTable {
    private static DomainState[][] table = {
                   /*rollback, create, update, delete, commit*/
    /*empty*/     {Empty,     Created,    null,    Empty,   Empty},
    /*created*/   {Empty,     null,       Created, Empty,   Committed},
    /*updated*/   {Committed, null, Updated, Deleted, Committed},
    /*deleted*/   {Committed, Replaced,   null,    Deleted, Empty},
    /*commited*/  {Committed, null,       Updated, Deleted, Committed},
    /*replaced*/  {Committed, null,       Replaced,Deleted, Committed}
    };

    public static DomainState getNextState(DomainState currentState, DomainOperation oper){
        return table[currentState.value][oper.value];
    }
}
