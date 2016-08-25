package com.zte.mos.domain;

import com.zte.mos.annotation.MoEnum;

/**
 * Created by root on 15-1-19.
 */
public enum AdminState implements MoEnum<Byte>
{
    initializing((byte) 0),
    synchronizing((byte) 1),
    idle((byte) 2),
    inconsistent((byte) 3),
    metaUnknown((byte) 4);
    private final byte stateValue;

    AdminState(byte stateValue)
    {
        this.stateValue = stateValue;
    }

    @Override
    public String value()
    {
        return String.valueOf(stateValue);
    }

    @Override
    public MoEnum valuesOf(String name)
    {
        return AdminState.valueOf(name);
    }

    @Override
    public MoEnum contentOf(Byte content)
    {
        for (AdminState enumInstance : values())
        {
            if (enumInstance.stateValue == content)
            {
                return enumInstance;
            }
        }
        return null;
    }

}
