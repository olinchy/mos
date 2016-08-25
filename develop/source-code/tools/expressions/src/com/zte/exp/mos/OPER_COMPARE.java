package com.zte.exp.mos;

import java.util.Stack;

@SuppressWarnings("rawtypes")
public abstract class OPER_COMPARE
{
    public boolean eval(Stack<Comparable> stack)
    {
        Comparable right = stack.pop();
        Comparable left = stack.pop();

        return eval(left, right);
    }

    protected abstract boolean eval(Comparable left, Comparable right);

}
