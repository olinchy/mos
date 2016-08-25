package com.zte.container.ext.operate;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.OperationLog;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.service.MOS;

import java.lang.reflect.Field;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 2/24/16.
 */
interface Operate
{
    void doOperation(OperationContext context) throws Exception;
};

public enum Operation
{
    add(
            new Operate()
            {
                @Override
                public void doOperation(OperationContext context) throws Exception
                {
                    MOS mos = context.getMos();
                    Maybe<Integer> transId = context.getTransId();
                    ManagementObject mo = context.getLog().toMo(context.getIndexer());
                    ManagementObject checked = mos.getMO(mo.dn().toString(), transId);
                    if ( null == checked) // check auto created child
                    {
                        mos.createMO(mo, transId);
                    }
                    else
                    {
                        logger(Operation.class).info("change mo creation to set, mo " + mo.dn().toString());
                        setMo(checked, mo);
                        mos.updateMO(checked, null, transId);
                    }
                }

                private void setMo(ManagementObject to, ManagementObject from) throws Exception
                {
                    Field[] fields = from.getClass().getFields();
                    for (Field f : fields)
                    {
                        if(f.isAnnotationPresent(MoAttribute.class) || f.isAnnotationPresent(MoReference.class))
                        {
                            Field toField = to.getClass().getField(f.getName());
                            toField.set(to, f.get(from));
                        }
                    }
                }

            }),
    set(
            new Operate()
            {
                @Override
                public void doOperation(OperationContext context) throws Exception
                {
                    OperationLog log = context.getLog();
                    MOS mos = context.getMos();
                    ManagementObject mo = mos.getMO(log.dn, context.getTransId());
                    setMo(mo, log.toMo(context.getIndexer()));
                    mos.updateMO(mo, null, context.getTransId());
                }

                private void setMo(ManagementObject to, ManagementObject from) throws Exception
                {
                    Field[] fields = from.getClass().getFields();
                    for (Field f : fields)
                    {
                        if(f.isAnnotationPresent(MoAttribute.class) || f.isAnnotationPresent(MoReference.class))
                        {
                            Field toField = to.getClass().getField(f.getName());
                            toField.set(to, f.get(from));
                        }
                    }
                }

            }),
    del(
            new Operate()
            {
                @Override
                public void doOperation(OperationContext context) throws Exception
                {
                    OperationLog log = context.getLog();
                    MOS mos = context.getMos();
                    Maybe<Integer> transId = context.getTransId();
                    ManagementObject mo = mos.getMO(log.dn, transId);
                    if (mo != null)
                    {
                        try
                        {
                            mos.deleteMO(log.dn, transId);
                        }
                        catch(NullMoException e)
                        {
                            logger(Operation.class).warn("fail to delete mo, dn = " + log.dn, e);
                        }
                    }

                }
            }),
    na(
            new Operate()
            {
                @Override
                public void doOperation(OperationContext context) throws Exception
                {

                }
            }),

    replace(
            new Operate()
            {
                @Override
                public void doOperation(OperationContext context) throws Exception
                {
                    OperationLog log = context.getLog();
                    MOS mos = context.getMos();
                    Maybe<Integer> transId = context.getTransId();

                    mos.deleteMO(log.dn, transId);
                    mos.createMO(log.toMo(context.getIndexer()), transId);
                }
            });


    private final Operate oper;

    Operation(Operate oper)
    {
        this.oper = oper;
    }

    private void doOperation(OperationContext context) throws Exception
    {
        this.oper.doOperation(context);
    }

    public static void process(OperationContext context) throws Exception
    {
        valueOf(context.getOp()).doOperation(context);
    }
};
