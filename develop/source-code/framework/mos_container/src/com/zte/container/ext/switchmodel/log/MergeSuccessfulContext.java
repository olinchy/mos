package com.zte.container.ext.switchmodel.log;

import com.zte.mos.message.Mo;
import com.zte.mos.service.RawMos;

import java.util.List;

/**
 * Created by ccy on 5/23/16.
 */
public class MergeSuccessfulContext implements Context
{
    public MergeSuccessfulContext(Mo origin, List<MoOperateLog> moOperateLogs, RawMos mos)
    {
        this.mo = origin;
        this.logs = moOperateLogs;
        this.mos = mos;
    }

    private final Mo mo;
    private final List<MoOperateLog> logs;
    private final RawMos mos;

    public Mo originMo()
    {
        return this.mo;
    }

    public List<MoOperateLog> moOperateLogs()
    {
        return this.logs;
    }

    public RawMos rawMos()
    {
        return this.mos;
    }

}
