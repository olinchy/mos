package com.zte.mos.domain;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.MOS;
import com.zte.mos.util.Clone;
import com.zte.mos.util.msg.MoActionMsg;

import java.io.Serializable;
import java.util.List;

public interface ManagementObject extends Clone<ManagementObject>, Serializable, Group<ManagementObject>
{
    DN dn();

    void setDn(String dn);

    void setParent(ManagementObject parent);

    void create() throws MOSException;

    ActionRsp act(MOS mos, MoActionMsg msg) throws MOSException;

    Mo afterGet() throws MOSException;

    Mo toMoClass() throws MOSException;

    Object referencedKilled(String fieldName, DN who) throws MOSException;

    MoMetaClass meta() throws MOSException;

    MoMetaClass childMeta(String childName) throws MOSException;

    void setMos(MOS mos);

    List<String> lsDN();
}
