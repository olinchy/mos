package com.zte.mos.domain;


import com.zte.mos.exception.MOSException;
import com.zte.mos.service.MOS;

public interface Diff {
    int revision();
    void sync(MOS mos, ModelHead header) throws MOSException;
}
