package com.zte.mos.msg.impl.restful.southservice.mocks;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoActionMsg;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dongyue on 16-7-13.
 */
public class Alarm implements ManagementObject {
    @Override
    public DN dn() {
        return new DN("Alarm");
    }

    @Override
    public void setDn(String dn) {

    }

    @Override
    public void setParent(ManagementObject parent) {

    }

    @Override
    public void create() throws MOSException {

    }

    @Override
    public ActionRsp act(MOS mos, MoActionMsg msg) throws MOSException {
        return null;
    }

    @Override
    public Mo afterGet() throws MOSException {
        return null;
    }

    @Override
    public Mo toMoClass() throws MOSException {
        Mo mo = new Mo();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        mo.setMo(map);
        return mo;
    }

    @Override
    public Object referencedKilled(String fieldName, DN who) throws MOSException {
        return null;
    }

    @Override
    public MoMetaClass meta() throws MOSException {
        return null;
    }

    @Override
    public MoMetaClass childMeta(String childName) throws MOSException {
        return null;
    }

    @Override
    public void setMos(MOS mos) {

    }

    @Override
    public List<String> lsDN() {
        return null;
    }

    @Override
    public ManagementObject clone() {
        return null;
    }

    @Override
    public ManagementObject[] ls() throws MOSException {
        return new ManagementObject[0];
    }

    @Override
    public boolean add(ManagementObject managementObject) {
        return false;
    }

    @Override
    public boolean remove(ManagementObject managementObject) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ManagementObject> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public void destroy() throws MOSException {

    }
}
