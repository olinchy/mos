package com.zte.mos.service;

import com.zte.mos.domain.ModelMETA;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;

/**
 * Created by luoqingkai on 15-8-10.
 */
public interface IMosBuilder
{
    MOS create(String id, String modelName, String modelVersion) throws MOSException;

    MOS createMos(MoMetaClass metaOfRoot, String dn, String ip, String version) throws MOSException;

    MOS create(ModelMETA meta, RawMos mos);
}
