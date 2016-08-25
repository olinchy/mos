package com.zte.mos.service;

import com.zte.mos.domain.ModelMETA;
import com.zte.mos.message.MoMetaClass;

@VmUnique
public interface IModelService extends IExtService
{
    void register(MoMetaClass rootMeta);

    ModelMETA get(String type, String version);
}
