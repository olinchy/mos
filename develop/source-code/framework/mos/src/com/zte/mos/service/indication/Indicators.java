package com.zte.mos.service.indication;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.MOSAdaptor;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.mos.util.msg.MoCreateIndicationMsg;
import com.zte.mos.util.msg.MoDeleteIndicationMsg;
import com.zte.mos.util.msg.MoSetIndicationMsg;

import static com.zte.mos.util.To.map;

/**
 * Created by olinchy on 15-5-18.
 */
public enum Indicators implements IndMsgCreator<ManagementObject>
{
    Insert
            {
                @Override
                public IndicateMsg createMsg(
                        Log<ManagementObject> tLog,
                        final String rootExternalDN) throws MOSException
                {
                    final String dn = fillDN(tLog.newValue(), rootExternalDN);
                    final Mo newValue = tLog.newValue().toMoClass();
                    fillInRefBys(rootExternalDN, newValue);

                    newValue.setDn(new DN(dn));
                    return new MoCreateIndicationMsg(dn, newValue);
                }
            },
    Update
            {
                @Override
                public IndicateMsg createMsg(
                        Log<ManagementObject> tLog,
                        String rootExternalDN) throws MOSException
                {
                    String dn = fillDN(tLog.newValue(), rootExternalDN);
                    Mo newValue = tLog.newValue().toMoClass();
                    Mo oldValue = tLog.oldValue() != null ? tLog.oldValue().toMoClass() : null;
                    fillInRefBys(rootExternalDN, newValue);
                    newValue.setDn(new DN(dn));
                    oldValue.setDn(new DN(dn));
                    return new MoSetIndicationMsg(dn, oldValue, newValue);
                }
            },
    Delete
            {
                @Override
                public IndicateMsg createMsg(
                        Log<ManagementObject> tLog,
                        String rootExternalDN) throws MOSException
                {
                    String dn = fillDN(tLog.oldValue(), rootExternalDN);
                    Mo oldValue = tLog.oldValue().toMoClass();
                    fillInRefBys(rootExternalDN, oldValue);
                    oldValue.setDn(new DN(dn));
                    return new MoDeleteIndicationMsg(dn, oldValue);
                }
            };

    private static void fillInRefBys(final String rootExternalDN, Mo moClass)
    {
        if (moClass.getDn() == null)
        {
            return;
        }
        ReferenceObject obj = ReferenceDBInBerkeley.get(new MOSAdaptor()
        {
            @Override
            public String getRootExternalDN()
            {
                return rootExternalDN;
            }
        }).get(moClass.getDn().toString());
        if (obj != null)
        {
            // FIXME: 16-2-25 same to up
            moClass.setAttr("refBys", map(obj.refBy, new LambdaConverter<String, String>()
            {
                @Override
                public String to(String refBy)
                {
                    if (!rootExternalDN.equals(MOS.ROOT_INTERNAL_DN) && !refBy.startsWith(
                            rootExternalDN))
                        refBy = new DN(rootExternalDN).append(refBy).toString();
                    return refBy;
                }
            }));
        }
    }

    private static String fillDN(ManagementObject managementObject, String rootExternalDN)
    {
        if (rootExternalDN.equalsIgnoreCase("/"))
            return managementObject.dn().toString();
        return rootExternalDN + managementObject.dn().toString();
    }

}
