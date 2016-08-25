package com.mos;

import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IMosBuilder;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;

/**
 * Created by luoqingkai on 15-8-10.
 */
public class UtMosBuilder implements IMosBuilder
{

    @Override
    public MOS create(String id, String modelName, String modelVersion) throws MOSException {
        UT_MOS mos = new UT_MOS();
        return mos;
    }

    @Override
    public MOS createMos(MoMetaClass metaOfRoot, String dn, String ip, String version) throws MOSException
    {
        return null;
    }

    @Override
    public MOS create(ModelMETA meta, RawMos mos)
    {
        return null;
    }
}
