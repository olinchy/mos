package com.zte.mos.service.cmd;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.type.Appendage;
import com.zte.mos.type.Merger;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.mos.util.scaner.ConverterTempStorage;
import com.zte.mos.util.tools.JsonUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import static com.zte.mos.service.cmd.FormatReference.formatRefDNInLocalContext;
import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.toNode;
import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by root on 14-12-4.
 */
public class SET_MO extends WriteCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoSetRequest;
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        MoConfigMsg configMsg = (MoConfigMsg) msg;
        Mo userUpdated = getUpdatedAttributes(configMsg);
        ManagementObject mo = getMo(configMsg, userUpdated, mos);
        mos.updateMO(mo, userUpdated, msg.getTransactionID());
        affectedDN.addAll(mos.getAffectedDN(msg.getTransactionID()));
    }

    private Mo getUpdatedAttributes(MoConfigMsg msg)
    {
        return msg.getMo();
    }

    protected ManagementObject getMo(MoConfigMsg msg, Mo userUpdated, MOS mos) throws MOSException
    {
        ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());
        if (mo == null)
        {
            throw new NullMoException("mo " + msg.dn() + " is not exist!");
        }

        MOValidator.validate(mo.getClass(), userUpdated, true);

        for (Entry<String, Object> settingFields : userUpdated.getMo().entrySet())
        {
            String fieldName = settingFields.getKey();
            Object fieldValue = settingFields.getValue();
            try
            {
                Field field = mo.getClass().getField(fieldName);
                if (Merger.class.isAssignableFrom(field.getType()))
                {
                    merger(userUpdated, mo, fieldName, field);
                }
                else
                {
                    convertToField(mo, fieldValue, field);
                }
            }
            catch (NoSuchFieldException e)
            {
                logger(this.getClass()).debug(mo.dn() + "has no such field:" + fieldName);
                continue;
            }
            catch (Exception e)
            {
                //throw new MOSException(e);
                logger(this.getClass()).warn(e.getMessage(), e);
            }
        }

        // FIXME: 16-2-25 set referenced dn in local context /Ems/1/Ne/1/Product/1/Shelf/1 ==> /Shelf/1
        formatRefDNInLocalContext(mos, mo, mo.toMoClass());
        return mo;
    }

    private void merger(
            Mo userUpdated, ManagementObject mo, String fieldName,
            Field field) throws IllegalAccessException, MOSException
    {
        try
        {
            Object obj = userUpdated.get(fieldName);
            Appendage appendage = toObject(JsonUtil.toString(obj), Appendage.class);
            if (appendage != null)
                appendage.work((Merger) field.get(mo));
            else
            {
                wrapWithCatch(userUpdated, mo, fieldName, field);
            }
        }
        catch (Throwable e)
        {
            wrapWithCatch(userUpdated, mo, fieldName, field);
        }
    }

    private void wrapWithCatch(
            Mo userUpdated, ManagementObject mo, String fieldName,
            Field field) throws IllegalAccessException, MOSException
    {
        try
        {
            convertToField(mo, userUpdated.get(fieldName), field);
        }
        catch (NoSuchFieldException e1)
        {
            logger(this.getClass()).warn("", e1);
        }
    }

    private void convertToField(
            ManagementObject mo, Object fieldValue,
            Field field) throws IllegalAccessException, MOSException, NoSuchFieldException
    {
        if (ManagementObject.class.isAssignableFrom(field.getType()))
        {
            ManagementObject obj = (ManagementObject) field.get(mo);

            JsonNode node = toNode(JsonUtil.toString(fieldValue));
            Iterator<String> iterator = node.fieldNames();
            while (iterator.hasNext())
            {
                String innerFieldName = iterator.next();
                Field objField = obj.getClass().getField(innerFieldName);
                convertToField(obj, toObject(node.get(innerFieldName).toString(), Object.class), objField);
            }
        }
        else
        {
            field.set(mo, getInstance(ConverterTempStorage.class).convert(fieldValue, field.getType()));
        }
    }

    public void handle(
            MOS mos, ManagementObject mo, Mo delta, Maybe<Integer> transId,
            DistinguishedList<String> lst) throws MOSException
    {
        mos.updateMO(mo, delta, transId);
        lst.addAll(mos.getAffectedDN(transId));
    }
}
