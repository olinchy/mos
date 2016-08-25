package com.zte.mos.storage.triggers.nongroupcreator;

import com.odb.database.Odb;
import com.zte.mos.annotation.MoAutoCreate;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.GroupOf;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.storage.triggers.ChildrenCreator;
import com.zte.mos.util.LambdaFilter;
import com.zte.mos.util.To;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-3-22.
 */
public class MoChildrenCreator implements ChildrenCreator
{
    public MoChildrenCreator(ManagementObject mo)
    {
        this.mo = mo;
    }

    private final ManagementObject mo;

    @Override
    public void addTo(Odb<ManagementObject> odb) throws MOSException
    {
        Field[] nonDuplicate = removeDuplicate(mo.getClass().getFields(), mo.getClass().getDeclaredFields());

        for (Field field : nonDuplicate)
        {
            try
            {
                ManagementObject childMo;
                if (GroupOf.class.isAssignableFrom(field.getType()))
                {
                    // all groups
                    childMo = (ManagementObject) field.getType().newInstance();
                    childMo.setDn(mo.dn().append(field.getName()).toString());
                    childMo.setMos(((BaseManagementObject) mo).getMos());
                    odb.add(childMo);
                }
                else
                {
                    // all mo linked directly
                    if (field.isAnnotationPresent(MoAutoCreate.class))
                    {
                        MoMetaClass meta = mo.childMeta(field.getName());
                        childMo = (ManagementObject) meta.type.newInstance();
                        childMo.setMos(((BaseManagementObject) mo).getMos());
                        Mo child = childMo.toMoClass();
                        field.set(mo, child);

                        childMo.setDn(mo.dn().append(field.getName()).toString());

                        odb.add(childMo);
                    }
                }
            }
            catch (Throwable e)
            {
                logger(this.getClass()).warn("auto create child of " + mo.getClass() + " failed", e);
                throw new MOSException(e);
            }
        }
    }

    private Field[] removeDuplicate(Field[] inThis, Field[] replaceByThis)
    {
        HashMap<String, Field> set = new HashMap<String, Field>();
        push(set, inThis);
        push(set, replaceByThis);

        return set.values().toArray(new Field[set.size()]);
    }

    private void push(HashMap<String, Field> map, Field[] toPush)
    {
        for (Field field : To.filter(Arrays.asList(toPush), new LambdaFilter<Field>()
        {
            @Override
            public boolean isAccept(Field content)
            {
                return content.isAnnotationPresent(MoChild.class);
            }
        }))
        {
            map.put(field.getName(), field);
        }
    }
}
