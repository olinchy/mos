package com.zte.mos.util.tools;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.exception.MO2RecordException;
import com.zte.mos.exception.PersistentException;
import com.zte.mos.persistent.Record;

import java.lang.reflect.Field;

/**
 * Created by olinchy on 7/2/14 for MO_JAVA.
 */
public class ToRecord
{
    public static Record toRecord(Object mo) throws PersistentException
    {
        Object dn;
        try
        {
            dn = mo.getClass().getField("dn").get(mo);
        }
        catch (Exception e)
        {
            throw new MO2RecordException(e);
        }
        Record record = new Record(mo.getClass().getSimpleName(), "dn");
        Field[] fields = mo.getClass().getFields();
        for (Field field : fields)
        {
            setAttr(mo, record, field);
        }
        return record;

    }

    private static void setAttr(Object mo, Record record, Field field)
            throws MO2RecordException
    {
        try
        {
            String fieldName;
            Object value;
            if (field.isAnnotationPresent(MoReference.class))
            {
                fieldName = field.getAnnotation(MoReference.class).field();
                value = field.get(mo);
            }
            else if (field.isAnnotationPresent(MoAttribute.class))
            {
                MoAttribute attr = field.getAnnotation(MoAttribute.class);
                fieldName = !attr.field().equals("") ? attr.field() : field.getName();
                if (attr.converter() != FieldToAndFroWithValidator.class)
                    value = attr.converter().newInstance().to(field.get(mo));
                else
                    value = attr.type().to(field.get(mo));
            }
            else
            {
                return;
            }

            record.add(fieldName, value);
        }
        catch (Exception e)
        {
            throw new MO2RecordException(e);
        }
    }
}
