package com.zte.mos.annotation;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by olinchy on 15-6-4.
 */
public interface MoReferencePolicy
{
    DistinguishedList<String> undo(String name, DN who) throws MOSException;
    void handleMo(String name, DN who) throws MOSException;
}
