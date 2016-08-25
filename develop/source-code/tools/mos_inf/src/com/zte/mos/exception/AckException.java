package com.zte.mos.exception;

import com.zte.mos.inf.MoMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 16-4-12.
 */
public class AckException extends MOSException
{
    public AckException()
    {
        super(new ErrorCode(ErrorCodeGetter.ACK_ERROR));
    }

    public AckException(List<AckException> exceptions)
    {
        this.exceptions = exceptions;
    }

    public AckException(MOSException e, String... dnList)
    {
        this.exception = e;
        this.dnList = dnList;
    }

    public String[] getDnList()
    {
        return dnList;
    }

    public void setDnList(String[] dnList)
    {
        this.dnList = dnList;
    }

    private String[] dnList;
    private MOSException exception;
    private List<AckException> exceptions = new ArrayList<AckException>();
    private MoMsg ack;

    public MOSException getException()
    {
        return exception;
    }

    public void setException(MOSException exception)
    {
        this.exception = exception;
    }

    public List<AckException> getExceptions()
    {
        return exceptions;
    }

    public void setExceptions(List<AckException> exceptions)
    {
        this.exceptions = exceptions;
    }

    public MoMsg getAck()
    {
        return ack;
    }

    public void setAck(MoMsg ack)
    {
        this.ack = ack;
    }
}
