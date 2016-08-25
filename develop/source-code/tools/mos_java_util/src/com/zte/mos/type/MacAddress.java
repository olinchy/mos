package com.zte.mos.type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by ccy on 5/12/15.
 */
@JsonSerialize(using = MacAddressSerializer.class)
public class MacAddress implements Serializable
{
    public MacAddress(String fieldValue)
    {
        this.value = fieldValue.trim();
    }

    private String value;

    public String toString()
    {
        return value;
    }

    protected MacAddress()
    {

    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MacAddress))
        {
            return false;
        }
        return String.valueOf(this.value).equalsIgnoreCase((String.valueOf(((MacAddress) o).value)));
    }
}
