package com.zte.mos.domain;

/**
 * Created by ccy on 6/16/16.
 */
public enum ConnectionState
{
    ONLINE("online"),OFFLINE("offline"), UNKNOWN("unknown");

    ConnectionState(String value)
    {
        this.value = value;
    }

    public static ConnectionState contentOf(String value)
    {
        for (ConnectionState state : values())
        {
            if (state.value.equals(value))
            {
                return state;
            }
        }
        throw new IllegalArgumentException(value + " is not an option");
    }


    private final String value;
}
