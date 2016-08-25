package com.zte.mos.tools.ut;

import com.zte.mos.inf.Maybe;

import java.io.Serializable;

/**
 * Created by olinchy on 15-10-28.
 */
public class ClassA implements Serializable
{
//    public ClassA(ClassB classB)
//    {
//        this.b = classB;
//    }

    public ClassA()
    {
    }

    public Maybe<Integer> transId;
    public int id = 1;

    @Override
    public String toString()
    {
        return "ClassA{" +
                "b=" + transId +
                ", id=" + id +
                '}';
    }
}
