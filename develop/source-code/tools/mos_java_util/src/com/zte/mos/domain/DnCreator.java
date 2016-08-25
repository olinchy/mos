package com.zte.mos.domain;

import com.zte.mos.persistent.Record;

public interface DnCreator
{
    DN mkDn(String neid, Record record);
}
