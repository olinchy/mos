package com.zte.mos.imaged;

import com.zte.container.ext.ExtServiceProvider;
import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IModelService;
import com.zte.mos.service.IMosBuilder;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;

public class MosBuilderImpl implements IMosBuilder
{
    @Override
    public MOS create(String id, String modelName, String modelVersion) throws MOSException
    {
        IModelService sv = ExtServiceProvider.getInstance().getModelService();
        ModelMETA meta = sv.get(modelName, modelVersion);

        if (meta == null)
        {
            throw new MOSException("no such product model loaded:" + modelName + ", " + modelVersion);
        }

        MOS mos = createNe(meta, id);
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
        if (meta.mosHead.version() == 1.1f)
        {
            return new V11ImagedMos(meta, mos);
        }
        else
        {
            return new V12ImagedMos(meta, mos);
        }
    }

    private MOS createNe(ModelMETA meta, String id) throws MOSException
    {
        ImagedMos mos;
        if (meta.mosHead.version() == 1.1f)
        {
            mos = new V11ImagedMos(meta, id, "BUNDLE");
        }
        else
        {
            mos = new V12ImagedMos(meta, id, "BUNDLE");
        }

        return mos;
    }
}
