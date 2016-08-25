package com.zte.exp.action;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class ActionEvaluate
{
    public static ParseTree getTree(String by)
    {
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(by);
        // create a lexer that feeds off of input CharStream
        ActionLexer lexer = new ActionLexer(input);
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        ActionParser parser = new ActionParser(tokens);
        ParseTree tree = parser.action(); // begin parsing at init rule

        return tree;
    }

    public static ActionDesc evaluate(ParseTree tree)
    {
        return new MOSActionVisitor().visit(tree);
    }

    public static ActionDesc evaluate(String exp)
    {
        return new MOSActionVisitor().visit(getTree(exp));
    }

    public static List<String> evaluate(String exp, MOSActionBaseListener listener)
    {
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, getTree(exp));
        return listener.getDNExp();
    }
}
