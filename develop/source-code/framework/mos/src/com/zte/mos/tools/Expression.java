package com.zte.mos.tools;

import com.zte.exp.mos.Evaluate;
import com.zte.exp.mos.MosBaseListener;
import com.zte.mos.message.Mo;
import org.antlr.v4.runtime.tree.ParseTree;

interface Evaluator
{
    boolean eval(Mo mo, Object local);

    boolean eval(MosBaseListener listener);
}

public class Expression
{
    private Evaluator evaluator = null;

    public Expression(String by)
    {
        this.evaluator = createEvaluator(by);
    }

    public boolean evaluate(Mo mo, Object local)
    {
        return this.evaluator.eval(mo, local);
    }

    public boolean evaluate(MosBaseListener listener)
    {
        return this.evaluator.eval(listener);
    }

    private Evaluator createEvaluator(String by)
    {
        if (by == null || by.equals(""))
        {
            return new NullEvaluator();
        }
        else
        {
            return new AntlrEvaluator(by);
        }
    }
}

class NullEvaluator implements Evaluator
{
    @Override
    public boolean eval(Mo mo, Object local)
    {
        return true;
    }

    @Override
    public boolean eval(MosBaseListener listener)
    {
        return true;
    }
}

class AntlrEvaluator implements Evaluator
{
    private final ParseTree tree;

    public AntlrEvaluator(String by)
    {
        this.tree = Evaluate.getTree(by);
    }

    @Override
    public boolean eval(Mo mo, Object local)
    {
        return Evaluate.evaluate(tree, new MoListener(mo));
    }

    @Override
    public boolean eval(MosBaseListener listener)
    {
        return Evaluate.evaluate(tree, listener);
    }
}
