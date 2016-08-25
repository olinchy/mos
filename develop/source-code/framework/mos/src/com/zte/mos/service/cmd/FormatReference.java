package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.ReferenceClass;
import com.zte.mos.service.MOS;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.ReferenceObject;
import com.zte.mos.util.LambdaConverter;

import java.util.ArrayList;

import static com.zte.mos.util.To.map;

/**
 * Created by olinchy on 16-2-25.
 */
public class FormatReference
{
    public static void formatRefDNInLocalContext(MOS mos, ManagementObject mo, Mo moClass) throws MOSException
    {
        for (ReferenceClass referenceClass : mo.meta().reference)
        {
            String refTo = moClass.get(referenceClass.name) == null ? null : moClass.get(
                    referenceClass.name).toString();
            if (refTo != null && !mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN) && refTo.startsWith(
                    mos.getRootExternalDN()))
            {
                refTo.replace(mos.getRootExternalDN(), "");
                try
                {
                    mo.getClass().getField(referenceClass.name).set(mo, refTo);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void appendMOSDN2Ref(MOS mos, ManagementObject mo, Mo moClass) throws MOSException
    {
        for (ReferenceClass referenceClass : mo.meta().reference)
        {
            Object refDN = moClass.get(referenceClass.name);
            if (refDN != null && DN.isDN(String.valueOf(refDN)))
                moClass.setAttr(referenceClass.name, new DN(mos.getRootExternalDN()).append(
                        refDN.toString()).toString());
        }
    }

    public static void parseMoClass(final MOS mos, ManagementObject mo, Mo moClass, Maybe<Integer> transactionID) throws MOSException
    {
        ReferenceObject obj = ReferenceDBInBerkeley.get(mos).get(mo.dn().toString(), transactionID);

        if (!mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN))
        {
            moClass.setDn(new DN(mos.getRootExternalDN() + mo.dn().toString()));
        }

        // FIXME: 16-2-25 append mos dn to reference dn when mo is been getting
        appendMOSDN2Ref(mos, mo, moClass);

        if (obj != null)
        {
            // FIXME: 16-2-25 same to up
            moClass.setAttr("refBys", map(obj.refBy, new LambdaConverter<String, String>()
            {
                @Override
                public String to(String refBy)
                {
                    if (!mos.getRootExternalDN().equals(MOS.ROOT_INTERNAL_DN) && !refBy.startsWith(
                            mos.getRootExternalDN()) && !refBy.startsWith("/Ems"))
                        refBy = new DN(mos.getRootExternalDN()).append(refBy).toString();
                    return refBy;
                }
            }));
        }
        else
        {
            moClass.setAttr("refBys", new ArrayList<String>());
        }
    }
}
