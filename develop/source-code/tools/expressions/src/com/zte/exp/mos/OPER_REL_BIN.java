package com.zte.exp.mos;

import java.util.Stack;

public abstract class OPER_REL_BIN
{
    @SuppressWarnings("rawtypes")
    public boolean eval(Stack<Comparable> stack)
    {
        Object right = stack.pop();
        Object left = stack.pop();

        return eval(left, right);
    }

    protected abstract boolean eval(Object left, Object right);
}
