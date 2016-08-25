package com.zte.mos.imaged;


import com.zte.mos.domain.ModelMETA;
import com.zte.mos.service.RawMos;

/**
 * Created by love on 16-1-22.
 */
public class ImagedMosFactory {
    static ImagedMos build(ModelMETA meta, RawMos mos)
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
}
