package com.zte.exp.action;

import com.zte.exp.action.ActionParser.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MOSActionBaseListener extends ActionBaseListener
{
    protected final Stack<String> stack = new Stack<String>();
    protected List<String> dnExp = new ArrayList<String>();

    @Override
    public void exitSlashValue(@NotNull SlashValueContext ctx)
    {
        stack.push("dnele:" + ctx.getText());
    }

    @Override
    public void exitSlashExp(@NotNull SlashExpContext ctx)
    {
        stack.push("dnele:" + "/" + stack.pop().toString());
    }

    @Override
    public void exitDnElements(@NotNull DnElementsContext ctx)
    {
        Stack<String> stckLocal = new Stack<String>();
        while (!stack.isEmpty())
        {
            String value = stack.pop().toString();
            if (!value.startsWith("dnele:"))
            {
                stack.push(value);
                break;
            }
            else
            {
                stckLocal.push(value.replaceAll("dnele:", ""));
            }
        }
        StringBuffer buffer = new StringBuffer();
        while (!stckLocal.isEmpty())
        {
            buffer.append(stckLocal.pop());
        }

        stack.push(buffer.toString());
    }

    @Override
    public void exitTwoDNTerm(@NotNull TwoDNTermContext ctx)
    {
        String rear = stack.pop().toString();
        String front = stack.pop().toString();
        stack.push(front + rear);
    }

    @Override
    public void exitMoexp(@NotNull MoexpContext ctx)
    {
        stack.push("*");
    }

    @Override
    public void exitLocalexp(@NotNull LocalexpContext ctx)
    {
        stack.push("*");
    }

    @Override
    public void exitDbexp(@NotNull DbexpContext ctx)
    {
        stack.push("*");
    }

    @Override
    public void exitDnexp(@NotNull ActionParser.DnexpContext ctx)
    {
        this.dnExp.add(stack.pop());
    }

    public List<String> getDNExp()
    {
        return this.dnExp;
    }

}
