package com.zte.mos.service.cmd;

import com.zte.mos.annotation.MoReference;
import com.zte.mos.annotation.MoReferencePolicy;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ReferenceClass;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.DestroyRefMsg;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static com.zte.mos.inf.MoCmds.MoDestroyRef;

/**
 * Created by olinchy on 15-6-4.
 */
public class REF_KILLED_OF_THIS_MO implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoDestroyRef;
    }

    @Override
    public Result<?> execute(MoMsg msg, MOS mos) throws MOSException
    {
        DestroyRefMsg destroyRefMsg = (DestroyRefMsg) msg;
        ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());
        if (mo != null)
        {
            return new Successful<String>(killRef(destroyRefMsg, mo, mos));
        }
        else
        {
            return new Successful();
        }
    }

    private ArrayList<String> killRef(DestroyRefMsg destroyRefMsg, ManagementObject mo, MOS mos) throws MOSException
    {
        ArrayList<String> affectDN = new ArrayList<String>();
        HashMap<ReferenceClass, MoReferencePolicy> needReDoMap = new HashMap<ReferenceClass, MoReferencePolicy>();
        try
        {
            for (ReferenceClass ref : mo.meta().reference)
            {
                Field field = mo.getClass().getField(ref.name);
                MoReference moReference = field.getAnnotation(MoReference.class);
                boolean needToReDo;
                if (moReference.isMulti())
                {
                    needToReDo = ((ArrayList) field.get(mo)).contains(destroyRefMsg.getFrom().toString());
                }
                else
                {
                    needToReDo = field.get(mo).equals(destroyRefMsg.getFrom().toString());
                }
                if (needToReDo)
                {
                    Class<? extends MoReferencePolicy> clazz = moReference.under();
                    Constructor<? extends MoReferencePolicy> constructor = clazz.getConstructor(
                            MOS.class, Maybe.class, ManagementObject.class);
                    MoReferencePolicy policy = constructor.newInstance(mos, destroyRefMsg.getTransactionID(), mo);
                    policy.handleMo(field.getName(), destroyRefMsg.getFrom());
                    needReDoMap.put(ref, policy);
                }
            }
            for (ReferenceClass ref : needReDoMap.keySet())
            {
                MoReferencePolicy policy = needReDoMap.get(ref);
                Field field = mo.getClass().getField(ref.name);
                affectDN.addAll(policy.undo(field.getName(), destroyRefMsg.getFrom()));
            }
        }
        catch (MOSException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new MOSException(e);
        }
        return affectDN;
    }
}
