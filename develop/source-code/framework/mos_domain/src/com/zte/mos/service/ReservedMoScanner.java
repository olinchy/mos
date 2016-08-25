package com.zte.mos.service;

import com.zte.mos.domain.ReservedMoChecker;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.impl.ReservedMoService;
import com.zte.mos.util.scaner.AbstractScanner;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 2/25/16.
 */
public class ReservedMoScanner extends AbstractScanner<Object>
{
    @Override
    public void handle(Class<Object> clazz) throws MOSException
    {
        if (ReservedMoChecker.class.isAssignableFrom(clazz) && !clazz.isInterface())
        {
            try
            {
                ReservedMoChecker reservedMoChecker = (ReservedMoChecker)clazz.newInstance();
                ReservedMoService.register(reservedMoChecker);
            }
            catch (Throwable e)
            {
               logger(ReservedMoScanner.class).error("fail to scan reserved mo, class name " + clazz.getName(), e);
            }
        }
    }
}
