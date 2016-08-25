package com.zte.mos.domain;

import com.zte.mos.annotation.MoEnum;

public enum State implements MoEnum<Byte>
{
    online((byte) 0), offline((byte) 1);
    private byte enumValue;

    State(byte enumValue)
    {
        this.enumValue = enumValue;
    }

    @Override
    public String value()
    {
        return String.valueOf(enumValue);
    }

    @Override
    public MoEnum valuesOf(String value)
    {
        return valueOf(value);
    }

    @Override
    public MoEnum contentOf(Byte content)
    {
        return null;
    }

}