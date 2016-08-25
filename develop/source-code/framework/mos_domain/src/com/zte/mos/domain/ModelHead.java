package com.zte.mos.domain;

import com.zte.mos.message.MoMetaClass;
import com.zte.mos.util.tools.JsonUtil;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 16-1-8.
 * The bacsic information of a model
 */
public class ModelHead
{
    public ModelHead(MoMetaClass rootMeta)
    {
        this.rootMeta = rootMeta;
    }

    //    public final String type;
//    public final String version;
//    public final Class<ManagementObject> rootClass;
    private final MoMetaClass rootMeta;

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ModelHead)
        {
            ModelHead objHead = (ModelHead) obj;
            return rootMeta.type.equals(objHead.rootMeta.type) && rootMeta.mosVersion.equals(
                    objHead.rootMeta.mosVersion);
        }
        else
        {
            return false;
        }
    }

    public ManagementObject buildMo(String simpleName)
    {
        return this.buildMo(simpleName, null);
    }

    public ManagementObject buildMo(String simpleName, String content)
    {
        try
        {
            Class<?> clazz = getInstance(MetaStringStore.class).getClass(rootMeta.modelName, rootMeta.modelVersion, simpleName);

            if (content != null && !content.equals(""))
            {
                return (ManagementObject)JsonUtil.toObject(content, clazz);
            }

        }
        catch (Throwable e)
        {
            logger(ModelHead.class).error("build mo with content fails,  className = " + simpleName, e);
        }
        return null;
    }

    public String modelName()
    {
        return rootMeta.modelName;
    }

    public String msgVersion()
    {
        return rootMeta.mosVersion;
    }

    public String modelVersion()
    {
        return rootMeta.modelVersion;
    }

    public Class<ManagementObject> rootClass()
    {
        return (Class<ManagementObject>) rootMeta.type;
    }
}
