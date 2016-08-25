package com.zte.mos.service.impl;


import com.zte.mos.exception.MOSException;
import com.zte.mos.service.IPersistenceService;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.PreLoadScanner;

import static com.zte.mos.util.basic.Logger.logger;

class PersistenceService implements IPersistenceService
{
    private final Logger log = logger(PersistenceService.class);
    private final String pkg = "com.zte.mos.persistent";

    @Override
    public void start() {

        PreLoadScanner scanner = new PreLoadScanner();
        try
        {
            scanner.scan(Scan.getClasses(pkg, Object.class));
            log.debug("data persistent component initialized.");
        }
        catch (MOSException e)
        {
            log.error("fail to start persistence service", e);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
