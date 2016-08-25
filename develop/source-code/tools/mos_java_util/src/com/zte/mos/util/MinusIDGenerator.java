package com.zte.mos.util;


public class MinusIDGenerator
{
    private static Integer seed = -96;

    public static final int genNextId()
    {
        synchronized (seed)
        {
            if (seed == Integer.MIN_VALUE)
            {
                seed = -96;
            }
            else
            {
                seed--;
            }
        }
        return seed;
    }
}
