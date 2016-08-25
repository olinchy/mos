package com.zte.mos.type;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.scaner.ConverterTempStorage;

import java.util.Date;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-4-23.
 */
public class CommonTypeRegister
{
    public static void regAll()
    {
        try
        {
            getInstance(ConverterTempStorage.class).register(new DateConvertor(), Date.class);
            getInstance(ConverterTempStorage.class).register(new IPV4AddressConvertor(), IPV4Address.class);
            getInstance(ConverterTempStorage.class).register(new DNConverter(), DN.class);
            getInstance(ConverterTempStorage.class).register(new SetConverter(), Set.class);
            getInstance(ConverterTempStorage.class).register(new DistinguishedListConverter(), DistinguishedList.class);
            getInstance(ConverterTempStorage.class).register(new RangeConverter(), Range.class);
            getInstance(ConverterTempStorage.class).register(new MacAddressConverter(), MacAddress.class);

        }
        catch (MOSException e)
        {
            logger(CommonTypeRegister.class).warn("reg failed!", e);
        }
    }
}
