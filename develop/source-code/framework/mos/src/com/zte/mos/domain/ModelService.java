package com.zte.mos.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.annotation.Imaged;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IModelService;
import com.zte.mos.service.impl.MosHead;
import com.zte.mos.type.Pair;
import com.zte.mos.util.Visitor;
import com.zte.mos.util.basic.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.toObject;

public class ModelService implements IModelService
{
    private static Logger log = logger(ModelService.class);
    private HashMap<String, ModelMETA> headerMap = new HashMap<String, ModelMETA>();

    @Override
    public void register(MoMetaClass rootMeta)
    {

        ModelHead modelHead;
        try
        {
            modelHead = buildModelHead(rootMeta);
        }
        catch (MOSException e)
        {
            log.error("register meta error:", e);
            return;
        }
        MosHead mosHead = buildMosHead(rootMeta);
        String key = buildKey(modelHead.modelName(), modelHead.modelVersion());

        headerMap.put(key, new ModelMETA(modelHead, mosHead));
    }

    @Override
    public void start()
    {
        try
        {
            final ArrayList<MoMetaClass> list = new ArrayList<MoMetaClass>();
            getInstance(MetaStringStore.class).visit(new Visitor<Pair<EntityName, JsonNode>>()
            {
                @Override
                public void visit(
                        Pair<EntityName, JsonNode> pair) throws MOSException
                {
                    MoMetaClass meta = toObject(pair.second().toString(), MoMetaClass.class);
                    if (meta != null && meta.derivedFrom != null)
                    {
                        if (meta.derivedFrom.equals("Product"))
                        {
                            meta.forName(pair.first().getFullName());
                            list.add(meta);
                        }
                    }
                }
            });

            for (MoMetaClass meta : list)
            {
                register(meta);
            }
        }
        catch (MOSException e)
        {
            log.error(" fail to start model service ", e);
        }
    }

    @Override
    public void stop()
    {
        headerMap.clear();
    }

    private static ModelHead buildModelHead(MoMetaClass rootMeta) throws MOSException
    {
        return new ModelHead(rootMeta);
    }

    private static MosHead buildMosHead(MoMetaClass rootMeta)
    {
        Imaged annotation = rootMeta.type.getAnnotation(Imaged.class);
        return new MosHead(annotation);
    }

    private static String buildKey(String type, String version)
    {
        return type + "_" + version;
    }

    public ModelMETA get(String type, String version)
    {
        String refinedType = type.toLowerCase();
        return headerMap.get(buildKey(refinedType, version)) == null ? headerMap.get(
                buildKey(refinedType, "")) : headerMap.get(buildKey(refinedType, version));
    }
}
