package com.zte.mos.node;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IMosBuilder;
import com.zte.mos.service.MOS;

class MosFactory
{
    private static IMosBuilder builder = new EmsMosBuilder();

    public static MOS createMOS(MoMetaClass metaOfRoot, String dn, String ip, String version) throws MOSException
    {
        return builder.createMos(metaOfRoot, dn, ip, version);
    }

    public static void setBuilder(IMosBuilder userBuilder)
    {
        builder = userBuilder;
    }
}
