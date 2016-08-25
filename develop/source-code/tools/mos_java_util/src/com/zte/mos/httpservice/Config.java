package com.zte.mos.httpservice;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Mo;

/**
 * Created by olinchy on 15-10-8.
 */
public interface Config
{
    ConfResultSet doConfig(MosService service) throws MOSException;

    class ConfigMethods
    {
        public static SET set(Mo... moArray)
        {
            return new SET(moArray);
        }

        public static ADD add(Mo... moArray)
        {
            return new ADD(moArray);
        }

        public static DEL del(DN... dnArray)
        {
            return new DEL(dnArray);
        }
    }
}
