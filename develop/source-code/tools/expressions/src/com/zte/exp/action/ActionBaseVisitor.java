// Generated from Action.g4 by ANTLR 4.1
package com.zte.exp.action;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link ActionVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public class ActionBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements ActionVisitor<T>
{
    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitOneDNTerm(@NotNull ActionParser.OneDNTermContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitSOrE(@NotNull ActionParser.SOrEContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitContains(@NotNull ActionParser.ContainsContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBoolTerm(@NotNull ActionParser.BoolTermContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitCommit(@NotNull ActionParser.CommitContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitValue(@NotNull ActionParser.ValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDbString(@NotNull ActionParser.DbStringContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitNotEqual(@NotNull ActionParser.NotEqualContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitRight(@NotNull ActionParser.RightContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitIntValue(@NotNull ActionParser.IntValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitNeid(@NotNull ActionParser.NeidContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitPropertyName(@NotNull ActionParser.PropertyNameContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitIndex(@NotNull ActionParser.IndexContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDnexp(@NotNull ActionParser.DnexpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitMinus(@NotNull ActionParser.MinusContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitSelect(@NotNull ActionParser.SelectContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitOr(@NotNull ActionParser.OrContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitInit(@NotNull ActionParser.InitContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBoolElement(@NotNull ActionParser.BoolElementContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDbexp(@NotNull ActionParser.DbexpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitOctValue(@NotNull ActionParser.OctValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitLike(@NotNull ActionParser.LikeContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitAnd(@NotNull ActionParser.AndContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitNot(@NotNull ActionParser.NotContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitExp(@NotNull ActionParser.ExpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitExtendedExp(@NotNull ActionParser.ExtendedExpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitCalcaddT(@NotNull ActionParser.CalcaddTContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitMulti(@NotNull ActionParser.MultiContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBar(@NotNull ActionParser.BarContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitCalcTerm(@NotNull ActionParser.CalcTermContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitArray(@NotNull ActionParser.ArrayContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBOrE(@NotNull ActionParser.BOrEContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitHexValue(@NotNull ActionParser.HexValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBigger(@NotNull ActionParser.BiggerContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitTwoDNTerm(@NotNull ActionParser.TwoDNTermContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitVer(@NotNull ActionParser.VerContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitBoolExpr(@NotNull ActionParser.BoolExprContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitAdd(@NotNull ActionParser.AddContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitArrayexp(@NotNull ActionParser.ArrayexpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitReadCompare(@NotNull ActionParser.ReadCompareContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitSlashValue(@NotNull ActionParser.SlashValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitSmaller(@NotNull ActionParser.SmallerContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDnElements(@NotNull ActionParser.DnElementsContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitPureString(@NotNull ActionParser.PureStringContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitLocalexp(@NotNull ActionParser.LocalexpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitJson(@NotNull ActionParser.JsonContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitTreeAct(@NotNull ActionParser.TreeActContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitName(@NotNull ActionParser.NameContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitClassName(@NotNull ActionParser.ClassNameContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitLeft(@NotNull ActionParser.LeftContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitSlashExp(@NotNull ActionParser.SlashExpContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitOnSelect(@NotNull ActionParser.OnSelectContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitJsonObject(@NotNull ActionParser.JsonObjectContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitTrueValue(@NotNull ActionParser.TrueValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitExpvalue(@NotNull ActionParser.ExpvalueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitFalseValue(@NotNull ActionParser.FalseValueContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDivid(@NotNull ActionParser.DividContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitEqual(@NotNull ActionParser.EqualContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitDnterm(@NotNull ActionParser.DntermContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitIn(@NotNull ActionParser.InContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitPara(@NotNull ActionParser.ParaContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitAmpersand(@NotNull ActionParser.AmpersandContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitCalcT(@NotNull ActionParser.CalcTContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitNe(@NotNull ActionParser.NeContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitCalcmulti(@NotNull ActionParser.CalcmultiContext ctx)
    {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override public T visitMoexp(@NotNull ActionParser.MoexpContext ctx)
    {
        return visitChildren(ctx);
    }
}