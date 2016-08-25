package com.zte.mos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.annotation.Type;
import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.*;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;
import com.zte.mos.util.msg.MoActionMsg;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.tools.JsonUtil.toNode;

public abstract class BaseManagementObject implements ManagementObject
{
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    protected transient MOS mos;

    public BaseManagementObject(DN dn)
    {
        this.dn = dn.toString();
    }

    public BaseManagementObject()
    {
    }

    public String dn;
    @JsonIgnore
    @MoAttribute(field = "filterby", length = 30, type = Type.String)
    public String classType = this.getClass().getSimpleName();
    public DistinguishedList<String> children = new DistinguishedList<String>();
    private ManagementObject parent;

    @Override
    public ManagementObject[] ls() throws MOSException
    {
        if (mos != null)
        {
            LinkedList<ManagementObject> list = new LinkedList<ManagementObject>();
            for (String child : children)
            {
                list.add(mos.getMO(child, new Maybe<Integer>(null)));
            }
            return list.toArray(new ManagementObject[children.size()]);
        }
        return new ManagementObject[0];
    }

    @Override
    public boolean add(ManagementObject managementObject)
    {
        return children.add(managementObject.dn().toString());
    }

    @Override
    public boolean remove(ManagementObject managementObject)
    {
        return children.remove(managementObject.dn().toString());
    }

    @Override
    public boolean addAll(Collection<? extends ManagementObject> c)
    {
        ArrayList<ManagementObject> list = new ArrayList<ManagementObject>();
        list.addAll(c);
        return children.addAll(To.map(list, new LambdaConverter<String, ManagementObject>()
        {
            @Override
            public String to(ManagementObject content)
            {
                return content.dn().toString();
            }
        }));
    }

    @Override
    public boolean contains(Object o)
    {
        return o instanceof ManagementObject && children.contains(((ManagementObject) o).dn().toString());
    }

    @Override
    @JsonIgnore
    public boolean isGroup()
    {
        return false;
    }

    @JsonIgnore
    public ManagementObject getParent()
    {
        return parent;
    }

    @Override
    public void setParent(ManagementObject parent)
    {
        this.parent = parent;
    }

    @Override
    public void create() throws MOSException
    {

    }

    @Override
    public ActionRsp act(MOS mos, MoActionMsg msg) throws MOSException
    {
        Method method;
        try
        {
            method = this.getClass().getMethod(msg.actionName(), MOS.class, MoActionMsg.class);
            return (ActionRsp) method.invoke(this, mos, msg);
        }
        catch (InvocationTargetException e)
        {
            Throwable ex = e.getCause();
            if (ex instanceof MOSException)
                throw (MOSException) ex;
            else
                throw new MOSException(ex);
        }
        catch (Exception e)
        {
            if (e instanceof MOSException)
                throw (MOSException) e;
            else
                throw new MOSException(e);
        }
    }

    @Override
    public MoMetaClass meta() throws MOSException
    {
        if (mos == null)
            return null;
        return mos.getMeta(this.getClass().getSimpleName());
    }

    @Override
    public MoMetaClass childMeta(String childName) throws MOSException
    {
        MoMetaClass meta = this.meta();
        for (ChildrenClass child : meta.children)
        {
            if (child.name.equals(childName))
            {
                if (child.type != null)
                {
                    return mos.getMeta(child.type);
                }
                return mos.getMeta(child.name);
            }
        }
        return null;
    }

    @Override
    public Mo toMoClass() throws MOSException
    {
        Mo mo;
        if (this.isGroup())
        {
            mo = new Mo("Group");
            ArrayList<Mo> list = new ArrayList<Mo>();
            for (ManagementObject managementObject : this.ls())
            {
                if (managementObject == null)
                    continue;
                managementObject.setMos(this.mos);
                list.add(managementObject.toMoClass());
            }
            if (list.size() > 0)
                mo.setAttr("children", list);
            mo.setDn(this.dn());
            return mo;
        }
        mo = new Mo(this.getClass(), meta(), toNode(this));
        mo.setDn(this.dn());
        return mo;
    }

    @Override
    public Object referencedKilled(String fieldName, DN who) throws MOSException
    {
        try
        {
            Field field = this.getClass().getField(fieldName);
            MoReference reference = field.getAnnotation(MoReference.class);
            if (reference.isMulti())
            {
                ArrayList list = (ArrayList) field.get(this);
                list.remove(who.toString());
                field.set(this, list);
                return list;
            }
            else
            {
                field.set(this, "");
                return "";
            }
        }
        catch (Throwable e)
        {
        }
        return null;
    }

    @Override
    public DN dn()
    {
        return new DN(dn);
    }

    @Override
    public void setDn(String dn)
    {
        this.dn = dn;
    }

    @Override
    public int hashCode()
    {
        return dn != null ? dn.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof BaseManagementObject))
        {
            return false;
        }

        BaseManagementObject that = (BaseManagementObject) o;
        return this.dn().equals(that.dn());
    }

    public BaseManagementObject clone()
    {
        try
        {
            BaseManagementObject clone = (BaseManagementObject) super.clone();
            clone.children = new DistinguishedList<String>();
            clone.children.addAll(children);
            return clone;
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString()
    {
        if (dn == null)
        {
            return "";
        }
        return dn().toString();
    }

    public boolean deepEquals(BaseManagementObject obj)
    {
        if (obj == null || !dn.equals(obj.dn))
        {
            return false;
        }
        Field[] fields = this.getClass().getFields();
        for (Field f : fields)
        {
            if (!f.isAnnotationPresent(MoAttribute.class))
            {
                continue;
            }
            try
            {
                if (f.getType().isArray())
                {
                    Object[] myArray = (Object[]) f.get(this);
                    Object[] hisArray = (Object[]) f.get(obj);
                    if (myArray.length == hisArray.length)
                    {
                        for (int i = 0; i < myArray.length; i++)
                        {
                            if (!myArray[i].equals(hisArray))
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    if (!f.isAccessible())
                    {
                        continue;
                    }
                    Object myObj = f.get(this);
                    Object hisObj = f.get(obj);
                    if (myObj == null)
                    {
                        return !(null == hisObj);
                    }
                    else if (!myObj.equals(hisObj))
                    {
                        return false;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean isKeyAttributeEquals(BaseManagementObject obj)
    {
        if (obj == null || !dn.equals(obj.dn))
        {
            return false;
        }
        Field[] fields = this.getClass().getFields();
        try
        {
            for (Field f : fields)
            {
                if (f.isAnnotationPresent(MoAttribute.class))
                {
                    MoAttribute attr = f.getAnnotation(MoAttribute.class);
                    boolean fieldValueEqual = isObjEquals(f.get(this), f.get(obj));

                    boolean fieldMutable = attr.mutable();
                    if (!fieldMutable && !fieldValueEqual)
                    {
                        return false;
                    }
                }
            }
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    private boolean isObjEquals(Object thisFValue, Object objFValue)
    {
        if (thisFValue == objFValue)
        {
            return true;
        }
        else
        {
            return thisFValue != null && thisFValue.equals(objFValue);
        }
    }

    @Override
    public void destroy() throws MOSException
    {
    }

    protected <T extends ActionRsp> T act(Class<?> clazz, MOS mos, MoActionMsg msg) throws MOSException
    {
        MoMetaClass meta = meta();
        Action action = getInstance(ActionRepo.class).get(clazz, msg.actionName());
        ActionClass actionClass = new ActionClass();
        actionClass.name = msg.actionName();

        if (action == null && meta.actions.customs.contains(actionClass))
        {
            JsonNode node = mos.act(msg);
            ActionRsp rsp = null;
            if (node.get("result").intValue() != 0)
            {
                throw new MOSException(new ErrorCode(node.get("result").intValue()));
            }
            else
            {
                for (ActionClass ac : meta.actions.customs)
                {
                    if (ac.name.equalsIgnoreCase(msg.actionName() + "Rsp"))
                    {
                        rsp = new ActionRsp(ac, node.get("mo"));
                    }
                }
            }
            return (T) rsp;
        }
        else
        {
            return (T) action.on(this, mos, msg.paras());
        }
    }

    @JsonIgnore
    public void setMos(MOS mos)
    {
        this.mos = mos;
    }

    public Mo afterGet() throws MOSException
    {
        return this.toMoClass();
    }

    @JsonIgnore
    public MOS getMos()
    {
        return mos;
    }

    @Override
    public List<String> lsDN()
    {
        LinkedList<String> list = new LinkedList<String>();
        list.addAll(children);

        return list;
    }
}

