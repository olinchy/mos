package com.zte.container.ext.switchmodel.log;

import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by ccy on 5/23/16.
 */
public interface LogCreator<T>
{
    List<Log<T>> createLogs(Context ctx);
}
