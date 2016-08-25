package com.zte.mos.service.impl;

import com.zte.mos.service.IConvertService;
import com.zte.mos.type.CommonTypeRegister;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.util.basic.Logger.logger;

class ConvertService implements IConvertService
{
    private final Logger log = logger(ConvertService.class);

    @Override
    public void start()
    {
        try
        {
            CommonTypeRegister.regAll();
            log.debug("start converter service success");
        }
        catch (Throwable e)
        {
            logger(CommonTypeRegister.class).error("fail to start converter service", e);
        }
    }

    @Override
    public void stop()
    {

    }
}
