package com.zte.scope.ems;

import com.zte.concept.IMoOperation;
import com.zte.concept.IOperationPool;
import com.zte.concept.ModelTool;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.inf.MoCmds;
import com.zte.scope.common.DefaultOperation;
import com.zte.scope.ems.oper.*;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-7-16.
 */
public class EmsOperationPool implements IOperationPool
{
    public static final EmsOperationPool pool = new EmsOperationPool();
    private final DefaultOperation defautOper = new DefaultOperation();
    private HashMap<String, IMoOperation> operMap = new HashMap<String, IMoOperation>();

    private EmsOperationPool()
    {
        IMoOperation ne_ls = new NE_LS();
        operMap.put(ne_ls.mib(), ne_ls);

        IMoOperation ne_creation = new NE_CREATION();
        operMap.put(ne_creation.mib(), ne_creation);

        IMoOperation ne_get_meta = new NE_GET_META();
        operMap.put(ne_get_meta.mib(), ne_get_meta);

        IMoOperation ne_find = new NE_FIND();
        operMap.put(ne_find.mib(), ne_find);

        IMoOperation ne_get = new NE_GET();
        operMap.put(ne_get.mib(), ne_get);

        IMoOperation reg = new EMSRouterRegister();
        operMap.put(reg.mib(), reg);

        IMoOperation pickBundle = new EMSBundlePicker();
        operMap.put(pickBundle.mib(), pickBundle);
    }

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
