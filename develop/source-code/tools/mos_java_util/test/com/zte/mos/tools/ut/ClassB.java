package com.zte.mos.tools.ut;

import java.io.Serializable;

/**
 * Created by olinchy on 15-10-28.
 */
public class ClassB implements Serializable
{
    public int id = 15;

    @Override
    public String toString()
    {
        return "ClassB{" +
                "id=" + id +
                '}';
    }
}
