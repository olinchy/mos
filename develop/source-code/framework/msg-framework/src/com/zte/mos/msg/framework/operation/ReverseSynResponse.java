package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.SysSnapSpot;
import com.zte.mos.msg.framework.observer.Observer;

public class ReverseSynResponse extends AbstractResponse implements SysSnapSpot
{

    private final Observer observer;

    public ReverseSynResponse(int result, int revision, Observer observer)
    {
        super(result);
        this.revision = revision;
        this.observer = observer;
    }

    private ManagementObject[] moArray;

    private int revision;

    public ReverseSynResponse(int result, int revision, ManagementObject[] moArray, Observer observer)
    {
        super(result);
        this.moArray = moArray;
        this.revision = revision;
        this.observer = observer;
    }

    public ManagementObject[] getMoArray() {
        return moArray;
    }

    public int revision() {
        return revision;
    }

    public Observer getObserver()
    {
        return observer;
    }
}
