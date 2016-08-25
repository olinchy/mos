package com.zte.mos.imaged;

import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.IMosBuilder;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;

/**
 * Created by root on 14-12-1.
 * The only interface to create a MOS from outside
 */
public class MosFactory
{
    private static IMosBuilder builder = new MosBuilderImpl();

    public static MOS createMOS(String id, String modelName, String modelVersion) throws MOSException
    {
        return builder.create(id, modelName, modelVersion);
    }

    public static MOS createMOS(ModelMETA meta, RawMos mos)
    {
        return builder.create(meta, mos);
    }

    public static void setBuilder(IMosBuilder userBuilder)
    {
        builder = userBuilder;
    }
}
