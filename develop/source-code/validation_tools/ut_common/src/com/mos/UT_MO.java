package com.mos;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoActionMsg;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created in name of luoqingkai on 15-8-17.
 */
public class UT_MO implements ManagementObject
{
    private String dn;

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
    public void setParent(ManagementObject parent)
    {

    }

    @Override
    public void create() throws MOSException
    {

    }

    @Override
    public ActionRsp act(MOS mos, MoActionMsg msg) throws MOSException
    {
        return null;
    }

    @Override
    public Mo afterGet() throws MOSException
    {
        return null;
    }

    @Override
    public MoMetaClass meta() throws MOSException
    {
        return null;
    }

    @Override
    public MoMetaClass childMeta(String childName) throws MOSException
    {
        return null;
    }

    @Override
    public void setMos(MOS mos)
    {

    }

    @Override
    public List<String> lsDN()
    {
        return null;
    }

    @Override
    public Mo toMoClass() throws MOSException
    {
        return null;
    }

    @Override
    public Object referencedKilled(String fieldName, DN who) throws MOSException
    {
        return null;
    }

    @Override
    public ManagementObject clone()
    {
        return null;
    }

    @Override
    public ManagementObject[] ls() throws MOSException
    {
        return new ManagementObject[0];
    }

    @Override
    public boolean add(ManagementObject managementObject)
    {
        return false;
    }

    @Override
    public boolean remove(ManagementObject managementObject)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ManagementObject> c)
    {
        return false;
    }

    @Override
    public boolean contains(Object o)
    {
        return false;
    }

    @Override
    public boolean isGroup()
    {
        return false;
    }

    @Override
    public void destroy() throws MOSException
    {

    }
}
