package com.zte.exp.mos;

import com.zte.exp.mos.Mos_expParser.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import static com.zte.exp.mos.CALC.ADD;
import static com.zte.exp.mos.CALC.AMPERSAND;
import static com.zte.exp.mos.CALC.BAR;
import static com.zte.exp.mos.CALC.BIGGER;
import static com.zte.exp.mos.CALC.*;
import static com.zte.exp.mos.CALC.EQUAL;
import static com.zte.exp.mos.CALC.LEFT;
import static com.zte.exp.mos.CALC.MINUS;
import static com.zte.exp.mos.CALC.MULTI;
import static com.zte.exp.mos.CALC.NOT;
import static com.zte.exp.mos.CALC.RIGHT;
import static com.zte.exp.mos.CALC.SMALLER;

@SuppressWarnings({ "rawtypes" })
public class MosBaseListener extends Mos_expBaseListener
{
    protected final Stack<Comparable> stack = new Stack<Comparable>();

    public boolean getRes()
    {
        return Boolean.valueOf(stack.pop().toString());
    }

    @Override
    public void exitNot(@NotNull Mos_expParser.NotContext ctx)
    {
        stack.push(NOT.eval(stack));
    }

    @Override
    public void exitAnd(@NotNull Mos_expParser.AndContext ctx)
    {
        Vector<Boolean> vec = new Vector<Boolean>();
        vec.add((Boolean) stack.pop());
        int starter = ctx.getChildCount() - 2;
        for (int i = starter; i >= 1; )
        {
            vec.add((Boolean) stack.pop());
            i = i - 2;
        }
        boolean res = true;
        for (int i = vec.size() - 1; i >= 0; i--)
        {
            res = res && vec.elementAt(i);
        }
        stack.push(res);
    }

    @Override public void exitRootDN(@NotNull RootDNContext ctx)
    {
        stack.push("/");
    }

    @Override public void enterWildDN(@NotNull WildDNContext ctx)
    {
        stack.push("/*");
    }

    @Override
    public void exitOr(@NotNull Mos_expParser.OrContext ctx)
    {
        Vector<Boolean> vec = new Vector<Boolean>();
        vec.add((Boolean) stack.pop());
        int starter = ctx.getChildCount() - 2;
        for (int i = starter; i >= 1; )
        {
            vec.add((Boolean) stack.pop());
            i = i - 2;
        }
        boolean res = false;
        for (int i = vec.size() - 1; i >= 0; i--)
        {
            res = res || vec.elementAt(i);
        }
        stack.push(res);
    }

    @Override
    public void exitBOrE(BOrEContext ctx)
    {
        stack.push(BorE.eval(stack));
    }

    @Override
    public void exitBigger(BiggerContext ctx)
    {
        stack.push(BIGGER.eval(stack));
    }

    @Override
    public void exitSOrE(SOrEContext ctx)
    {
        stack.push(SorE.eval(stack));
    }

    @Override
    public void exitContains(ContainsContext ctx)
    {
        Object toCheck = stack.pop();
        Object check = stack.pop();
        if (check instanceof List)
        {
            boolean isContains = ((List) check).contains(toCheck);
            boolean isContainsByElement = false;
            for (Object o : ((List) check))
            {
                if (String.valueOf(o).contains(toCheck.toString()))
                {
                    isContainsByElement = true;
                    break;
                }
            }

            stack.push(isContains || isContainsByElement);
        }
        else
        {
            stack.push(check.toString().contains(toCheck.toString()));
        }
    }

    @Override
    public void exitSmaller(SmallerContext ctx)
    {
        stack.push(SMALLER.eval(stack));
    }

    @Override
    public void exitNotEqual(NotEqualContext ctx)
    {
        stack.push(NOTEqual.eval(stack));
    }

    @Override
    public void exitTrueValue(TrueValueContext ctx)
    {
        stack.push(true);
    }

    @Override
    public void exitFalseValue(FalseValueContext ctx)
    {
        stack.push(false);
    }

    @Override
    public void exitEqual(EqualContext ctx)
    {
        stack.push(EQUAL.eval(stack));
    }

    @Override
    public void exitIn(InContext ctx)
    {
        String[] array = stack.pop().toString().split(",");
        stack.push(Arrays.asList(array).contains(stack.pop().toString()));
    }

    @Override
    public void exitArrayexp(@NotNull Mos_expParser.ArrayexpContext ctx)
    {
        stack.push(ctx.getText());
    }

    @Override
    public void exitDbString(@NotNull Mos_expParser.DbStringContext ctx)
    {
        stack.push(ctx.getText().replaceAll("'", ""));
    }

    @Override
    public void exitPureString(@NotNull Mos_expParser.PureStringContext ctx)
    {
        stack.push(ctx.getText());
    }

    @Override
    public void exitOctValue(@NotNull Mos_expParser.OctValueContext ctx)
    {
        stack.push(Long.parseLong(ctx.getText()));
    }

    @Override
    public void exitAdd(@NotNull Mos_expParser.AddContext ctx)
    {
        stack.push(ADD.eval(stack));
    }

    @Override
    public void exitMinus(@NotNull Mos_expParser.MinusContext ctx)
    {
        stack.push(MINUS.eval(stack));
    }

    @Override
    public void exitMulti(@NotNull Mos_expParser.MultiContext ctx)
    {
        stack.push(MULTI.eval(stack));
    }

    @Override
    public void exitDivid(@NotNull Mos_expParser.DividContext ctx)
    {
        stack.push(DIVIDE.eval(stack));
    }

    @Override
    public void exitAmpersand(@NotNull Mos_expParser.AmpersandContext ctx)
    {
        stack.push(AMPERSAND.eval(stack));
    }

    @Override
    public void exitBar(@NotNull Mos_expParser.BarContext ctx)
    {
        stack.push(BAR.eval(stack));
    }

    @Override
    public void exitLeft(@NotNull Mos_expParser.LeftContext ctx)
    {
        stack.push(LEFT.eval(stack));
    }

    @Override
    public void exitRight(@NotNull Mos_expParser.RightContext ctx)
    {
        stack.push(RIGHT.eval(stack));
    }

    @Override
    public void exitHexValue(@NotNull Mos_expParser.HexValueContext ctx)
    {
        stack.push(Long.parseLong(ctx.getText().replaceAll("0x", ""), 16));
    }

    @Override
    public void exitSlashValue(@NotNull Mos_expParser.SlashValueContext ctx)
    {
        stack.push("dnele:" + ctx.getText());
    }

    @Override
    public void exitSlashExp(@NotNull Mos_expParser.SlashExpContext ctx)
    {
        stack.push("dnele:" + "/" + stack.pop().toString());
    }

    @Override
    public void exitDnElements(@NotNull Mos_expParser.DnElementsContext ctx)
    {
        Stack<String> stckLocal = new Stack<String>();
        while (true)
        {
            String value = stack.pop().toString();
            if (!value.startsWith("dnele:"))
            {
                stack.push(value);
                break;
            }
            else
            {
                stckLocal.push(value.replace("dnele:", ""));
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
    public void exitTwoDNTerm(@NotNull Mos_expParser.TwoDNTermContext ctx)
    {
        String rear = stack.pop().toString();
        String front = stack.pop().toString();
        stack.push(front + rear);
    }

    @Override
    public void exitLike(@NotNull Mos_expParser.LikeContext ctx)
    {
        String rear = stack.pop().toString();
        String front = stack.pop().toString();

        stack.push(front.matches(rear.replace("*", ".*").replace("//", "/")));
    }

    protected void pushObject(Object obj)
    {
        if (obj == null)
        {
            this.pushText("null");
        }
        else
        {
            if (obj instanceof Comparable && (!(obj instanceof String)))
            {
                stack.push((Comparable) obj);
            }
            else
            {
                this.pushText(obj.toString());
            }
        }
    }

    protected void pushText(String text)
    {
        if (text.matches("[0-9]+"))
        {
            stack.push(Long.parseLong(text));
        }
        else
        {
            stack.push(text);
        }
    }

}
