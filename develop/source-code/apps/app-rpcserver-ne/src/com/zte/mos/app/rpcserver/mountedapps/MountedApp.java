package com.zte.mos.app.rpcserver.mountedapps;

import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.EmsJsonService;

/**
 * Created by olinchy on 7/15/14 for MO_JAVA.
 */
public interface MountedApp extends EmsJsonService
{
    void start() throws MOSException;

    void stop() throws MOSException;

}
