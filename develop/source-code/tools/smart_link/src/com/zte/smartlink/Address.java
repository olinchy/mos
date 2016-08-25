package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;

import java.util.ArrayList;

public interface Address
{
    Result on(MoMsg msg) throws MOSException;

    Result on(ArrayList<? extends MoMsg> msg) throws MOSRMIException;
}
