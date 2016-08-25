package com.zte.scope.bundle;

import com.zte.concept.IMoOperation;
import com.zte.concept.IOperationPool;
import com.zte.concept.ModelTool;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.inf.MoCmds;
import com.zte.scope.bundle.oper.*;
import com.zte.scope.common.DefaultOperation;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-7-17.
 */
public class BundleOperationPool implements IOperationPool
{
    public static final BundleOperationPool pool = new BundleOperationPool();
    private HashMap<String, IMoOperation> operMap = new HashMap<String, IMoOperation>();
    private final DefaultOperation defautOper = new DefaultOperation();

    public BundleOperationPool()
    {
        IMoOperation ne_creation = new NE_CREATION();
        operMap.put(ne_creation.mib(), ne_creation);
        IMoOperation ne_del = new NE_DEL();
        operMap.put(ne_del.mib(), ne_del);

        IMoOperation ne_set = new NE_SET();
        operMap.put(ne_set.mib(), ne_set);

//        IMoOperation product_set = new PRODUCT_SET();
//        operMap.put(product_set.mib(), product_set);

        IMoOperation product_ls = new PRODUCT_LS();
        operMap.put(product_ls.mib(), product_ls);

        IMoOperation product_action = new PRODUCT_ACTION();
        operMap.put(product_action.mib(), product_action);
    }

    @Override
    public IMoOperation getOperation(DN dn, MoCmds cmd)
    {
        String key = ModelTool.buildKey(new DNWrapper(dn.toString()).evenPos(), cmd);
        IMoOperation oper = operMap.get(key);
        if (oper == null)
        {
            oper = defautOper;
        }
        return oper;
    }

    public boolean isRegistered(DN dn, MoCmds cmd)
    {
        String key = ModelTool.buildKey(new DNWrapper(dn.toString()).evenPos(), cmd);
        return operMap.containsKey(key);
    }
}
