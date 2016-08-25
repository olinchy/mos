package com.zte.mos.util.tools.joiner;

import com.zte.mos.type.Pair;

import java.util.List;

/**
 * Created by olinchy on 16-3-17.
 */
public interface Relation
{
    <T> List<Pair<SelectIn<T>, SelectIn<T>>> deal();
}
