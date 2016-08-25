package com.odb.database.operstate;

import com.zte.mos.domain.Group;

/**
 * Created by olinchy on 5/20/14.
 */
public interface StateMaker
{
    <T extends Group> DataState<T> make(T t);
}

