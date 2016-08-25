package com.zte.mos.service.cmd;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.Type;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MosSettings;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NoTargetException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.ReferenceClass;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;
import com.zte.mos.util.msg.DeRefMsg;
import com.zte.mos.util.msg.DestroyRefMsg;
import com.zte.mos.util.msg.RefMsg;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.msg.RefMsg.refMsg;

/**
 * Created by olinchy on 15-6-2.
 */
public class Ref
{
    public static List<String> set(
            final ManagementObject old, ManagementObject afterSet, final Maybe<Integer> transId,
            MOS mos) throws MOSException
    {
        ArrayList<String> affectedDN = new ArrayList<String>();
        try
        {
            affectedDN.addAll(delRef(old, transId, mos));
            affectedDN.addAll(add(afterSet, transId, mos));
        }
        catch (MOSException e)
        {
            throw e;
        }
        return affectedDN;
    }

    public static List<String> add(
            final ManagementObject mo, final Maybe<Integer> transId, final MOS mos) throws MOSException
    {
        logger(Ref.class).test("massive test:" + " enter ref.add ");
        final ArrayList<String> affectedDN = new ArrayList<String>();
        RefType refAdd = new RefAdd()
        {
            @Override
            public void doRef(String refDN, ReferenceClass referenceClass) throws MOSException
            {
                if (check(refDN))
                {
                    refDN = decideRefDN(refDN, mos, transId);
                    try
                    {
                        ref(refDN, mo.toMoClass(), transId);
                        affectedDN.add(refDN);
                    }
                    catch (MOSException e)
                    {
                        throw new ValidateException(
                                "ref " + referenceClass.name + " to " + refDN + " failed!");
                    }
                }
            }
        };

        logger(Ref.class).test("massive test:" + " start to add ref of " + mo.dn().toString());

        doRef(mo, refAdd);

        logger(Ref.class).test("massive test:" + " finished adding ref of " + mo.dn().toString());
        return affectedDN;
    }

    private static String decideRefDN(String refDN, MOS mos, Maybe<Integer> transId) throws MOSException
    {
        if (mos.getMO(refDN, transId) != null)
        {
            refDN = new DN(mos.getRootExternalDN()).append(refDN).toString();
        }
        return refDN;
    }

    private static void doRef(ManagementObject mo, RefType ref) throws MOSException
    {
        Mo moClass = mo.toMoClass();
        // iterate all references
        for (ReferenceClass referenceClass : mo.meta().reference)
        {
            if (isSingle(referenceClass))
            {
                Object refDN = moClass.get(referenceClass.name);
                if (refDN != null)
                    ref.doRef(refDN.toString(), referenceClass);
            }
            else if (isMulti(referenceClass))
            {
                ArrayList refDNs = (ArrayList) moClass.get(referenceClass.name);
                for (Object refDN : refDNs)
                {
                    ref.doRef(refDN.toString(), referenceClass);
                }
            }
        }

        // iterate all fields
        for (Field field : mo.getClass().getFields())
        {
            if (!field.isAnnotationPresent(MoAttribute.class))
            {
                continue;
            }

            MoAttribute moAttribute = field.getAnnotation(MoAttribute.class);
            try
            {
                Object obj = field.get(mo);
                if (moAttribute.type().equals(
                        Type.UserDefine) && obj != null && ManagementObject.class.isAssignableFrom(obj.getClass()))
                {
                    ManagementObject nextMo = (ManagementObject) obj;
                    nextMo.setDn(mo.dn().toString());
                    doRef(nextMo, ref);
                }
            }
            catch (IllegalAccessException e)
            {

            }
        }
    }

    private static void ref(String ref, Mo moClass, Maybe<Integer> transId) throws MOSException
    {
        sendMsg(refMsg(new DN(ref), moClass.getDn(), transId));
    }

    public static List<String> del(ManagementObject mo, MOS mos, Maybe<Integer> transId) throws MOSException
    {
        ArrayList<String> affectedDN = new ArrayList<String>();

        affectedDN.addAll(delRef(mo, transId, mos));

        ReferenceObject obj = ReferenceDBInBerkeley.get(mos).get(mo.dn().toString(), transId);
        handleRefKeyDN(obj, mos);

        if (obj == null)
        {
            return affectedDN;
        }
        for (String refBy : obj.refBy)
        {
            killRefBy(refBy, mo.dn().toString(), transId, mos, affectedDN);
        }
        return affectedDN;
    }

    public static void handleRefKeyDN(ReferenceObject obj, MOS mos)
    {
        if(null == obj || mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN)) return;

        for(String refBy : obj.refBy)
        {
            if(refBy.startsWith("/Ems")) continue;
            obj.refBy.remove(refBy);
            obj.refBy.add(mos.getRootExternalDN() + refBy);
        }
    }

    private static ArrayList<String> delRef(
            final ManagementObject mo, final Maybe<Integer> transId, final MOS mos) throws MOSException
    {
        final ArrayList<String> affectedDN = new ArrayList<String>();

        RefDel refDel = new RefDel()
        {
            @Override
            public void doRef(String refDN, ReferenceClass referenceClass) throws MOSException
            {
                if (check(refDN))
                {
                    refDN = decideRefDN(refDN, mos, transId);
                    try
                    {
                        deRef(refDN, mo.dn().toString(), transId);
                        affectedDN.add(refDN);
                    }
                    catch (MOSException e)
                    {
                        throw new ValidateException(
                                "del reference on " + refDN + " at " + mo.dn().toString() + "." + referenceClass.name + " failed",
                                e);
                    }
                }
            }
        };

        doRef(mo, refDel);

        return affectedDN;
    }

    @SuppressWarnings("unchecked")
    private static void killRefBy(
            String to, String from, Maybe<Integer> transId, MOS mos, ArrayList<String> affectedDN) throws
            MOSException
    {
        Result<Result> res = SmartLinkMsgService.send(
                getInstance(MosSettings.class).getProcessName(),
                new DestroyRefMsg(
                        new DN(to), new DN(
                        mos.getRootExternalDN()).append(from), transId));
        if (res.getMo().size() > 0)
        {
            if (res.getMo().get(0).isSuccess())
            {
                for (Object o : res.getMo().get(0).getMo())
                {
                    affectedDN.add(String.valueOf(o));
                }
            }
            else
            {
                Throwable e = res.getMo().get(0).exception();
                if (e instanceof MOSException)
                {
                    throw (MOSException) e;
                }
                else
                {
                    throw new MOSException(e);
                }
            }
        }
        affectedDN.add(to);
    }

    private static boolean check(String refDN)
    {
        return refDN != null && DN.isDN(refDN);
    }

    private static void deRef(String to, String from, Maybe<Integer> transId) throws MOSException
    {
        sendMsg(new DeRefMsg(new DN(to), new DN(from), transId));
    }

    private static boolean isSingle(ReferenceClass referenceClass)
    {
        return !referenceClass.isMulti;
    }

    private static boolean isMulti(ReferenceClass referenceClass)
    {
        return referenceClass.isMulti;
    }

    private static void sendMsg(RefMsg msg) throws MOSException
    {
        String sender = getInstance(MosSettings.class).getProcessName();

        Result res = SmartLinkMsgService.send(sender, msg);
        Result refResult = (Result) res.getMo().get(0);
        if (!refResult.isSuccess())
        {
            Throwable e = refResult.exception();
            if (e instanceof MOSException)
            {
                if (e instanceof NoTargetException)
                {
                    // sometimes if bundle is not exist, consider de-ref successfully
                    return;
                }
                else
                {
                    throw (MOSException) e;
                }
            }
            throw new MOSException(e);
        }
    }

    public static RefByTypes by(String type)
    {
        return RefByTypes.valueOf(type);
    }
}
