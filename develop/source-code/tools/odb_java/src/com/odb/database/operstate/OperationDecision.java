package com.odb.database.operstate;

/**
 * Created by olinchy on 5/20/14.
 */

import com.odb.database.Key;
import com.odb.database.operation.OperTypes;
import com.odb.exception.OperationMergeFailure;
import com.zte.mos.domain.Group;

import static com.odb.database.operstate.Decision.de;
import static com.odb.database.operstate.Existence.destroy;
import static com.odb.database.operstate.Existence.keep;
import static com.odb.database.operstate.IsSuccess.failure;
import static com.odb.database.operstate.IsSuccess.success;
import static com.odb.database.operstate.OperationStates.*;
import static com.zte.mos.util.basic.Logger.logger;

public class OperationDecision
{
    static Decision[][] decs = new Decision[][]
            {
/*                  add                               set                           remove                          addChild                    rmChild*/
/*StNull*/    { de(StAdd, success, keep),     de(StSet, failure, destroy),  de(StRemove, failure, destroy), de(null, failure, destroy),     de(null, failure, destroy) },
/*StOrigin*/  { de(StAdd, failure, destroy),  de(StSet, success, keep),     de(StRemove, success, keep),    de(null, success, keep),        de(null, success, keep)},
/*StAdd*/     { de(StAdd, failure, keep),     de(StAdd, success, keep),     de(StNull, success, destroy),   de(null, success, keep),        de(null, success, keep) },
/*StSet*/     { de(StSet, failure, keep),     de(StSet, success, keep),     de(StRemove, success, keep),    de(null, success, keep),        de(null, success, keep) },
/*StRemove*/  { de(StReplace, success, keep), de(StRemove, failure, keep),  de(StRemove, failure, keep),    de(StRemove, failure, destroy), de(StRemove, failure, destroy) },
/*StReplace*/ { de(StReplace, failure, keep), de(StReplace, success, keep), de(StRemove, success, keep),    de(null, success, keep),        de(null, success, keep) },
            };

    public static Decision decide(OperationStates state, OperTypes oper)
    {
        return decs[state.ordinal()][oper.ordinal()];
    }

    public static <T extends Group> Decision merge(OperTypes oper, T t, DataState state)
            throws OperationMergeFailure
    {
        OperationStates states = OperationStates.valueOf(state);
        Decision decision = OperationDecision.decide(states, oper);
        if (! decision.isSuccess())
        {
            logger(OperationDecision.class).warn(" merge failed! t.key is " + new Key(t).toString(), null);
            throw new OperationMergeFailure(
                    "merge " + oper.name() + " to " + states.name());
        }

        return decision;
    }

}
