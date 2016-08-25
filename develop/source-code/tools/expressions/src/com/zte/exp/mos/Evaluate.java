package com.zte.exp.mos;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Evaluate
{
    public static boolean evaluate(String by, MosBaseListener listener)
    {
        walk(by, listener);

        return listener.getRes();
    }

    public static boolean evaluate(ParseTree tree, MosBaseListener listener)
    {
        walk(tree, listener);

        return listener.getRes();
    }

    public static void walk(String by, MosBaseListener listener)
    {
        ParseTree tree = getTree(by);
        walk(tree, listener);
    }

    private static void walk(ParseTree tree, MosBaseListener listener)
    {
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
    }

    public static ParseTree getTree(String by)
    {
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(by);
        // create a lexer that feeds off of input CharStream
        Mos_expLexer lexer = new Mos_expLexer(input);
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        Mos_expParser parser = new Mos_expParser(tokens);
        ParseTree tree = parser.init(); // begin parsing at init rule
        return tree;
    }

}
