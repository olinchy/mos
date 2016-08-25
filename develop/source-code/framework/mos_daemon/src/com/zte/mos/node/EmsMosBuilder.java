package com.zte.mos.node;

import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IMosBuilder;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;

import static com.zte.mos.util.basic.Logger.logger;

public class EmsMosBuilder implements IMosBuilder
{
    @Override
    public MOS create(String id, String modelName, String modelVersion) throws MOSException
    {
        return null;
    }

    @Override
    public MOS createMos(MoMetaClass metaOfRoot, String dn, String ip, String version) throws MOSException
    {
        ModelHead header = new ModelHead(metaOfRoot);
        return new RawMos(dn, header, "MOS");
    }

    @Override
    public MOS create(ModelMETA meta, RawMos mos)
    {
        return null;
    }
}
