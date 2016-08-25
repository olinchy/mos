package com.zte.app.smartlink.mos;

import com.zte.smartlink.Address;

public class SmartLinkContext
{
    Address selfAddr;

    private SmartLinkContext()
    {

    }

    public Address getSelfAddr()
    {
        return selfAddr;
    }

    public void setSelfAddr(Address selfAddr)
    {
        this.selfAddr = selfAddr;
    }

}
