package com.zte.mos.message;

import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olinchy on 15-5-18.
 */
public interface IndicationSender<T extends Group>
{
    void noti(List<Log<T>> logList) throws MOSException;
}
