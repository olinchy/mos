package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.JsonUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by olinchy on 3/17/15 for mosjava.
 */
public class Mo implements Serializable
{
    private MoMetaClass metaClass;
    private String moClass;
    private String dn;
    private HashMap<String, Object> mo = new HashMap<String, Object>();

    public Mo()
    {

    }

    public Mo(String moClass)
    {
        this.moClass = moClass;
    }

    public Mo(Class<?> clazz, MoMetaClass metaClass, JsonNode node)
    {
        this.moClass = clazz.getSimpleName();
        this.metaClass = metaClass;
        if (this.metaClass != null)
            this.metaClass.type = null;
        buildConfDataMap(clazz, node);
    }

    @JsonIgnore
    public MoMetaClass getMeta()
    {
        return metaClass;
    }

    public HashMap<String, Object> getMo()
    {
        return mo;
    }

    public void setMo(HashMap<String, Object> mo)
    {
        this.mo = mo;
    }

    public String getMoClass()
    {
        return moClass;
    }

    public void setMoClass(String moClass)
    {
        this.moClass = moClass;
    }

    public Mo setAttr(String attrName, Object value)
    {
        mo.put(attrName, value);
        return this;
    }

    private void buildConfDataMap(Class<?> clazz, JsonNode node)
    {
        for (Field field : clazz.getFields())
        {
            if (field.isAnnotationPresent(MoAttribute.class) && !field.isAnnotationPresent(JsonIgnore.class))
            {
                MoAttribute attr = field.getAnnotation(MoAttribute.class);
                JsonNode nodeValue = node.get(field.getName());
                Object value;
                if (nodeValue == null || nodeValue.isNull())
                {
                    value = null;
                }
                else
                {
                    if (nodeValue.isArray())
                    {
                        value = JsonUtil.toObject(nodeValue.toString(), ArrayList.class);
                    }
                    else
                    {
                        try
                        {
                            value = attr.type().fro(nodeValue.asText());
                        }
                        catch (Exception ignore)
                        {
                            value = JsonUtil.toObject(nodeValue.toString(), Object.class);
                        }
                    }
                }
                mo.put(field.getName(), value);
            }
            else if (field.isAnnotationPresent(MoReference.class))
            {
                MoReference ref = field.getAnnotation(MoReference.class);
                if (ref.isMulti())
                {
                    ArrayList<String> lstRef = new ArrayList<String>();
                    Iterator<JsonNode> iterator = node.get(field.getName()).elements();
                    while (iterator.hasNext())
                    {
                        lstRef.add(iterator.next().asText());
                    }
                    mo.put(field.getName(), lstRef);
                }
                else
                    mo.put(field.getName(), node.get(field.getName()).asText());
            }
        }
    }

    public DN getDn()
    {
        return new DN(dn);
    }

    @JsonDeserialize(using = DNJsonDeserializer.class)
    public Mo setDn(DN dn)
    {
        this.dn = dn.toString();
        return this;
    }

    public Object get(String fieldName)
    {
        return this.mo.get(fieldName);
    }

    public Mo duplicate()
    {
        Mo mo = new Mo(this.moClass);
        return mo.setDn(new DN(this.dn));
    }

    public <T> T to(Class<T> meta) throws MOSException
    {
        return toObject(JsonUtil.toNode(this.mo).toString(), meta);
    }

    @Override
    public String toString()
    {
        try
        {
            return JsonUtil.toString(this);
        }
        catch (MOSException e)
        {
            return "mo {}";
        }
    }
}
