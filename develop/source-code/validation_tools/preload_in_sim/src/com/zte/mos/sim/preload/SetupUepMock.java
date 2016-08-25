package com.zte.mos.sim.preload;

import com.zte.mos.annotation.PreLoad;
import com.zte.uep.stub.UepMock;

/**
 * Created by olinchy on 16-1-26.
 */
@PreLoad
public class SetupUepMock
{
    public SetupUepMock()
    {
        new UepMock().setup();
    }
}
