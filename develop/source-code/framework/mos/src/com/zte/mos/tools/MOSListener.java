package com.zte.mos.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.exp.mos.MosBaseListener;
import com.zte.exp.mos.Mos_expParser;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.persistent.Record;
import org.antlr.v4.runtime.misc.NotNull;

import java.lang.reflect.Type;

public class MOSListener extends MosBaseListener
{

    private ManagementObject mo = null;
    private Object local = null;
    private Record record = null;

    public MOSListener(ManagementObject mo, Object local)
    {
        this(mo, local, null);
    }

    public MOSListener(Record record)
    {
        this(null, null, record);
    }

    public MOSListener(ManagementObject mo, Object local, Record record)
    {
        this.mo = mo;
        this.local = local;
        this.record = record;
    }

    @Override
    public void exitDbexp(@NotNull Mos_expParser.DbexpContext ctx)
    {
        if (record != null)
        {
            String name = ctx.getText();
            Object value = record.get(name);
            this.pushObject(value);
        }
        else
        {
            throw new IllegalArgumentException(
                    "record is not allowed to be null when db expression is in used");
        }

    }

    @Override
    public void exitLocalexp(@NotNull Mos_expParser.LocalexpContext ctx)
    {
        Object obj = getObjProperty(this.local, ctx.name().getText());
        this.pushObject(obj);
    }

    @Override
    public void exitMoexp(@NotNull Mos_expParser.MoexpContext ctx)
    {
        String className = ctx.className().getText();
        if (isAssignableFrom(className))
        {
            Object obj = getObjProperty(mo, ctx.propertyName().getText());
            pushObject(obj);
        }
        else
        {
            stack.push(Boolean.FALSE);
        }

    }

    private boolean isAssignableFrom(String className)
    {
        if (!className.equals("mo"))
        {
            return checkClass(mo.getClass(), className);
        }
        return true;
    }

    private boolean checkClass(Class<?> clazz, String className)
    {
        if (clazz == null)
        {
            return false;
        }

        if (clazz.getSimpleName().equals(className))
        {
            return true;
        }
        else
        {
            Type type = clazz.getGenericSuperclass();
            if (type instanceof Class)
            {
                return checkClass((Class<?>) clazz.getGenericSuperclass(), className);
            }
            else
            {
                return false;
            }
        }
    }

    private Object getObjProperty(Object obj, String text)
    {
        try
        {
            if (obj instanceof JsonNode)
            {
                return ((JsonNode) obj).findValue(text).asText();
            }
            return obj.getClass().getField(text).get(obj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
