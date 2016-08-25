package com.mos.lite.store;

import com.zte.mos.domain.DN;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by olinchy on 15-7-13.
 */
public class StringKeyComparator implements Comparator<byte[]>, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(byte[] o1, byte[] o2)
    {
        return new DN(new String(o1)).compareTo(new DN(new String(o2)));
    }
}
