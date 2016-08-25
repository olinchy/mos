package com.zte.exp.mos;

import java.util.Stack;

public class OPER_REL_NOT
{
    @SuppressWarnings("rawtypes")
    public boolean eval(Stack<Comparable> stack)
    {
        Object operand = stack.pop();

        return !Boolean.valueOf(operand.toString());
    }

}
