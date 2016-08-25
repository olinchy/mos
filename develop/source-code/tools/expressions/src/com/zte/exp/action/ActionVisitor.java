// Generated from Action.g4 by ANTLR 4.1
package com.zte.exp.action;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ActionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface ActionVisitor<T> extends ParseTreeVisitor<T>
{
    /**
     * Visit a parse tree produced by .
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOneDNTerm(@NotNull ActionParser.OneDNTermContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#sOrE}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSOrE(@NotNull ActionParser.SOrEContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#contains}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContains(@NotNull ActionParser.ContainsContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#boolTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBoolTerm(@NotNull ActionParser.BoolTermContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#commit}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCommit(@NotNull ActionParser.CommitContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#value}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitValue(@NotNull ActionParser.ValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#dbString}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDbString(@NotNull ActionParser.DbStringContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#notEqual}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNotEqual(@NotNull ActionParser.NotEqualContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#right}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRight(@NotNull ActionParser.RightContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#intValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIntValue(@NotNull ActionParser.IntValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#neid}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNeid(@NotNull ActionParser.NeidContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#propertyName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPropertyName(@NotNull ActionParser.PropertyNameContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#index}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndex(@NotNull ActionParser.IndexContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#dnexp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDnexp(@NotNull ActionParser.DnexpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#minus}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMinus(@NotNull ActionParser.MinusContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#select}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSelect(@NotNull ActionParser.SelectContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#or}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOr(@NotNull ActionParser.OrContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#init}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInit(@NotNull ActionParser.InitContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#boolElement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBoolElement(@NotNull ActionParser.BoolElementContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#dbexp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDbexp(@NotNull ActionParser.DbexpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#octValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOctValue(@NotNull ActionParser.OctValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#like}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLike(@NotNull ActionParser.LikeContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#and}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnd(@NotNull ActionParser.AndContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#not}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNot(@NotNull ActionParser.NotContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#exp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExp(@NotNull ActionParser.ExpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#extendedExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExtendedExp(@NotNull ActionParser.ExtendedExpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#calcaddT}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCalcaddT(@NotNull ActionParser.CalcaddTContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#multi}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMulti(@NotNull ActionParser.MultiContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#bar}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBar(@NotNull ActionParser.BarContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#calcTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCalcTerm(@NotNull ActionParser.CalcTermContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#array}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray(@NotNull ActionParser.ArrayContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#bOrE}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBOrE(@NotNull ActionParser.BOrEContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#hexValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHexValue(@NotNull ActionParser.HexValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#bigger}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBigger(@NotNull ActionParser.BiggerContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#twoDNTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTwoDNTerm(@NotNull ActionParser.TwoDNTermContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#ver}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVer(@NotNull ActionParser.VerContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#boolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBoolExpr(@NotNull ActionParser.BoolExprContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#add}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdd(@NotNull ActionParser.AddContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#arrayexp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArrayexp(@NotNull ActionParser.ArrayexpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#readCompare}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReadCompare(@NotNull ActionParser.ReadCompareContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#slashValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSlashValue(@NotNull ActionParser.SlashValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#smaller}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSmaller(@NotNull ActionParser.SmallerContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#dnElements}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDnElements(@NotNull ActionParser.DnElementsContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#pureString}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPureString(@NotNull ActionParser.PureStringContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#localexp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLocalexp(@NotNull ActionParser.LocalexpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#json}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJson(@NotNull ActionParser.JsonContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#treeAct}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTreeAct(@NotNull ActionParser.TreeActContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitName(@NotNull ActionParser.NameContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#className}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClassName(@NotNull ActionParser.ClassNameContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#left}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLeft(@NotNull ActionParser.LeftContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#slashExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSlashExp(@NotNull ActionParser.SlashExpContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#onSelect}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOnSelect(@NotNull ActionParser.OnSelectContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#jsonObject}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJsonObject(@NotNull ActionParser.JsonObjectContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#trueValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTrueValue(@NotNull ActionParser.TrueValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#expvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpvalue(@NotNull ActionParser.ExpvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#falseValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFalseValue(@NotNull ActionParser.FalseValueContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#divid}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDivid(@NotNull ActionParser.DividContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#equal}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEqual(@NotNull ActionParser.EqualContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#dnterm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDnterm(@NotNull ActionParser.DntermContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#in}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIn(@NotNull ActionParser.InContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#para}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPara(@NotNull ActionParser.ParaContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#ampersand}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAmpersand(@NotNull ActionParser.AmpersandContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#calcT}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCalcT(@NotNull ActionParser.CalcTContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#ne}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNe(@NotNull ActionParser.NeContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#calcmulti}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCalcmulti(@NotNull ActionParser.CalcmultiContext ctx);

    /**
     * Visit a parse tree produced by {@link ActionParser#moexp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMoexp(@NotNull ActionParser.MoexpContext ctx);
}