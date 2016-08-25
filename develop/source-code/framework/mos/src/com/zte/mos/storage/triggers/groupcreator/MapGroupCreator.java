package com.zte.mos.storage.triggers.groupcreator;

import com.odb.database.Odb;
import com.zte.mos.annotation.MoAutoCreate;
import com.zte.mos.annotation.MoChildDesc;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.storage.triggers.ChildrenCreator;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-3-22.
 */
public class MapGroupCreator implements ChildrenCreator
{
    public MapGroupCreator(ManagementObject mo, MoAutoCreate autoCreate)
    {
        this.mo = mo;
        this.autoCreate = autoCreate;
    }

    private final MoAutoCreate autoCreate;
    private final ManagementObject mo;

    @Override
    public void addTo(Odb<ManagementObject> odb) throws MOSException
    {
        for (MoChildDesc moChildDesc : autoCreate.value())
        {
            DN childDN = mo.dn().append(moChildDesc.key());
            try
            {
                Class<?> childClass = mo.childMeta(moChildDesc.className()).type;
                Mo mo = new Mo(childClass.getSimpleName());
                if (!moChildDesc.template().equals(""))
                {
                    LinkedHashMap<String, String> map = JsonUtil.toObject(moChildDesc.template(), LinkedHashMap.class);
                    for (Map.Entry<String, String> entry : map.entrySet())
                    {
                        mo.setAttr(entry.getKey(), entry.getValue());
                    }
                }
                ManagementObject obj = (ManagementObject) mo.to(childClass);
                obj.setMos(((BaseManagementObject) this.mo).getMos());
                obj.setDn(childDN.toString());

                logger(this.getClass()).debug("auto creating " + obj.toString());

                odb.add(obj);
            }
            catch (Throwable e)
            {
                logger(this.getClass()).warn("create child: " + moChildDesc.className() + " failed", e);
            }
        }
    }
}
