package com.zte.mos.util.msg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.util.tools.JsonUtil;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 6/5/14 for MO_JAVA.
 */
@SuppressWarnings("serial")
public class Template implements Serializable
{
    private String[] fieldNames;
    private FieldTypes fieldType;

    public Template(FieldTypes fieldType)
    {
        this.fieldType = fieldType;
    }

    public Template(String... fieldNames)
    {
        this.fieldNames = fieldNames;
    }

    public JsonNode toNode(Object mo) throws IllegalAccessException, IOException
    {
        ObjectNode node = JsonUtil.newObjNode();
        boolean needRevert = false;

        String[] names = getFieldNamesToOutput(mo);

        for (String fieldName : names)
        {
            try
            {
                Field field = mo.getClass().getField(fieldName);
                if (!field.isAccessible())
                {
                    needRevert = true;
                    field.setAccessible(true);
                }
                node.put(fieldName, JsonUtil.toNode(field.get(mo)));

                if (needRevert)
                {
                    needRevert = false;
                    field.setAccessible(false);
                }
            }
            catch (Exception e)
            {
                logger(this.getClass()).warn("get field " + fieldName + " failed!", e);
            }
        }

        return node;
    }

    private String[] getFieldNamesToOutput(Object mo) throws IllegalAccessException
    {
        ArrayList<String> names = new ArrayList<String>();
        if (fieldNames == null || fieldNames.length == 0)
        {
            boolean isAll = false;
            if (this.fieldType != null)
            {
                isAll = this.fieldType.equals(FieldTypes.all);
            }
            for (Field field : mo.getClass().getFields())
            {
                if (!field.isAnnotationPresent(MoChild.class) && (isAll
                        || field.isAnnotationPresent(MoAttribute.class)
                        || field.isAnnotationPresent(MoReference.class)))
                {
                    names.add(field.getName());
                }
            }
        }
        else
        {
            names.addAll(Arrays.asList(fieldNames));
        }
        return names.toArray(new String[names.size()]);
    }

    public enum FieldTypes
    {
        all, attributes
    }
}
