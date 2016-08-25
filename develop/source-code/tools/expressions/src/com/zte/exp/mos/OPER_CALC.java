package com.zte.exp.mos;

import java.util.Stack;

@SuppressWarnings("rawtypes")
public abstract class OPER_CALC
{
    public long eval(Stack<Comparable> stack)
    {
        long right = 0;
        long left = 0;
        try
        {
            right = Long.parseLong(stack.pop().toString());
            left = Long.parseLong(stack.pop().toString());
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }

        return eval(left, right);
    }

    public abstract long eval(long left, long right);
}
